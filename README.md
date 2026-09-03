# BSc Software 1 - Homework 8

A historical archive of my CS BSc coursework.

## Contents

This homework contains two Java exercises:

- `il.ac.tau.cs.sw1.ex8.histogram` - a generic histogram backed by a hash map, with sorted iteration by descending frequency and natural-order tie breaking.
- `il.ac.tau.cs.sw1.ex8.wordsRank` - a file index that tokenizes documents, ranks words per file by frequency, and computes average/min/max rank queries across files.

## Provenance

- Era: CS BSc.
- Last recovered work: May 2018.
- Original handout status: the exact matching Homework 8 handout was not recovered.
- Maintenance changes: the current version adds synthetic fixtures, strict local validation, deterministic file ordering, safer histogram edge-case handling, and a case-normalization fix in file-rank lookup.

## Tech Stack

- Java 8 language features, validated with Java 11 or newer.
- Plain `javac` and `java`; no external dependencies.
- `make` for repeatable compile, test, and cleanup commands.

## Run

```bash
make test
```

To remove generated files:

```bash
make clean
```
