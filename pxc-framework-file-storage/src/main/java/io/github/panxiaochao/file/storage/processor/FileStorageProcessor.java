package io.github.panxiaochao.file.storage.processor;

import io.github.panxiaochao.file.storage.context.FileStorageContext;
import io.github.panxiaochao.file.storage.meta.FileMetadata;
import org.springframework.lang.Nullable;

/**
 * {@code FileStorageProcessor}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-14
 */
public interface FileStorageProcessor<T extends FileMetadata> {

    /**
     * FileStorageContext
     *
     * @param storageContext return {@code FileStorageContext}
     * @return true or false
     */
    @Nullable
    T process(FileStorageContext storageContext);
}
