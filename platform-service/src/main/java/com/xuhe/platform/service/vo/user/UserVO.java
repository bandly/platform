package com.xuhe.platform.service.vo.user;

import lombok.Data;

import java.util.Date;

/**
 * @author liqiang
 * @date 2019/05/23
 * @description:
 */
@Data
public class UserVO {

    private Long userId;
    private String account;


    private String email;
    private String mobile;

    private Integer status;

    private Date createTime;



}
