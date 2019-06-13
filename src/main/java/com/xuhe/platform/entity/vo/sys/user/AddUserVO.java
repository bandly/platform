package com.xuhe.platform.entity.vo.sys.user;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author liqiang
 * @date 2019/05/23
 * @description:
 */
@Data
public class AddUserVO {

    @NotEmpty(message = "登录账号不能为空")
    @Pattern(regexp="^[A-Za-z0-9]+$",message = "登录账号不符合规则")
    @Length(min = 5,max = 16,message = "登录账号长度在8到16位")
    private String username;


    @Pattern(regexp="(^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$){0,1}",message = "邮箱不符合规则")
    private String email;


    @Pattern(regexp="(^1(3|4|5|6|7|8|9)\\d{9}$){0,1}",message = "手机号码不符合规则")
    private String mobile;

    @NotEmpty(message = "密码不能为空")
    @Length(min = 6,max = 16,message = "密码长度在6到16位")
    private String password;

    @NotEmpty(message = "确认密码不能为空")
    @Length(min = 6,max = 16,message = "密码长度在6到16位")
    private String repassword;





}
