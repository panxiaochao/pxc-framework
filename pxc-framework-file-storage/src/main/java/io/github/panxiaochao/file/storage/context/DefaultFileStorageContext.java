package io.github.panxiaochao.file.storage.context;

import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code DefaultFileStorageContext}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-14
 */
public class DefaultFileStorageContext implements FileStorageContext {

    private final Map<Object, Object> context;

    private DefaultFileStorageContext(Map<Object, Object> context) {
        this.context = Collections.unmodifiableMap(new HashMap<>(context));
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public <V> V get(Object key) {
        return hasKey(key) ? (V) this.context.get(key) : null;
    }

    @Override
    public boolean hasKey(Object key) {
        Assert.notNull(key, "key cannot be null");
        return this.context.containsKey(key);
    }

    /**
     * Returns a new {@link Builder}.
     *
     * @return the {@link Builder}
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * A builder for {@link DefaultFileStorageContext}.
     */
    public static final class Builder extends AbstractBuilder<DefaultFileStorageContext, Builder> {

        private Builder() {
        }

        /**
         * Builds a new {@link DefaultFileStorageContext}.
         *
         * @return the {@link DefaultFileStorageContext}
         */
        public DefaultFileStorageContext build() {
            return new DefaultFileStorageContext(getContext());
        }

    }
}
