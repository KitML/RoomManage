package com.itwt.web.sys_role_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwt.web.sys_role_menu.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
    boolean saveRoleMenu(@Param("roleId") Long roleId, @Param("menuIds")List<Long> menuIds);
}
