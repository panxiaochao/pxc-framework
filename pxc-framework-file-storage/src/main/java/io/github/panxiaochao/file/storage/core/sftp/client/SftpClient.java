package io.github.panxiaochao.file.storage.core.sftp.client;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.SftpProgressMonitor;
import io.github.panxiaochao.file.storage.core.sftp.factory.SftpFactory;
import io.github.panxiaochao.file.storage.enums.SftpResponseEnum;
import io.github.panxiaochao.file.storage.excetion.SftpRuntimeException;
import io.github.panxiaochao.file.storage.properties.FileStorageProperties;
import io.github.panxiaochao.file.storage.properties.nest.StorageSftp;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.util.Objects;

/**
 * {@code SftpClient}
 * <p> description: sftp客户端
 *
 * @author Lypxc
 * @since 2023-03-20
 */
@Getter
public class SftpClient implements AutoCloseable {

    private final FileStorageProperties fileStorageProperties;

    private final SftpFactory sftpFactory;

    private GenericObjectPool<ChannelSftp> objectPool;

    public SftpClient(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
        this.sftpFactory = new SftpFactory(fileStorageProperties);
        // 自定义池化配置
        GenericObjectPoolConfig<ChannelSftp> poolConfig = new GenericObjectPoolConfig<>();
        StorageSftp.Config config = fileStorageProperties.getSftp().getPoolConfig();
        poolConfig.setMaxIdle(config.getMaxIdle());
        poolConfig.setMaxTotal(config.getMaxTotal());
        poolConfig.setMinIdle(config.getMinIdle());
        poolConfig.setTestOnBorrow(config.isTestOnBorrow());
        poolConfig.setTestOnCreate(config.isTestOnCreate());
        poolConfig.setTestOnReturn(config.isTestOnReturn());
        poolConfig.setTestWhileIdle(config.isTestWhileIdle());
        poolConfig.setBlockWhenExhausted(config.isBlockWhenExhausted());
        poolConfig.setMaxWait(Duration.ofMillis(config.getMaxWaitMillis()));
        poolConfig.setTimeBetweenEvictionRuns(Duration.ofMillis(config.getTimeBetweenEvictionRunsMillis()));
        this.objectPool = new GenericObjectPool<>(this.sftpFactory, poolConfig);
    }

    /**
     * 上传文件到目标文件夹
     *
     * @param in             文件流
     * @param targetDir      目标文件夹
     * @param targetFileName 目标文件名
     * @return upload true or false
     */
    public boolean uploadFile(InputStream in, String targetDir, String targetFileName) {
        return uploadFile(in, targetDir, targetFileName, null);
    }

    /**
     * 上传文件，添加进度监视器
     *
     * @param in             文件流
     * @param targetDir      目标文件夹
     * @param targetFileName 目标文件名
     * @param monitor        上传进度模式
     * @return upload true or false
     */
    public boolean uploadFile(InputStream in, String targetDir, String targetFileName, SftpProgressMonitor monitor) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = this.objectPool.borrowObject();
            // 如果不存在目标文件夹
            if (!exist(channelSftp, targetDir)) {
                mkdirs(channelSftp, targetDir);
            }
            channelSftp.cd(targetDir);
            if (Objects.nonNull(monitor)) {
                channelSftp.put(in, targetFileName, monitor);
            } else {
                channelSftp.put(in, targetFileName);
            }
            return true;
        } catch (Exception e) {
            throw new SftpRuntimeException(SftpResponseEnum.SFTP_UPLOAD_ERROR, e);
        } finally {
            if (Objects.nonNull(channelSftp)) {
                this.objectPool.returnObject(channelSftp);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFile     远程下载文件
     * @param targetFilePath 目标文件路径
     * @return download true or false
     */
    public boolean downloadFile(String remoteFile, String targetFilePath) {
        return downloadFile(remoteFile, targetFilePath, null);
    }

    /**
     * 下载目标文件到本地
     *
     * @param remoteFile     远程下载文件
     * @param targetFilePath 目标文件路径
     * @param monitor        下载进度模式
     * @return download true or false
     */
    public boolean downloadFile(String remoteFile, String targetFilePath, SftpProgressMonitor monitor) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = this.objectPool.borrowObject();
            // 如果不存在目标文件夹
            if (!exist(channelSftp, remoteFile)) {
                // 不用下载了
                return false;
            }
            File targetFile = new File(targetFilePath);
            try (FileOutputStream outputStream = new FileOutputStream(targetFile)) {
                if (Objects.nonNull(monitor)) {
                    channelSftp.get(remoteFile, outputStream, monitor);
                } else {
                    channelSftp.get(remoteFile, outputStream);
                }
            }
            return true;
        } catch (Exception e) {
            throw new SftpRuntimeException(SftpResponseEnum.SFTP_UPLOAD_ERROR, e);
        } finally {
            if (Objects.nonNull(channelSftp)) {
                this.objectPool.returnObject(channelSftp);
            }
        }
    }

    /**
     * 下载文件
     *
     * @param remoteFile   远程下载文件
     * @param outputStream 输出流
     * @return download true or false
     */
    public boolean downloadFile(String remoteFile, OutputStream outputStream) {
        return downloadFile(remoteFile, outputStream, null);
    }

    /**
     * 下载文件
     *
     * @param remoteFile   远程下载文件
     * @param outputStream 输出流
     * @param monitor      下载进度模式
     * @return download true or false
     */
    public boolean downloadFile(String remoteFile, OutputStream outputStream, SftpProgressMonitor monitor) {
        ChannelSftp channelSftp = null;
        try {
            channelSftp = this.objectPool.borrowObject();
            // 如果不存在目标文件夹
            if (!exist(channelSftp, remoteFile)) {
                // 不用下载了
                return false;
            }
            if (Objects.nonNull(monitor)) {
                channelSftp.get(remoteFile, outputStream, monitor);
            } else {
                channelSftp.get(remoteFile, outputStream);
            }
            return true;
        } catch (Exception e) {
            throw new SftpRuntimeException(SftpResponseEnum.SFTP_UPLOAD_ERROR, e);
        } finally {
            if (Objects.nonNull(channelSftp)) {
                this.objectPool.returnObject(channelSftp);
            }
        }
    }

    /**
     * 创建文件夹
     *
     * @param channelSftp sftp通道
     * @param dir         目录
     * @return 是否成功
     */
    private boolean mkdirs(ChannelSftp channelSftp, String dir) {
        try {
            String pwd = channelSftp.pwd();
            if (StringUtils.contains(pwd, dir)) {
                return true;
            }
            String relativePath = StringUtils.substringAfter(dir, pwd);
            String[] dirs = StringUtils.splitByWholeSeparatorPreserveAllTokens(relativePath, "/");
            for (String path : dirs) {
                if (StringUtils.isBlank(path)) {
                    continue;
                }
                try {
                    channelSftp.cd(path);
                } catch (SftpException e) {
                    channelSftp.mkdir(path);
                    channelSftp.cd(path);
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判断文件夹是否存在
     *
     * @param channelSftp sftp通道
     * @param dir         目录
     * @return 是否成功
     */
    private boolean exist(ChannelSftp channelSftp, String dir) {
        try {
            channelSftp.lstat(dir);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void close() {
        // 销毁链接池
        if (Objects.nonNull(this.objectPool)) {
            if (!this.objectPool.isClosed()) {
                this.objectPool.close();
            }
        }
        this.objectPool = null;
        // 销毁sftpFactory
        if (Objects.nonNull(this.sftpFactory)) {
            this.sftpFactory.close();
        }
    }

}
