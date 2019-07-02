package com.xuhe.platform.service.dto;

import lombok.Data;

/**
 * @author liqiang
 * @date 2019/06/27
 * @description:
 */
@Data
public class SysUserDTO {

    private Long userId;
    private String account;
    private String password;
    private Integer status;
    private String email;

}
