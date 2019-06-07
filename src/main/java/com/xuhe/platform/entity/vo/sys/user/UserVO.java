package com.xuhe.platform.entity.vo.sys.user;

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
    private String username;


    private String email;
    private String mobile;

    private Integer status;

    private Date createTime;



}
