package com.xuhe.platform.service.context;

import lombok.Data;

import java.io.Serializable;

/**
 * @author liqiang
 * @date 2019/06/25
 * @description: 权限用户信息
 */
@Data
public class AuthUser implements Serializable {


    private String userId;
    private String name;
    private String avatar;
    private String email;
    private String accessToken;



}
