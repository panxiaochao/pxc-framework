package io.github.panxiaochao.captcha.generator;

/**
 * <p>
 * 验证码生成器.
 * </p>
 *
 * @author Lypxc
 * @since 2024-08-07
 * @version 1.0
 */
public interface ICaptchaGenerator {

	/**
	 * 生成验证码.
	 */
	String generateCode();

	/**
	 * 验证用户输入的字符串是否与生成的验证码匹配
	 * @param code 生成的随机验证码
	 * @param inputCode 用户输入的验证码
	 * @return 是否验证通过 true or false
	 */
	boolean verify(String code, String inputCode);

}
