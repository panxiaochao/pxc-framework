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
package io.github.panxiaochao.core.exception;

import io.github.panxiaochao.core.ienums.IEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * {@code ServerException}
 * <p>
 * 基础错误异常类
 *
 * @author Lypxc
 * @since 2022/4/19
 */
@Getter
@Setter
public class ServerException extends Exception {

  private static final long serialVersionUID = 9012390889969142663L;

  /**
   * 自定义枚举
   */
  private IEnum<Integer> responseEnum;

  /**
   * 参数
   */
  private Object[] args;

  private Integer code;

  private String errMsg;

  public ServerException() {
    super();
  }

  public ServerException(IEnum<Integer> responseEnum, Object[] args, String errMsg) {
    super(errMsg);
    this.responseEnum = responseEnum;
    this.args = args;
    this.errMsg = errMsg;
    this.code = responseEnum.getCode();
  }

  public ServerException(IEnum<Integer> responseEnum, Object[] args, String errMsg,
      Throwable cause) {
    super(errMsg, cause);
    this.responseEnum = responseEnum;
    this.args = args;
    this.errMsg = errMsg;
    this.code = responseEnum.getCode();
  }

  public ServerException(int code, String errMsg) {
    super(errMsg);
    this.code = code;
    this.errMsg = errMsg;
  }

  public ServerException(int code, String errMsg, Throwable cause) {
    super(errMsg, cause);
    this.code = code;
    this.errMsg = errMsg;
  }

}
