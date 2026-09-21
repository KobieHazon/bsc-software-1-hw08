# BSc Software 1 - Homework 8

- Course: Software 1.

## Contents

This homework contains two Java exercises:

- `il.ac.tau.cs.sw1.ex8.histogram` - a generic histogram backed by a hash map, with sorted iteration by descending frequency and natural-order tie breaking.
- `il.ac.tau.cs.sw1.ex8.wordsRank` - a file index that tokenizes documents, ranks words per file by frequency, and computes average/min/max rank queries across files.

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
