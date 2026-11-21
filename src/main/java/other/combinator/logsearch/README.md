# Combinator Pattern — Log Search

## Intent

Build complex behaviour from a few **primitives** plus **combinators**: functions that take values of a type and return a new value of the *same* type, so results can be combined again and again.

## Problem

Every new log search becomes another hand-written loop:

```java
List<String> paymentErrors(String log) {
    List<String> result = new ArrayList<>();
    for (String line : log.lines().toList())
        if (line.contains("ERROR") && line.contains("payment")) result.add(line);
    return result;
}
// ...and again for errorsWithoutTimeout(), errorsOrWarnings(), ...
```

## Solution

Define one type, `Finder` (text → matching lines), one primitive, `contains(word)`, and combinators that turn Finders into Finders:

```java
Finder paymentErrors = Finder.contains("error").and(Finder.contains("payment"));
Finder realErrors    = Finder.contains("error").except(Finder.contains("timeout"));

paymentErrors.find(log);
```

Because every combinator returns a `Finder`, new helpers can be built from the old ones, e.g. `containsAll(words...)` is just `contains` reduced with `and`.

## Structure

```
              Finder  (String text -> List<String> lines)
                 |
   primitive:    contains(word)
   combinators:  and(Finder)  or(Finder)  except(Finder)
   derived:      containsAll(...) = contains . and . and ...
                 containsAny(...) = contains . or  . or  ...
```

## Participants

| Element | Role |
|---------|------|
| `Finder` | The combinable type (a functional interface) |
| `contains` | Primitive — the only Finder that touches the text directly |
| `and`, `or`, `except` | Combinators — take Finders, return a Finder |
| `containsAll`, `containsAny` | Higher-level Finders built only from combinators |
| `ZMain` | Client that composes searches over a sample log |

## Run

```bash
java -cp target/classes other.combinator.logsearch.ZMain
```

## Real-World Examples

`Predicate.and/or/negate`, `Function.andThen/compose`, `Comparator.comparing(...).thenComparing(...)`, parser combinator libraries.
