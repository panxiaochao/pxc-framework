package io.github.panxiaochao.core.utils;

/**
 * <p>
 * JDK环境判断工具类
 * </p>
 *
 * @author Lypxc
 * @since 2023-08-09
 */
public class JdkUtil {

	/**
	 * JDK版本
	 */
	public static final int JVM_VERSION;

	/**
	 * 是否JDK8
	 */
	public static final boolean IS_JDK8;

	/**
	 * 是否大于JDK8
	 */
	public static final boolean IS_GT_JDK8;

	/**
	 * 是否大于等于JDK17
	 */
	public static final boolean IS_GTE_JDK17;

	/**
	 * 是否Android环境
	 */
	public static final boolean IS_ANDROID;

	static {
		int jvmVersion = -1;
		boolean isAndroid = false;

		try {
			String jmvName = System.getProperty("java.vm.name");
			isAndroid = jmvName.equals("Dalvik");
			String javaSpecVer = System.getProperty("java.specification.version");
			if (javaSpecVer.startsWith("1.")) {
				javaSpecVer = javaSpecVer.substring(2);
			}
			if (javaSpecVer.indexOf('.') == -1) {
				jvmVersion = Integer.parseInt(javaSpecVer);
			}
		}
		catch (Exception e) {
			// skip
			jvmVersion = 8;
		}

		// SET JVM VERSION
		JVM_VERSION = jvmVersion;
		IS_JDK8 = (8 == jvmVersion);
		IS_GT_JDK8 = jvmVersion > 8;
		IS_GTE_JDK17 = jvmVersion >= 17;
		IS_ANDROID = isAndroid;
	}

}
