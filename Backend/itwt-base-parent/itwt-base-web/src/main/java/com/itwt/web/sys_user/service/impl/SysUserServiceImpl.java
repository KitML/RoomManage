package com.itwt.web.sys_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwt.web.sys_user.entity.PageParm;
import com.itwt.web.sys_user.entity.SysUser;
import com.itwt.web.sys_user.mapper.SysUserMapper;
import com.itwt.web.sys_user.service.SysUserService;
import com.itwt.web.sys_user_role.entity.SysUserRole;
import com.itwt.web.sys_user_role.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public IPage<SysUser> list(PageParm parm) {
        //构造分页对象
        IPage<SysUser> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        //构造查询条件
        QueryWrapper<SysUser> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getNickName())){
            query.lambda().like(SysUser::getNickName,parm.getNickName());
        }
        if(StringUtils.isNotEmpty(parm.getPhone())){
            query.lambda().like(SysUser::getPhone,parm.getPhone());
        }
        return this.baseMapper.selectPage(page,query);
    }

    @Override
    @Transactional
    public void add(SysUser user) {
        //新增用户
        int insert = this.baseMapper.insert(user);
        //保存角色
        if (insert >0){
            SysUserRole role = new SysUserRole();
            role.setRoleId(user.getRoleId());
            role.setUserId(user.getUserId());
            sysUserRoleService.save(role);
        }
    }

    @Override
    @Transactional
    public void edit(SysUser user) {
        //编辑
        int i = this.baseMapper.updateById(user);
        if (i>0){
            //先删除再插入
            LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysUserRole::getUserId,user.getUserId());
            sysUserRoleService.remove(queryWrapper);
            //保存
            SysUserRole role = new SysUserRole();
            role.setRoleId(user.getRoleId());
            role.setUserId(user.getUserId());
            sysUserRoleService.save(role);
        }
    }
}
