package com.xuhe.platform.service.form.user;

import lombok.Data;

/**
 * @author liqiang
 * @date 2019/05/23
 * @description:
 */
@Data
public class EditUserForm {

    private Long userId;
    private String account;


    private String email;
    private String mobile;

    private Integer status;




}
