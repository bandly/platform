package com.xuhe.platform.service.vo.role;



import lombok.Data;

import java.util.Date;

/**
 * @author liqiang
 * @date 2019/06/07
 * @description:
 */
@Data
public class RoleVO {

    //@NotNull(groups = EditGroup.class,message = "roleId 不能为空")
    private Long roleId;

    private String roleName;

    private String remark;

    private Date createTime;
}
