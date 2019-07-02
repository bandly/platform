package com.xuhe.platform.dal.query;

import lombok.Data;


import java.util.Date;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
@Data
public class DalUserQuery {

    private String account;

    private Date startCreateTime;

    private Date endCreateTime;

    private Integer status;


}
