package io.github.panxiaochao.common.function;

/**
 * {@code ToBooleanBiFunction}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-10
 */
@FunctionalInterface
public interface ToBooleanBiFunction<T, U> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument.
     * @param u the second function argument.
     * @return the function result.
     */
    boolean applyAsBoolean(T t, U u);
}
