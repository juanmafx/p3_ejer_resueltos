# java-algorithms-lab

A tested Java 17 library and command-line tool built from a set of solved introductory programming exercises, extended with correct algorithms, a seeded tournament simulation, and statistical verification.

> **Project history:** originally created in 2019, modernized in 2026. The 2019 code is no longer part of the published Git history; the 2026 version is a rewrite, not a record of what was written at that time.

## Purpose

The repository began as a folder of independent NetBeans console exercises from a programming course. This version keeps each exercise's subject and turns the collection into one coherent, verifiable codebase.

## Why this project exists

- To preserve the original exercises in a form that can be built and run today.
- To correct the logic defects in the original solutions and pin each fix with a regression test.
- To serve as a compact study reference for domain modeling, algorithm analysis, reproducible simulation, and property-based testing.

It is a study project. It is not a production service and has no known production users.

## What it does

| Command | Exercise | Behavior |
| --- | --- | --- |
| `leap-year` | Leap year check | Applies the Gregorian rule to one or more years |
| `first-positive` | Positive number validation | Returns the first strictly positive finite value |
| `vehicle` | Vehicle record | Reports a vehicle and its age |
| `person` | Person record | Reports full name, age reached in the current year, and optional body mass index |
| `grades` | Student averages | Reports cohort mean, sample standard deviation, best student, and standing counts |
| `match` | Match winner | Decides the winner of a match or reports a draw |
| `schedule` | Tournament extension | Generates a single round-robin schedule |
| `simulate` | Tournament extension | Estimates title odds by seeded Monte Carlo simulation |
| `loop-trace` | Control-flow demo | Traces `continue` and `break` in a loop |

`schedule` and `simulate` are 2026 additions that extend the original match and team exercise.

## Architecture

The code has one dependency direction: `cli` depends on `domain`, and `domain` depends only on `support` and the JDK. There are no runtime dependencies. The project has no client/server split, so it has no `frontend/` or `backend/` directories.

```text
src/main/java/io/github/juanmafx/p3exercises/
  cli/            argument parsing, dispatch, exit codes
    commands/     one class per command
  domain/
    calendar/     leap-year rules and closed-form counting
    controlflow/  loop tracing
    grading/      students, standings, cohort reports
    person/       person and body metrics
    statistics/   Welford statistics and Wilson intervals
    tournament/   results, standings, scheduling, simulation
    validation/   numeric validation
    vehicle/      vehicle model
  support/        shared input validation
db/               optional SQLite schema and its tests
reports/tests/    committed report from a real test run
scripts/          bootstrap, test, and report tooling
```

See [docs/ARCHITECTURE.md](docs/ARCHITECTURE.md) for design decisions and complexity analysis.

## Quick start

Requirements: JDK 17 (the only version tested), Python 3 with SQLite 3.37 or later, and `curl`. Maven 3.10.2 is downloaded on first use and verified against a pinned SHA-512 checksum.

```bash
./scripts/run-tests.sh
java -jar target/p3-exercises-1.0.1.jar --help
```

## Usage guide

```bash
alias p3='java -jar target/p3-exercises-1.0.1.jar'

p3 leap-year 1900 2000
p3 first-positive -3 0 2.5
p3 vehicle Acme 2010 120000 8500.00
p3 person Ada Lovelace 1990 1.75 70
p3 grades Alice:7,8,8 Bob:2,3,4
p3 match Home 0 Away 2
p3 schedule Lions Hawks Owls Foxes
p3 simulate 42 5000 1.2 Lions:1.8 Hawks:1.4 Owls:1.0
p3 loop-trace
```

The `simulate` arguments are seed, runs, home advantage multiplier, and `team:goalRate` pairs. The same seed and inputs produce identical output:

```text
team,titles,probability,ci95_lower,ci95_upper
Lions,2865,0.5730,0.5592,0.5866
Hawks,1462,0.2924,0.2800,0.3052
Owls,673,0.1346,0.1254,0.1443
```

These figures come from hypothetical input rates. They are an illustration of the method and do not predict any real competition.

Exit codes: `0` for success and `2` for invalid usage. Invalid usage writes nothing to standard output. Body mass index is an arithmetic exercise and is not medical advice.

## Configuration

The tool reads no configuration files, environment variables, or network resources. All input comes from command-line arguments.

| Limit | Value |
| --- | --- |
| Label length | 100 characters |
| Grades | Integers from 0 to 10, 64 per student |
| Regular standing | Average of at least 4 |
| Teams | 2 to 64 |
| Simulation runs | 100,000 in the CLI, 1,000,000 in the library |
| Simulated matches | 20,000,000 per simulation, in the CLI and the library |
| Team goal rate | Greater than 0, at most 10 |
| Home advantage | Greater than 0, at most 3 |
| Numeric arguments | Plain decimal notation, 40 characters at most |

The tooling honors `MAVEN_TOOLS_DIR` for the Maven install location and the standard `MAVEN_ARGS`.

## Database (optional)

[db/schema.sql](db/schema.sql) contains SQLite `CREATE` statements for people, vehicles, students, grades, tournaments, matches, and simulation results.

**The schema is optional. The CLI does not read or write a database.** It is provided as a reference model and has its own tests.

```bash
sqlite3 p3.db < db/schema.sql
```

The schema uses `STRICT` tables, which require SQLite 3.37 or later. SQLite enforces foreign keys per connection, so run `PRAGMA foreign_keys = ON` on every connection that writes data.

## Testing

```bash
./scripts/run-tests.sh
```

The suite combines JUnit 5 example tests, jqwik property-based tests with fixed seeds, statistical tests (chi-square goodness of fit, binomial tolerance, and empirical interval coverage), CLI tests, and SQLite schema tests. The latest real run is recorded in [reports/tests/test-report.md](reports/tests/test-report.md).

## Security notes

The tool has no network access, authentication, stored data, or runtime dependencies. Input is validated and bounded. Labels containing control or format characters are rejected, and CSV cells that start with a formula character are prefixed so spreadsheets treat them as text. See [SECURITY.md](SECURITY.md) and [docs/SECURITY_AUDIT.md](docs/SECURITY_AUDIT.md).

## Attribution and license

The exercises derive from a programming course whose name and institution are not recorded in the repository. "p3" appears to abbreviate the course title. Exercise statements, and the control-flow demo that appears to be course material, belong to their original authors and are not presented as original work. The 2019 solutions were committed by Juan Manuel Ramallo. The 2026 rewrite was prepared with AI assistance and committed under his name.

No license has been declared, so default copyright applies.
