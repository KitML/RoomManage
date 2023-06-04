package com.itwt.web.sys_role_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwt.web.sys_role_menu.service.SysRoleMenuService;
import com.itwt.web.sys_role_menu.entity.SysRoleMenu;
import com.itwt.web.sys_role_menu.mapper.SysRoleMenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper, SysRoleMenu> implements SysRoleMenuService {
    @Override
    @Transactional
    public void saveRoleMenu(Long roleId, List<Long> menuIds) {
        //先删除原角色id，再重新插入
        LambdaQueryWrapper<SysRoleMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysRoleMenu::getRoleId,roleId);
        //删除
        this.baseMapper.delete(queryWrapper);
        //插入
        this.baseMapper.saveRoleMenu(roleId,menuIds);
    }
}
