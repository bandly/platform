package com.xuhe.platform.entity.vo.sys.menu;

import lombok.Data;

import java.util.List;

/**
 * @author liqiang
 * @date 2019/06/07
 * @description:
 */
@Data
public class MenuTree {

    private String name;

    private Long id;

    private List<MenuTree> children;


}
