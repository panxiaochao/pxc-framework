package io.github.panxiaochao.file.storage.context;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 * {@code Context}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-14
 */
public interface Context {
    /**
     * Returns the value of the attribute associated to the key.
     *
     * @param key the key for the attribute
     * @param <V> the type of the value for the attribute
     * @return the value of the attribute associated to the key, or {@code null} if not available
     */
    @Nullable
    <V> V get(Object key);

    /**
     * Returns the value of the attribute associated to the key.
     *
     * @param key the key for the attribute
     * @param <V> the type of the value for the attribute
     * @return the value of the attribute associated to the key, or {@code null} if not available or not of the specified type
     */
    @Nullable
    default <V> V get(Class<V> key) {
        Assert.notNull(key, "key cannot be null");
        V value = get((Object) key);
        return key.isInstance(value) ? value : null;
    }

    /**
     * Returns {@code true} if an attribute associated to the key exists, {@code false} otherwise.
     *
     * @param key the key for the attribute
     * @return {@code true} if an attribute associated to the key exists, {@code false} otherwise
     */
    boolean hasKey(Object key);
}
