/*
 * Copyright © 2022-2024 Lypxc (545685602@qq.com)
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
package io.github.panxiaochao.weixin.config.properties.nested;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *
 * <p>
 * 微信开放平台配置属性
 * </p>
 *
 * @author Lypxc
 * @since 2024-12-13
 */
@Getter
@Setter
public class WxOpenProperties {

	/**
	 * 是否开启
	 */
	private boolean enabled;

	/**
	 * 微信开放平台配置
	 */
	private WxOpenConfig config;

	/**
	 * 基础信息配置
	 */
	@Getter
	@Setter
	public static class WxOpenConfig {

		/**
		 * 微信三方平台的appid
		 */
		private String componentAppId;

		/**
		 * 微信三方平台的app secret
		 */
		private String componentSecret;

		/**
		 * 微信三方平台的token
		 */
		private String componentToken;

		/**
		 * 微信三方平台的EncodingAESKey
		 */
		private String componentAesKey;

		/**
		 * 授权 URL
		 */
		private String authorizeUrl;

		/**
		 * 微信公众号 AppId
		 */
		private String wxMpAppId;

		/**
		 * 微信小程序 AppId
		 */
		private String wxMaAppId;

	}

	/**
	 * 配置多个消息处理器
	 */
	private List<WxMpProperties.MpHandler> handlers;

}
