package io.github.panxiaochao.captcha.enums;

import io.github.panxiaochao.captcha.utils.CaptchaSymbolPool;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

/**
 * <p>
 * 验证码内容类型.
 * </p>
 *
 * @author Lypxc
 * @since 2024-08-07
 * @version 1.0
 */
@Getter
@AllArgsConstructor
public enum CaptchaType {

	/**
	 * 字母数字混合
	 */
	CHAR_NUMBER(1, CaptchaSymbolPool.CHAR_NUMBER_MIX),

	/**
	 * 纯数字
	 */
	NUMBER(2, CaptchaSymbolPool.NUMBER),

	/**
	 * 纯大写字母
	 */
	CHAR_UPPER(3, CaptchaSymbolPool.CHAR_UPPER),

	/**
	 * 纯小写字母
	 */
	CHAR_LOWER(4, CaptchaSymbolPool.CHAR_LOWER),

	/**
	 * 中文
	 */
	MODERN_CHINESE(5, CaptchaSymbolPool.MODERN_CHINESE);

	private final int index;

	private final char[] source;

	public char[] of(CaptchaType captchaType) {
		if (Optional.ofNullable(captchaType).isPresent()) {
			for (CaptchaType captchaTypeObj : values()) {
				if (captchaTypeObj.equals(captchaType)) {
					return captchaTypeObj.getSource();
				}
			}
		}
		throw new NullPointerException("CaptchaType is null!");
	}

}
