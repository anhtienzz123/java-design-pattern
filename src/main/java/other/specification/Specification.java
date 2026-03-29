package other.specification;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Specification Pattern - Generic specification interface.
 *
 * The Specification pattern encapsulates business rules that can be combined
 * using boolean logic (AND, OR, NOT) to create complex selection criteria.
 *
 * @param <T> the type of object this specification applies to
 */
@FunctionalInterface
public interface Specification<T> {

    /**
     * Checks if the candidate satisfies this specification.
     *
     * @param candidate the object to check
     * @return true if the candidate satisfies the specification
     */
    boolean isSatisfiedBy(T candidate);

    /**
     * Combines this specification with another using logical AND.
     *
     * @param other the other specification
     * @return a new specification that is satisfied when both are satisfied
     */
    default Specification<T> and(Specification<T> other) {
        return candidate -> this.isSatisfiedBy(candidate) && other.isSatisfiedBy(candidate);
    }

    /**
     * Combines this specification with another using logical OR.
     *
     * @param other the other specification
     * @return a new specification that is satisfied when either is satisfied
     */
    default Specification<T> or(Specification<T> other) {
        return candidate -> this.isSatisfiedBy(candidate) || other.isSatisfiedBy(candidate);
    }

    /**
     * Negates this specification.
     *
     * @return a new specification that is satisfied when this one is not
     */
    default Specification<T> not() {
        return candidate -> !this.isSatisfiedBy(candidate);
    }

    /**
     * Converts this specification to a Java Predicate for use with Streams.
     *
     * @return a Predicate equivalent to this specification
     */
    default Predicate<T> toPredicate() {
        return this::isSatisfiedBy;
    }

    /**
     * Filters a collection using this specification.
     *
     * @param candidates the collection to filter
     * @return a list of candidates that satisfy this specification
     */
    default List<T> filter(Collection<T> candidates) {
        return candidates.stream()
                .filter(this::isSatisfiedBy)
                .toList();
    }

    /**
     * Checks if any candidate in the collection satisfies this specification.
     *
     * @param candidates the collection to check
     * @return true if any candidate satisfies the specification
     */
    default boolean anyMatch(Collection<T> candidates) {
        return candidates.stream().anyMatch(this::isSatisfiedBy);
    }

    /**
     * Checks if all candidates in the collection satisfy this specification.
     *
     * @param candidates the collection to check
     * @return true if all candidates satisfy the specification
     */
    default boolean allMatch(Collection<T> candidates) {
        return candidates.stream().allMatch(this::isSatisfiedBy);
    }

    /**
     * Counts how many candidates satisfy this specification.
     *
     * @param candidates the collection to count
     * @return the number of candidates that satisfy the specification
     */
    default long count(Collection<T> candidates) {
        return candidates.stream().filter(this::isSatisfiedBy).count();
    }
}
