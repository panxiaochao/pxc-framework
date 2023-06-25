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
package io.github.panxiaochao.common.jackson;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * {@code CustomizeJavaTimeModule}
 * <p> description: jackson TimeModule
 *
 * @author Lypxc
 * @since 2023-06-06
 */
public class CustomizeJavaTimeModule extends SimpleModule {

    private static final long serialVersionUID = -7425869912487751834L;

    /**
     * 默认日期时间格式
     */
    private static final String LOCAL_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    /**
     * 默认日期格式
     */
    private static final String LOCAL_DATE_FORMAT = "yyyy-MM-dd";
    /**
     * 默认时间格式
     */
    private static final String DATE_TIME_FORMAT = "HH:mm:ss";

    public CustomizeJavaTimeModule() {
        super(PackageVersion.VERSION);
        this.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT)));
        this.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT)));
        this.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        this.addSerializer(Instant.class, InstantSerializer.INSTANCE);
        this.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(LOCAL_DATE_TIME_FORMAT)));
        this.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(LOCAL_DATE_FORMAT)));
        this.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT)));
        this.addDeserializer(Instant.class, InstantDeserializer.INSTANT);
    }
}
