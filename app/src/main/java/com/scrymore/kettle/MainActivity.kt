package com.scrymore.kettle

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // scry:begin
        // Capture hook: `adb shell am start -n <pkg>/.MainActivity --es scry_screen <id>` shows only that screen.
        // A normal launch has no extra, so it is unchanged. Release builds get a no-op ScryLaunch.
        val scryId = ScryLaunch.requestedScreen(intent)
        setContent { if (scryId != null) ScryCaptureRoot(scryId) else KettleApp() }
        // scry:end
    }
}
