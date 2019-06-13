package com.xuhe.platform.entity.query;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author liqiang
 * @date 2019/06/06
 * @description:
 */
@Data
public class UserQuery  extends PageQuery{

    private String username;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startCreateTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endCreateTime;

    private Integer status;


}
