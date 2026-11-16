# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Educational repository of Java design patterns (GoF plus extras such as Retry, Specification, Intercepting Filter), each shown through one or more real-world use cases. Maven project compiled with `maven.compiler.release=26`.

Libraries (see `pom.xml`): Lombok (annotation processor, `provided`), JUnit Jupiter 6 and AssertJ (test scope). `.mvn/jvm.config` silences Lombok's `sun.misc.Unsafe` warning on JDK 26.

## Build and Run

```bash
mvn clean compile
java -cp target/classes behavioral.chainofresponsibility.approvalsystem.ZMain
```

Every use case has a runnable `ZMain` class; swap in its fully qualified name.

### Testing
New tests go in `src/test/java` (same package as the code) using JUnit Jupiter + AssertJ, and run with `mvn test`. Tests are optional — the `ZMain` demo is the main way to show a pattern.

The older `behavioral/interpreter/booleanlogic/BooleanLogicInterpreterTest.java` is a plain `main()` class under `src/main`:

```bash
java -cp target/classes behavioral.interpreter.booleanlogic.BooleanLogicInterpreterTest
```

## Code Structure

```
src/main/java/<category>/<pattern>/<usecase>/
    *.java        # pattern classes for this use case
    ZMain.java    # runnable demo
    README.md     # explanation (for non-trivial patterns)
```

- `<category>` is `behavioral`, `creational`, `structural`, or `other` (non-GoF patterns).
- Examples: `behavioral/state/trafficlight/`, `creational/singleton/loggersystem/`, `other/interceptingfilter/apigateway/`.
- A pattern can have several use cases side by side (e.g. `structural/adapter/database/`, `structural/adapter/paymentsystem/`).
- Exception: `other/retry/`, `other/specification/`, and `other/stepbuilder/` keep their files directly in the pattern package. This is intentional — leave them as they are.

## Adding a Pattern or Use Case

1. Create `<category>/<pattern>/<usecase>/` — every new use case gets its own sub-package.
2. Pick a simple, familiar real-world scenario.
3. Add a `ZMain.java` demo that prints what is happening.
4. Add a short `README.md` (intent, problem, solution, structure, participants).
5. Compile with `mvn clean compile` and run the `ZMain` to check the output.

## Coding Guidelines

The repo exists to learn the **idea** of each pattern, so keep implementations minimal:

- Write only the classes and methods needed to show the pattern's participants and how they interact. Skip anything that doesn't help explain the pattern.
- Avoid production extras unless they are the point of the pattern: no input validation, security hardening, logging frameworks, configuration, or edge-case handling. Thread safety only where the pattern is about it (e.g. Singleton).
- Keep the scenario small: a few concrete classes, and a short `ZMain` that clearly shows the pattern working.
- Keep READMEs short: intent, problem, solution, structure diagram, participants.
- Use modern Java (records, sealed types, switch expressions, pattern matching) where it makes the code shorter and clearer.

### Lombok
- Prefer a `record` for simple immutable data; use Lombok only for regular classes (`@Getter`, `@RequiredArgsConstructor`, `@ToString`).
- Never let Lombok generate the pattern itself: no `@Builder` in Builder / Step Builder examples, no `@Delegate` in Proxy / Decorator / Adapter examples. The pattern code must stay visible and hand-written.
- Avoid `@Data`, `@SneakyThrows`, and `@Slf4j` (no logging framework; demos use `System.out`).
