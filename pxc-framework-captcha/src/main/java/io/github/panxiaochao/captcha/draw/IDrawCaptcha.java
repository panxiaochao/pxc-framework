package io.github.panxiaochao.captcha.draw;

import java.io.OutputStream;
import java.io.Serializable;

/**
 * <p>
 * 绘画验证码基类.
 * </p>
 *
 * @author Lypxc
 * @since 2024-08-12
 * @version 1.0
 */
public interface IDrawCaptcha extends Serializable {

	/**
	 * 获取验证码的内容
	 */
	String getCaptchaCode();

	/**
	 * 将验证码写出入到目标流中
	 * @param out 目标流
	 */
	void writeTo(OutputStream out);

	/**
	 * 获取图形验证码图片bytes
	 */
	byte[] getImageBytes();

	/**
	 * 获得图片的Base64形式
	 */
	String getImageBase64();

	/**
	 * 获取图片的Base64Data格式
	 */
	String getImageBase64Data();

}
