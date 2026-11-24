# Changelog

This project follows [Semantic Versioning](https://semver.org/) and the [Keep a Changelog](https://keepachangelog.com/) format.

## [1.0.0] - 2026-11-23

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

### Security

- Removed tracked compiled classes and private IDE metadata.
- Removed personal details from sample code.
- Added input bounds and protection against terminal and CSV injection.
- Pinned the build toolchain and CI actions.

### Removed

- Duplicate control-flow project and an empty placeholder class.
