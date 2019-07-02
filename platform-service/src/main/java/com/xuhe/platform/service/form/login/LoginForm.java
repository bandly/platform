package com.xuhe.platform.service.form.login;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author liqiang
 * @date 2019/06/26
 * @description:
 */
@Data
public class LoginForm {

    @NotEmpty(message = "登录账号不能为空")
    private String account;

    @NotEmpty(message = "密码不能为空")
    private String password;

    //@NotEmpty(message = "验证码不能为空")
    private String captcha;
}
