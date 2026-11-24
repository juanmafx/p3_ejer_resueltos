# Release Notes: 1.0.1

Documentation-only patch release. Code behaviour is unchanged from 1.0.0.

## Changes

- The README, security audit, and these notes now reflect that 1.0.0 is published and that the public Git history was rewritten on 2026-11-24. The 2019 exercises are no longer in any branch or tag.
- Audit items about personal metadata in Git history and legacy remote branches are marked resolved.

## Upgrade notes

No action is needed when upgrading from 1.0.0. Users of the unversioned 2019 exercises should use the commands listed in the [README](README.md).

## Verification

Run `./scripts/run-tests.sh`. The committed result of the real run is in [reports/tests/test-report.md](reports/tests/test-report.md).

## Known limitations

- The simulation model is illustrative and has not been calibrated against real data.
- The optional database schema is not connected to the CLI.
- Only JDK 17 has been tested.
- No license has been declared.
