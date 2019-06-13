package com.xuhe.platform.entity.vo.sys.role;

import lombok.Data;

import java.util.Date;

/**
 * @author liqiang
 * @date 2019/06/07
 * @description:
 */
@Data
public class RoleVO {

    private Long roleId;

    private String roleName;

    private String remark;

    private Date createTime;
}
