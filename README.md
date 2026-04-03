# junit-mocks-assignment

This repository contains two Java/Maven projects with unit tests written for software testing coursework:

- `Scrubbing` (JUnit 5 + Mockito)
- `java-math-library-master` (JUnit-based tests for utility classes)

## Test Files Covered

### `Scrubbing/src/test/java/Tests/DigitScrubberTest.java`
Covers `DigitScrubber.scrub(...)` behavior:
- Replaces digits with `X` in normal text
- Keeps price tokens such as `100$` unchanged
- Throws `NullPointerException` for `null` input
- Throws `IllegalArgumentException` for blank input

### `Scrubbing/src/test/java/Tests/EmailScrubberTest.java`
Covers `EmailScrubber.scrub(...)` behavior:
- Replaces emails containing digits with `[EMAIL_HIDDEN]`
- Handles multiple emails in one input string
- Throws `NullPointerException` for `null` input
- Throws `IllegalArgumentException` for blank input

### `Scrubbing/src/test/java/Tests/MainScrubberTest.java`
Covers `MainScrubber.scrub(input, mode)` with mocked dependencies:
- `ScrubMode.ONLY_DIGITS`: calls only digit scrubber
- `ScrubMode.ONLY_EMAILS`: calls only email scrubber
- `ScrubMode.FULL_SCRUBBING`: calls digit scrubber then email scrubber
- Returns `null` when an internal scrubber throws an exception

### `java-math-library-master/src/test/java/de/tilman_neumann/test/junit/StringUtilTest.java`
Covers `StringUtil` methods:
- `repeat(String, int)` for null, empty, boundary, and normal cases
- `formatLeft(String, String)` for null/empty and length-relationship partitions
- `formatRight(String, String)` for null/empty and length-relationship partitions

### `java-math-library-master/src/test/java/de/tilman_neumann/test/junit/Multiset_HashMapImplTest.java`
Covers `Multiset_HashMapImpl` extensively:
- Constructors (empty, collection, array, copy)
- Core operations (`add`, `addAll`, `remove`, `removeAll`, `intersect`)
- Derived behavior (`totalCount`, `toList`, `toString`, `equals`)
- Exceptional behavior (`hashCode()` throws `IllegalStateException`)

## Requirements

- Maven installed (`mvn` available in terminal)
- Java:
  - `Scrubbing`: Java 17 (from `Scrubbing/pom.xml`)
  - `java-math-library-master`: Java release 9 target (from `java-math-library-master/pom.xml`)

## How to Run Tests

### Run all tests in `Scrubbing`
```powershell
mvn -f "Scrubbing\pom.xml" test
```

### Run specific tests in `Scrubbing`
```powershell
mvn -f "Scrubbing\pom.xml" -Dtest=Tests.DigitScrubberTest test
mvn -f "Scrubbing\pom.xml" -Dtest=Tests.EmailScrubberTest test
mvn -f "Scrubbing\pom.xml" -Dtest=Tests.MainScrubberTest test
```

### Run all tests in `java-math-library-master`
```powershell
mvn -f "java-math-library-master\pom.xml" test
```

### Run specific tests in `java-math-library-master`
```powershell
mvn -f "java-math-library-master\pom.xml" -Dtest=de.tilman_neumann.test.junit.StringUtilTest test
mvn -f "java-math-library-master\pom.xml" -Dtest=de.tilman_neumann.test.junit.Multiset_HashMapImplTest test
```

## Notes

- The tests are organized by behavior style (happy path, sad path, edge cases, and boundary-partition coverage).
- `MainScrubberTest` uses Mockito to verify interactions and call ordering across scrubber components.
