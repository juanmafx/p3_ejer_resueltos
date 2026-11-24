# Release Notes: 1.0.0

**Status:** prepared on branch `modernize/2026`. No tag or GitHub release has been created.

## Highlights

- One tested Java 17 CLI and library replaces the separate 2019 NetBeans exercises.
- Ten logic defects from the original code are corrected.
- A new seeded tournament simulation reports title odds with 95% Wilson intervals.
- The codebase, documentation, and output are in English.

## Upgrade notes

The old projects and interactive prompts are gone. Use the commands listed in the [README](README.md). The original code remains in the Git history.

## Verification

Run `./scripts/run-tests.sh`. The committed result of the real run is in [reports/tests/test-report.md](reports/tests/test-report.md).

## Known limitations

- The simulation model is illustrative and has not been calibrated against real data.
- The optional database schema is not connected to the CLI.
- The CI workflow has not yet run on GitHub.
- Only JDK 17 has been tested.
- No license has been declared.
- Git history retains minor personal metadata, described in [docs/SECURITY_AUDIT.md](docs/SECURITY_AUDIT.md).
