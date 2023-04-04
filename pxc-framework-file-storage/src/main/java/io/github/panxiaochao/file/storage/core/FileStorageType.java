package io.github.panxiaochao.file.storage.core;

import org.springframework.util.Assert;

import java.io.Serializable;

/**
 * {@code FileStorageType}
 * <p> description: 文件存储类型
 *
 * @author Lypxc
 * @since 2023-03-14
 */
public class FileStorageType implements Serializable {

    private static final long serialVersionUID = 5330567190131343864L;

    public static final FileStorageType LOCAL_STORAGE = new FileStorageType("local");
    public static final FileStorageType SFTP_STORAGE = new FileStorageType("sftp");

    private final String value;

    public FileStorageType(String value) {
        Assert.hasText(value, "value cannot be empty");
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        FileStorageType that = (FileStorageType) obj;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return getValue().hashCode();
    }
}
