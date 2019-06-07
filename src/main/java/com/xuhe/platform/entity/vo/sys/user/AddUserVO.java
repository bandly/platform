package com.xuhe.platform.entity.vo.sys.user;

import lombok.Data;

import java.util.Date;

/**
 * @author liqiang
 * @date 2019/05/23
 * @description:
 */
@Data
public class AddUserVO {

    private Long userId;
    private String username;
    private String password;


    private String email;
    private String mobile;





}
