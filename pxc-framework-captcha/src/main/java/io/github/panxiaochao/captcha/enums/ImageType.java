package io.github.panxiaochao.captcha.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>
 * 验证码生成格式类型.
 * </p>
 *
 * @author Lypxc
 * @since 2024-08-13
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum ImageType {

	JPG("jpg", "data:image/jpg;base64", "image/jpg"),

	JPEG("jpeg", "data:image/jpeg;base64", "image/jpeg"),

	PNG("png", "data:image/png;base64", "image/png"),

	GIF("gif", "data:image/gif;base64", "image/gif");

	private final String suffix;

	private final String imageData;

	private final String contentType;

}
