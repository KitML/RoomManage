package com.itwt.web.sys_role.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwt.web.sys_menu.entity.MakeTree;
import com.itwt.web.sys_menu.entity.SysMenu;
import com.itwt.web.sys_menu.service.SysMenuService;
import com.itwt.web.sys_role.entity.AssignParm;
import com.itwt.web.sys_role.entity.AssignVo;
import com.itwt.web.sys_role.entity.RoleParm;
import com.itwt.web.sys_role.entity.SysRole;
import com.itwt.web.sys_role.mapper.SysRoleMapper;
import com.itwt.web.sys_role.service.SysRoleService;
import com.itwt.web.sys_user.entity.SysUser;
import com.itwt.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public IPage<SysRole> list(RoleParm parm) {
        //构造分页对象
        IPage<SysRole> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        //添加查询条件
        LambdaQueryWrapper<SysRole> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getRoleName())){
            queryWrapper.like(SysRole::getRoleName,parm.getRoleName());
        }
        return this.baseMapper.selectPage(page,queryWrapper);
    }

    @Override
    public AssignVo getAssignShow(AssignParm parm) {
        //查询当前用户的信息,判断是否是超级管理员
        SysUser user = sysUserService.getById(parm.getUserId());
        List<SysMenu> list = null;
        //判断是否是超级管理员
        if( user.getIsAdmin().equals("1")){
            //如果是超级管理员，拥有所有的菜单权限
            //构造查询条件
            LambdaQueryWrapper<SysMenu> query = new LambdaQueryWrapper<>();
            query.orderByAsc(SysMenu::getOrderNum);
            list = sysMenuService.list(query);
        }else{ //如果不是超级管理员，根据自己的id查询菜单权限
            list = sysMenuService.getMenuByUserId(parm.getUserId());
        }
        //组装菜单树
        List<SysMenu> menuList = MakeTree.makeMenuTree(list, 0L);
        //查询角色原来的菜单权限
        List<SysMenu> menuByRoleId = sysMenuService.getMenuByRoleId(parm.getRoleId());
        //过滤出id
        List<Long> ids = new ArrayList<>();
        Optional.ofNullable(menuByRoleId).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null)
                .forEach(item ->{
                    ids.add(item.getMenuId());
                });
        //组装返回值
        AssignVo vo = new AssignVo();
        vo.setCheckList(ids.toArray());
        vo.setMenuList(menuList);
        return vo;
    }
}
