package io.github.panxiaochao.jackson.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.github.panxiaochao.core.utils.date.DatePattern;
import io.github.panxiaochao.core.utils.jackson.CustomizeJavaTimeModule;
import io.github.panxiaochao.core.utils.jackson.jsonserializer.NullValueJsonSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.annotation.Bean;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

/**
 * {@code Jackson2AutoConfiguration}
 * <p> Jackson2 自动化配置
 * <pre>
 *     注册顺序：
 *     JacksonAutoConfiguration
 *     ObjectMapper
 *     Jackson2ObjectMapperBuilder
 *     Jackson2ObjectMapperBuilderCustomizer
 * </pre>
 *
 * <p>参考：<a href="https://codingnconcepts.com/spring-boot/customize-jackson-json-mapper/">customize-jackson-json-mapper</a><a></a></p>
 *
 * @author Lypxc
 * @since 2023-06-26
 */
@AutoConfiguration(before = JacksonAutoConfiguration.class)
public class Jackson2AutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(Jackson2AutoConfiguration.class);

    /**
     * to override the default ObjectMapper (and XmlMapper)
     * <pre>
     *     1.Jackson2ObjectMapperBuilderCustomizer 注册Bean
     *     2.生成Bean Jackson2ObjectMapperBuilder
     *     3.通过 Jackson2ObjectMapperBuilder 生成 ObjectMapper
     * </pre>
     *
     * @return custom Jackson2ObjectMapperBuilderCustomizer
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jsonCustomizer() {
        LOGGER.info("配置[Jackson2ObjectMapper]成功！");
        return builder -> builder
                .locale(Locale.CHINA)
                // 所有字段全部展现
                .serializationInclusion(JsonInclude.Include.ALWAYS)
                .timeZone(TimeZone.getDefault())
                .dateFormat(new SimpleDateFormat(DatePattern.NORMAL_DATE_TIME_PATTERN))
                // 忽略空Bean转json的错误
                .failOnEmptyBeans(false)
                // 忽略未知属性
                .failOnUnknownProperties(false)
                // 自定义 NUll 处理
                .postConfigurer(objectMapper -> objectMapper
                        .getSerializerProvider()
                        .setNullValueSerializer(NullValueJsonSerializer.INSTANCE))
                // 时间格式化处理
                .modules(new CustomizeJavaTimeModule());
    }
}