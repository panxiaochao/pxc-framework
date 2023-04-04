package io.github.panxiaochao.file.storage.processor;

import io.github.panxiaochao.file.storage.context.FileStorageContext;
import io.github.panxiaochao.file.storage.meta.FileMetadata;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * {@code DelegatingFileStorageProcessor}
 * <p> description:
 *
 * @author Lypxc
 * @since 2023-03-14
 */
public class DelegatingFileStorageProcessor implements FileStorageProcessor<FileMetadata> {

    private final List<FileStorageProcessor<FileMetadata>> processors;

    /**
     * Constructs a {@code DelegatingFileStorageProcessor} using the provided parameters.
     *
     * @param processors a {@code List} of {@link FileStorageProcessor}(s)
     */
    @SafeVarargs
    public DelegatingFileStorageProcessor(FileStorageProcessor<? extends FileMetadata>... processors) {
        Assert.notEmpty(processors, "processors cannot be empty");
        this.processors = Collections.unmodifiableList(asList(processors));
    }

    /**
     * FileStorageContext
     *
     * @param storageContext return {@code FileStorageContext}
     * @return the FileMetadata
     */
    @Override
    public FileMetadata process(FileStorageContext storageContext) {
        Assert.notNull(storageContext, "storageContext cannot be null");
        for (FileStorageProcessor<FileMetadata> processor : this.processors) {
            FileMetadata fileMetadata = processor.process(storageContext);
            if (Objects.nonNull(fileMetadata)) {
                return fileMetadata;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private static List<FileStorageProcessor<FileMetadata>> asList(FileStorageProcessor<? extends FileMetadata>... processors) {
        List<FileStorageProcessor<FileMetadata>> processorList = new ArrayList<>();
        for (FileStorageProcessor<? extends FileMetadata> processor : processors) {
            processorList.add((FileStorageProcessor<FileMetadata>) processor);
        }
        return processorList;
    }
}
