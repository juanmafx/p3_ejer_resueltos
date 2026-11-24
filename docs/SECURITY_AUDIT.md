# Security Audit

- **Date:** 2026-11-23
- **Scope:** all tracked files and the full history reachable from local and remote-tracking refs (2 commits)
- **Method:** manual source review, pattern-based secret scan, static inspection of tracked class files without executing them, and an OSV query for the direct and engine test dependencies

This was a focused review of a small codebase. It was not an independent penetration test.

## Summary

| Area | Result |
| --- | --- |
| Private keys and credentials | None found by the pattern scan in the tree or history |
| Injection (SQL, command, template) | Not applicable: no SQL execution, subprocess, or template use in the Java code |
| XSS and CSRF | Not applicable: no web interface |
| Unsafe evaluation or deserialization | None |
| Weak hashing | None: the project does not hash data |
| Runtime dependencies | None |
| Test dependencies | OSV returned no known advisories for JUnit Jupiter 5.14.4, JUnit Platform Engine 1.14.4, or jqwik 1.10.2 on 2026-11-23 |

## Findings fixed on this branch

| ID | Severity | Finding | Resolution |
| --- | --- | --- | --- |
| F-01 | Low | IDE metadata exposed a local operating system account name | Private IDE metadata removed and ignored |
| F-02 | Low | Sample code contained the author's personal details | Replaced with neutral sample data |
| F-03 | Low | Compiled class files were tracked | Removed and ignored |
| F-04 | Low | Unvalidated input could crash programs or produce invalid state | Bounded validation at construction and parsing |
| F-05 | Low | Terminal control sequences could reach output through labels | Control, format, and separator characters rejected; invalid tokens are not echoed |
| F-06 | Low | CSV output could carry spreadsheet formulas | Formula-leading cells are neutralized and CSV is quoted |
| F-07 | Low | Large simulation inputs could exhaust CPU | Run, team, and total match limits enforced |

## Supply chain controls

- Maven distribution verified against a pinned SHA-512 checksum.
- Dependencies and plugins pinned to exact versions, resolved with strict checksums.
- GitHub Actions pinned to full commit SHAs, with read-only `contents` permission and no persisted checkout credentials.
- Dependabot configured for Maven and GitHub Actions.

## Open items

| ID | Severity | Item | Required action |
| --- | --- | --- | --- |
| O-01 | Low | Git history still contains the masked account name from F-01 and the masked personal details from F-02 | Owner decision on whether to rewrite history; this branch does not rewrite it |
| O-02 | Informational | Other remote branches still hold the legacy tree until this work is merged and they are retired | Owner decision |

## Not verified

- The GitHub Actions workflow has not run on GitHub; the same script was run locally.
- Secret scanning used targeted patterns rather than a dedicated scanner.
