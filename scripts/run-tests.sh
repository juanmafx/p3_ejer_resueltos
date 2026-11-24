#!/usr/bin/env bash
set -euo pipefail

root="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
cd "$root"
mvn_bin="$(./scripts/bootstrap-maven.sh)"
"$mvn_bin" -B -ntp --strict-checksums clean verify
JAVA_RUNTIME="$(java -version 2>&1 | sed -n '2p')" \
MAVEN_RUNTIME="$("$mvn_bin" -v | sed -n '1p' | sed 's/\x1b\[[0-9;]*m//g')" \
python3 scripts/generate_test_report.py
