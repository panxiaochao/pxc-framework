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
package io.github.panxiaochao.springdoc.runner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.net.InetAddress;

/**
 * {@code SpringDocPrint}
 * <p> 启动信息打印
 *
 * @author Lypxc
 * @since 2022/10/26
 */
public class SpringDocPrint implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringDocPrint.class);

    @Resource
    private Environment env;

    private static final String CONTEXT_PATH = "/";

    public SpringDocPrint() {
    }

    /**
     * 启动后执行
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            // 延迟 1 秒，保证输出到结尾
            Thread.sleep(1000);
            // 获取环境变量
            String ip = InetAddress.getLocalHost().getHostAddress();
            String port = env.getProperty("server.port");
            String path = env.getProperty("server.servlet.context-path");
            if (StringUtils.isEmpty(path) || CONTEXT_PATH.equals(path)) {
                path = "";
            }
            LOGGER.info("\n----------------------------------------------------------\n\t{}{}{}{}",
                    "SpringDoc is running Success! Access URLs:",
                    "\n\tSwagger 访问网址: \thttp://" + ip + ":" + port + path + "/swagger-ui/index.html",
                    "\n\tKnife4j 访问网址: \thttp://" + ip + ":" + port + path + "/doc.html",
                    "\n----------------------------------------------------------\n");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
