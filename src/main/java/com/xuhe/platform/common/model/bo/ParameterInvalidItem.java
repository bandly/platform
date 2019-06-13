package com.xuhe.platform.common.model.bo;

import lombok.Data;

/**
 * @author liqiang
 * @date 2019/06/12
 * @description: 参数无效项
 */
@Data
public class ParameterInvalidItem {

    /**
     * 无效字段的名称
     */
    private String fieldName;

    /**
     * 错误信息
     */
    private String message;
}
