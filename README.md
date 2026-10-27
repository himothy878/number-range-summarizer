# Number Range Summarizer

My solution to the impact.com take-home assessment. It implements the provided
`NumberRangeSummarizer` interface, which takes a comma separated list of numbers and groups
sequential numbers into ranges.

```
Input:  1,3,6,7,8,12,13,14,15,21,22,23,24,31
Output: 1, 3, 6-8, 12-15, 21-24, 31
```

## How it works

- `NumberRangeSummarizerImpl` implements the interface.
  - `collect` splits the input on commas and converts each value to an `Integer` (using a stream).
  - `summarizeCollection` sorts the numbers and removes duplicates, then goes through them once,
    grouping numbers that follow on from each other into a `Range`.
- `Range` holds a start and end number and knows how to print itself (`5` or `6-8`).

## Assumptions

These are also written as comments above the matching unit tests.

1. The example in the interface says `13-15`, but the input has 12, 13, 14 and 15, so I assumed
   this is a typo and the correct output is `12-15`.
2. Two numbers next to each other count as a range (`6,7` gives `6-7`).
3. The input might not be sorted, so the numbers are sorted first.
4. Duplicate numbers are only counted once.
5. Spaces around numbers are allowed.
6. Negative numbers are allowed (`-3,-2,-1` gives `-3--1`).
7. Null or empty input returns an empty result instead of an error.
8. Anything that is not a whole number (e.g. `abc`, `2.5`, or an empty value like `1,,3`) throws an
   `IllegalArgumentException`.

## Running the tests

Requires Java 8 or later and Maven.

```bash
mvn test
```
