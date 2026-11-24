#!/usr/bin/env bash
set -euo pipefail

version="3.10.2"
sha512="57d2db6b37e7582119137ade544557bba741b29e62109ef81539a2bdfd6d0e641fdd5d8edb014af1816d0510deac6fac8f347433b3a1aa36316ba85aa77b8119"
root="$(cd "$(dirname "${BASH_SOURCE[0]}")/.." && pwd)"
tools_dir="${MAVEN_TOOLS_DIR:-$root/.tools}"
maven_home="$tools_dir/apache-maven-$version"

if [[ ! -x "$maven_home/bin/mvn" ]]; then
  mkdir -p "$tools_dir"
  archive="$(mktemp "$tools_dir/maven.XXXXXX")"
  trap 'rm -f "$archive"' EXIT
  curl --fail --silent --show-error --location --proto '=https' --tlsv1.2 \
    --output "$archive" \
    "https://repo1.maven.org/maven2/org/apache/maven/apache-maven/$version/apache-maven-$version-bin.tar.gz"
  echo "$sha512  $archive" | sha512sum --check --status
  tar -xzf "$archive" -C "$tools_dir"
fi

printf '%s\n' "$maven_home/bin/mvn"
