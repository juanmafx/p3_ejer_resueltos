# Changelog

This project follows [Semantic Versioning](https://semver.org/) and the [Keep a Changelog](https://keepachangelog.com/) format.

## [1.0.1] - 2026-11-24

Documentation corrections only. No change to code behaviour.

### Changed

- README, release notes, and security audit now reflect the published 1.0.0 release and the rewritten Git history, which no longer contains the 2019 code.

## [1.0.0] - 2026-11-24 (released)

First versioned release, and a complete rewrite of the unversioned 2019 exercises.

### Breaking

- Replaced the separate NetBeans projects with one Maven project and CLI.
- Replaced interactive console prompts with command-line arguments.
- Renamed all code, output, files, and folders to English.

### Added

- Layered Java 17 domain model and CLI with no runtime dependencies.
- Round-robin scheduling, standings, and seeded Monte Carlo title odds.
- Welford statistics and Wilson score intervals.
- JUnit, jqwik property-based, statistical, CLI, and SQLite schema tests.
- Optional SQLite schema, committed test report, and least-privilege CI.
- Architecture, security, and release documentation.

### Fixed

- Leap year check used inverted modulo operands.
- Student average used integer division and lost its fractional part.
- Best student was empty when every average was zero.
- Average of an empty cohort produced a non-numeric result.
- Match winner never reported an away win.
- Match entry stored the away team's goals on the home team.
- Vehicle constructor discarded the mileage.
- Person initializers were void methods and copied the first name into the last name.
- Person summary printed the birth year labeled as age.
- Positive number validation accepted zero.
- Failed `leap-year` and `person` commands no longer write partial output.
- Numeric arguments no longer accept Java-only forms such as `1d` or `0x1p3`.
- Labels made only of Unicode space characters are rejected as blank.

### Security

- Removed tracked compiled classes and private IDE metadata.
- Removed personal details from sample code.
- Added input bounds and protection against terminal and CSV injection.
- Pinned the build toolchain and CI actions.
- Overrode `plexus-utils` in the clean and resources plugins to fixed versions (CVE-2025-67030).
- Enforced the total simulated match budget in the library as well as the CLI.
- Extended CSV formula protection to full-width prefix characters.
- CI no longer publishes the committed test report when a run fails.

### Removed

- Duplicate control-flow project and an empty placeholder class.
