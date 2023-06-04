package com.itwt.web.sys_role.entity;

import lombok.Data;

import java.util.List;

@Data
public class SaveRoleParm {
    //角色id
    private Long roleId;
    //菜单的id
    private List<Long> list;
}
