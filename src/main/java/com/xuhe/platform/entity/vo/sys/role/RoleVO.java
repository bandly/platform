package com.xuhe.platform.entity.vo.sys.role;


import com.xuhe.platform.common.validator.EditGroup;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author liqiang
 * @date 2019/06/07
 * @description:
 */
@Data
public class RoleVO {

    @NotNull(groups = EditGroup.class,message = "roleId 不能为空")
    private Long roleId;

    private String roleName;

    private String remark;

    private Date createTime;
}
