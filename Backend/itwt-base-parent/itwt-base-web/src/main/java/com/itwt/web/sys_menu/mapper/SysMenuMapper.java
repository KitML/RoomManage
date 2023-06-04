package com.itwt.web.sys_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwt.web.sys_menu.entity.SysMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {
    //根据用户id查询权限
    List<SysMenu> getMenuByUserId(@Param("userId") Long userId);
    //根据角色id查询权限
    List<SysMenu> getMenuByRoleId(@Param("roleId") Long roleId);
    //根据学生id查询权限
    List<SysMenu> getStuMenuByUserID(@Param("stuId")Long stuId);
}
