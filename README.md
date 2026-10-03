# scry-sample-android

Kettle, a small coffee-order app in Kotlin and Jetpack Compose, wired so [Scry](https://scrymore.com) can capture its screens.
Clone it, run it on an emulator, and get three screens and two components into your own Scry project in five steps.
Search, the Scry MCP server and the Figma link then work on your Android screens the way they do on web.

**No code of yours is uploaded.** Scry receives PNG screenshots and a small manifest (`scf.json`), nothing else.

The app is the example. To do the same for your own app, jump to [Make it your own](#make-it-your-own).

## Before you start

- JDK 17, the Android SDK (platform 34 and platform-tools) and an emulator or device (`adb devices` lists one).
  On a Pixel 6 API 34 emulator the screenshots come out at 1080 x 2400.
- Node 20 or newer (the capture script and the Scry CLI).
- A Scry project and a project API key (dashboard, Settings, API keys). Keep the key in your shell or CI secrets, never in a file you commit.
  Placeholders below: `proj_xxxxxxxx`, `sk_live_xxxxxxxx`.

## The five steps

### 1. Get the sample app

```sh
git clone https://github.com/scryorg/scry-sample-android.git
cd scry-sample-android
echo "sdk.dir=$ANDROID_HOME" > local.properties     # once; the file is gitignored
./gradlew :app:installDebug                           # builds with the pinned Gradle wrapper and installs on the running emulator
```

Open "Kettle" on the emulator: Menu, tap a drink for its detail, "View order" for the order.

### 2. Capture your screens

```sh
bash scripts/capture.sh
```

The script installs the debug build, turns animations off, puts the status bar in demo mode (9:41, full battery, no notifications),
then for each screen in `scripts/screens.json` starts the app with `--es scry_screen <id>`, waits for the logcat line
`scry:ready <id>`, and takes `adb exec-out screencap -p`. It writes `.scry/capture/` (`scf.json` plus `images/`, gitignored).

Expected: five `capture: <id> ok (N bytes)` lines and `scf: 5/5 captured, 0 skipped -> .scry/capture`.
Components are captured full-frame (the manifest says `crop: none`), so a component image is mostly background.
`docs/screens/` holds the five PNGs from a real run, so you can see what to expect.

### 3. Check the bundle

```sh
npx @scrymore/scry-deployer upload .scry/capture --dry-run
```

Expected, and nothing is sent:

```
Validating .scry/capture ...
✅ Bundle valid: 5 captures, source compose-preview:android.
Dry run: not uploading. Bundle ZIP: /tmp/scry-bundle-XXXXXX/bundle.zip
```

### 4. Upload

```sh
export SCRY_PROJECT_ID=proj_xxxxxxxx
export SCRY_API_KEY=sk_live_xxxxxxxx
npx @scrymore/scry-deployer upload .scry/capture
```

Expected: the CLI ends with `build #N` for your project.

### 5. See it in Scry

Open the project in the dashboard. The new build shows the source chip "Compose · Android" and the emulator device card;
click a screen to see it in the editor with its source file and line (`MenuScreen.kt:14`). Search for "quantity stepper",
or ask the Scry MCP server for it: the result names the platform and the file.

## What is in the app

| Piece | Where |
|---|---|
| 3 screens: Menu, Item Detail, Order | `app/src/main/java/com/scrymore/kettle/ui/*Screen.kt` |
| 2 components: Button, Quantity Stepper (plus the menu row) | `app/src/main/java/com/scrymore/kettle/ui/` |
| Fixed data and copy (same as `scryorg/scry-sample-rn`) | `Fixtures.kt`, `Tokens.kt` |
| Capture hook (debug builds only) | `app/src/debug/java/com/scrymore/kettle/ScryLaunch.kt` |
| Screen registry (debug builds only) | `app/src/debug/java/com/scrymore/kettle/ScryScreens.kt` |
| No-op hook for release builds | `app/src/release/java/com/scrymore/kettle/ScryLaunch.kt` |
| Capture, bundle writer | `scripts/capture.sh`, `scripts/make-scf.mjs`, `scripts/screens.json` |
| The same app without any hook | `scripts/make-bare.sh <dest>` (generated, not committed) |
| CI | `.github/workflows/ci.yml`, `.github/workflows/scry-capture.yml` |

The hook is about 30 lines. A normal launch is unchanged. The hook and registry live in the `debug` source set, so a release
build has none of it (CI checks that the release APK does not contain the `scry_screen` extra).
The app has no dependencies beyond Compose and AndroidX, and nothing from Scry.

## Put it in CI

`.github/workflows/scry-capture.yml` captures on a Linux runner with an Android emulator
(`reactivecircus/android-emulator-runner`) and uploads **only on a push to `main`**. Set a repository variable `SCRY_PROJECT_ID`
and a repository secret `SCRY_API_KEY`. `ci.yml` builds and lints on every pull request and uses no secrets.
Two rules hold for both workflows and `scripts/check-workflows.sh` fails if either breaks: no `pull_request_target`, no
self-hosted runner; and only `scry-capture.yml` may read the key, on a push to the default branch.

## Make it your own

Four things to change in the sample: the application id (`app/build.gradle.kts`, `namespace` and `applicationId`, plus the
package folders), the screens (`ScryScreens.kt` and `scripts/screens.json`), your project id, and your key (as above).

To add one of your own screens, add one line to `ScryScreens.kt`:

```kotlin
ScryScreen("screens-settings", "Settings", "$UI/SettingsScreen.kt", 12) { SettingsScreen() },
```

and the matching entry to `scripts/screens.json`. Give each screen fixed data (no clock, no network, no sign-in) so it
captures the same twice.

To see the "before" picture, `bash scripts/make-bare.sh ../kettle-bare` writes the app without any Scry code; it builds
with `./gradlew assembleDebug` and is what the skill below starts from.

## Use the Scry skill

For an existing app, the supported way is the Scry skill, which does the wiring above for you and checks the result:

```sh
npx skills add scryorg/scry-node --skill scry-native-capture-setup
```

Then ask your assistant: "set up Scry capture for this app". It inspects the project, adds the launch hook and registry,
fixtures, `scripts/capture.sh` and `scripts/make-scf.mjs`, and optionally the CI workflow; it runs the capture and
`upload --dry-run`, shows you the screenshots, and never uploads or reads your key without your go.

## License

MIT, see `LICENSE`. The Inter font in `app/src/main/res/font/` is under the SIL Open Font License (`licenses/Inter-OFL.txt`).
