package com.xuhe.platform.web.controller;

import com.xuhe.platform.common.enums.ResultCode;
import com.xuhe.platform.common.exception.BusinessException;
import com.xuhe.platform.common.result.PlatformResult;
import com.xuhe.platform.common.result.Result;
import com.xuhe.platform.service.form.login.LoginForm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @author liqiang
 * @date 2019/06/26
 * @description: 登录相关
 */
@RestController
public class SysLoginController {


    @GetMapping(value = "captcha.jpg")
    public void captcha(HttpServletResponse response){
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

       /* //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtils.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);*/
    }

    @PostMapping(value = "/sys/login")
    public Result login(@Valid  LoginForm loginForm){
        UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getAccount(),loginForm.getPassword());
        Subject currentUser = SecurityUtils.getSubject();
        //登录
        currentUser.login(token);
        if(currentUser.isAuthenticated()){
            String jwtToken = (String)currentUser.getPrincipal();
            System.out.println("jwtToken:"+jwtToken);
            currentUser.hasRole("sss");
            return PlatformResult.success(jwtToken);
        }else{
            throw new BusinessException(ResultCode.USER_LOGIN_ERROR);
        }
    }

    @GetMapping(value = "/sys/logout")
    public void logout(){

    }

}
