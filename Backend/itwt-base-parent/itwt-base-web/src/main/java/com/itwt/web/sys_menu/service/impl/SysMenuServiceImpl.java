package com.itwt.web.sys_menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwt.web.sys_menu.entity.MakeTree;
import com.itwt.web.sys_menu.entity.SysMenu;
import com.itwt.web.sys_menu.mapper.SysMenuMapper;
import com.itwt.web.sys_menu.service.SysMenuService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {


    @Override
    public List<SysMenu> menuList() {
        //查询列表
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menuList = this.baseMapper.selectList(queryWrapper);
        //组装树
        List<SysMenu> list = MakeTree.makeMenuTree(menuList, 0L);
        return list;
    }

    @Override
    public List<SysMenu> parentList() {
        //查询类型，只查类型为目录和菜单
        String [] types = {"0","1"};
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SysMenu::getType, Arrays.asList(types)).orderByAsc(SysMenu::getOrderNum);
        List<SysMenu> menuList = this.baseMapper.selectList(queryWrapper);
        //组装顶级菜单，防止无数据的时候没有菜单
        SysMenu menu = new SysMenu();
        menu.setMenuId(0L);
        menu.setParentId(-1L);
        menu.setTitle("顶级菜单");
        menuList.add(menu);
        //组装数据
        List<SysMenu> list = MakeTree.makeMenuTree(menuList, -1L);
        return list;
    }

    @Override
    public List<SysMenu> getMenuByUserId(Long userId) {
        return this.baseMapper.getMenuByUserId(userId);
    }

    @Override
    public List<SysMenu> getMenuByRoleId(Long roleId) {
        return this.baseMapper.getMenuByRoleId(roleId);
    }

    @Override
    public List<SysMenu> getStuMenuByUserId(Long stuId) {
        return this.baseMapper.getStuMenuByUserID(stuId);
    }
}
