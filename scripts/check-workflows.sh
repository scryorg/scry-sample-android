#!/usr/bin/env bash
# scripts/check-workflows.sh - guarantee G5: the Scry upload can never run for a pull request, and the
# workflows never run on a machine you do not control. Fails (exit 1) when either rule is broken.
#   Rule 1: no `pull_request_target` trigger, no `self-hosted` runner, anywhere in .github/workflows.
#   Rule 2: secrets are used only by scry-capture.yml; that workflow triggers on `push` to the default
#           branch only (no pull_request, no workflow_dispatch, no schedule) and gates the upload step on
#           the default branch ref.
set -uo pipefail
cd "$(dirname "$0")/.."
wf=.github/workflows
fail=0
bad() { echo "check-workflows: $*" >&2; fail=1; }

[ -d "$wf" ] || { echo "check-workflows: no $wf directory" >&2; exit 1; }
shopt -s nullglob
files=("$wf"/*.yml "$wf"/*.yaml)
[ "${#files[@]}" -gt 0 ] || { echo "check-workflows: no workflow files" >&2; exit 1; }

# Rule 1 (comments are ignored: only non-comment lines count).
for f in "${files[@]}"; do
  grep -v '^[[:space:]]*#' "$f" | grep -n 'pull_request_target' >/dev/null && bad "$f uses pull_request_target"
  grep -v '^[[:space:]]*#' "$f" | grep -n 'self-hosted' >/dev/null && bad "$f uses a self-hosted runner"
done

# Rule 2.
for f in "${files[@]}"; do
  if [ "$(basename "$f")" != scry-capture.yml ]; then
    grep -v '^[[:space:]]*#' "$f" | grep -nE 'secrets\.|SCRY_API_KEY' >/dev/null && bad "$f reads a secret (only scry-capture.yml may)"
  fi
done
cap="$wf/scry-capture.yml"
if [ ! -f "$cap" ]; then
  bad "$cap is missing"
else
  body="$(grep -v '^[[:space:]]*#' "$cap")"
  echo "$body" | grep -nE '^[[:space:]]*(pull_request|workflow_dispatch|schedule|workflow_run|issue_comment)\b' >/dev/null \
    && bad "$cap has a trigger other than push to the default branch"
  echo "$body" | grep -qE '^[[:space:]]*push:' || bad "$cap has no push trigger"
  echo "$body" | grep -qE 'branches:[[:space:]]*\[main\]' || bad "$cap does not limit push to branches: [main]"
  # The step that uses the key must carry the default-branch guard.
  echo "$body" | awk '
    /^[[:space:]]*- / { if (step ~ /SCRY_API_KEY/ && step !~ /github.ref == .refs\/heads\/main./) bad = 1; step = "" }
    { step = step "\n" $0 }
    END { if (step ~ /SCRY_API_KEY/ && step !~ /github.ref == .refs\/heads\/main./) bad = 1; exit bad }
  ' || bad "$cap: the step that reads SCRY_API_KEY is not gated on github.ref == 'refs/heads/main'"
fi

[ "$fail" -eq 0 ] && echo "check-workflows: ok (${#files[@]} workflows)"
exit "$fail"
