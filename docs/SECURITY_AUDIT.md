# Security Audit

- **Date:** 2026-11-24
- **Scope:** all tracked files on `modernize/2026` and the full history reachable from local and remote-tracking refs
- **Method:** manual source review, gitleaks 8.30.1 with redaction over the history and working tree, an OSV query for every JAR used by a clean build and bundled with the pinned Maven distribution, static bytecode inspection of the legacy class files without executing them, and a seeded fuzz run of 400,000 random CLI argument vectors

This was a focused review of a small codebase. It was not an independent penetration test.

## Summary

| Area | Result |
| --- | --- |
| Secrets | gitleaks reported no findings in the history or working tree |
| Injection (SQL, command, template) | Not applicable: no SQL execution, subprocess, or template use in the Java code |
| XSS and CSRF | Not applicable: no web interface |
| Authentication and 2FA | Not applicable: no accounts, sessions, or stored credentials |
| Cryptography | Not applicable: the project does not hash or encrypt data, and its seeded generator is not used for security |
| Unsafe evaluation or deserialization | None |
| Runtime dependencies | None |
| Build and test dependencies | OSV returned no known advisories on 2026-11-24 for the 67 JARs used by the clean build or the 50 JARs bundled with Maven 3.10.2 |
| CLI fuzzing | No uncaught exceptions, unexpected exit codes, control characters in output, or partial output on failure |

## Findings fixed on this branch

| ID | Severity | Finding | Resolution |
| --- | --- | --- | --- |
| F-01 | Low | IDE metadata exposed a local operating system account name | Private IDE metadata removed and ignored |
| F-02 | Low | Sample code contained the author's personal details | Replaced with neutral sample data |
| F-03 | Low | Compiled class files were tracked | Removed and ignored |
| F-04 | Low | Unvalidated input could crash programs or produce invalid state | Bounded validation at construction and parsing |
| F-05 | Low | Terminal control sequences could reach output through labels | Control, format, and separator characters rejected; invalid tokens are not echoed |
| F-06 | Low | CSV output could carry spreadsheet formulas | Formula-leading cells, including full-width prefixes, are neutralized and CSV is quoted |
| F-07 | Low | Large simulation inputs could exhaust CPU | Run, team, and total match limits enforced in the library and the CLI |
| F-08 | High advisory, build time only | The clean and resources plugins resolved `plexus-utils` versions affected by CVE-2025-67030 (directory traversal in `Expand`) | Plugin dependencies overridden to fixed versions 4.0.3 and 3.6.1 |
| F-09 | Low | CI could publish the stale committed test report after a failed run | The report artifact is uploaded only on success; raw Surefire output is uploaded on failure |
| F-10 | Low | Labels made only of Unicode space characters passed the blank check | Rejected as blank |
| F-11 | Low | Failed commands could write partial output before exiting with an error | Input is fully validated before output starts |
| F-12 | Low | Numeric arguments accepted Java-only forms such as `1d` and `0x1p3` | Only plain decimal notation is accepted |

## Supply chain controls

- Maven distribution verified against a pinned SHA-512 checksum, which matches the value published on Maven Central.
- Direct dependencies and all build plugins pinned to exact versions, resolved with strict checksums.
- GitHub Actions pinned to full commit SHAs that match their upstream release tags, with read-only `contents` permission and no persisted checkout credentials.
- Dependabot configured for Maven and GitHub Actions.

## Items resolved after the audit

| ID | Severity | Item | Resolution |
| --- | --- | --- | --- |
| O-01 | Low | Git history contained the masked account name from F-01 and the masked personal details from F-02 | Resolved by the 2026-11-24 history rewrite: pre-2026 commits are no longer in any branch or tag. Old commits may remain reachable by direct SHA on GitHub until unreferenced objects are purged, so anything that was ever published should still be treated as exposed |
| O-02 | Informational | Remote branches `master`, `dev`, and `stg` held the legacy tree | Resolved: all three branches point at the released 2026 code since 2026-11-24 |

## Not verified

- Only JDK 17 was tested.
- Whether the build ever reached the vulnerable `plexus-utils` code path before F-08 was fixed.
- OSV reports only published advisories. A clean result does not prove the absence of vulnerabilities.
