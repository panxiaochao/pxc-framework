/*
 * Copyright © 2022-2023 Lypxc (545685602@qq.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.panxiaochao.file.storage.core.sftp.factory;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.Session;
import io.github.panxiaochao.file.storage.core.sftp.utils.SftpUtil;
import io.github.panxiaochao.file.storage.properties.FileStorageProperties;
import lombok.Getter;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import java.util.Objects;

/**
 * {@code SftpFactory}
 * <p> description: sftp工厂类
 *
 * @author Lypxc
 * @since 2023-03-20
 */
@Getter
public class SftpFactory extends BasePooledObjectFactory<ChannelSftp> implements AutoCloseable {

    private Session session;

    private final FileStorageProperties fileStorageProperties;

    /**
     * 创建目标session，后续可用通过这个session不断地创建ChannelSftp
     *
     * @param fileStorageProperties 配置属性
     */
    public SftpFactory(FileStorageProperties fileStorageProperties) {
        this.fileStorageProperties = fileStorageProperties;
        // 创建Session
        String username = fileStorageProperties.getSftp().getUserName();
        String password = fileStorageProperties.getSftp().getPassword();
        String host = fileStorageProperties.getSftp().getHost();
        int port = fileStorageProperties.getSftp().getPort();
        String privateKeyFile = fileStorageProperties.getSftp().getPrivateKeyPath();
        String passphrase = fileStorageProperties.getSftp().getPassphrase();
        this.session = SftpUtil.createSession(username, password, host, port, privateKeyFile, passphrase);
    }

    @Override
    public void destroyObject(PooledObject<ChannelSftp> pooledObject) throws Exception {
        pooledObject.getObject().disconnect();
    }

    @Override
    public ChannelSftp create() {
        int timeout = fileStorageProperties.getSftp().getConnectTimeout();
        return SftpUtil.openSftpChannel(this.session, timeout);
    }

    @Override
    public PooledObject<ChannelSftp> wrap(ChannelSftp channelSftp) {
        return new DefaultPooledObject<>(channelSftp);
    }

    @Override
    public boolean validateObject(PooledObject<ChannelSftp> pooledObject) {
        return pooledObject.getObject().isConnected();
    }

    @Override
    public void close() {
        if (Objects.nonNull(session)) {
            if (session.isConnected()) {
                session.disconnect();
            }
            session = null;
        }
    }
}
