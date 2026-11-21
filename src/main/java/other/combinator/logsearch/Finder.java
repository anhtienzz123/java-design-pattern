package other.combinator.logsearch;

import java.util.Arrays;
import java.util.List;

/**
 * The combinator type: a function from log text to the lines it matches.
 * <p>
 * {@link #contains(String)} is the only primitive. Everything else is a
 * combinator that takes Finders and returns a new Finder.
 */
@FunctionalInterface
public interface Finder {

    List<String> find(String text);

    // --- Primitive ---

    static Finder contains(String word) {
        return text -> text.lines()
                .filter(line -> line.toLowerCase().contains(word.toLowerCase()))
                .toList();
    }

    // --- Combinators ---

    /** Lines matched by this Finder AND by the other one. */
    default Finder and(Finder other) {
        return text -> other.find(String.join("\n", this.find(text)));
    }

    /** Lines matched by this Finder OR by the other one (original order kept). */
    default Finder or(Finder other) {
        return text -> {
            List<String> left = this.find(text);
            List<String> right = other.find(text);
            return text.lines().filter(line -> left.contains(line) || right.contains(line)).toList();
        };
    }

    /** Lines matched by this Finder but NOT by the other one. */
    default Finder except(Finder other) {
        return text -> {
            List<String> excluded = other.find(text);
            return this.find(text).stream().filter(line -> !excluded.contains(line)).toList();
        };
    }

    // --- Higher-level Finders built only from the combinators above ---

    static Finder containsAll(String... words) {
        return Arrays.stream(words).map(Finder::contains).reduce(Finder::and).orElseThrow();
    }

    static Finder containsAny(String... words) {
        return Arrays.stream(words).map(Finder::contains).reduce(Finder::or).orElseThrow();
    }
}
