package com.itwt.web.sys_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.sys_role.entity.SysRole;
import com.itwt.web.sys_role.service.SysRoleService;
import com.itwt.web.sys_user.entity.PageParm;
import com.itwt.web.sys_user.entity.SysUser;
import com.itwt.web.sys_user.service.SysUserService;
import com.itwt.web.sys_user_role.entity.SysUserRole;
import com.itwt.web.sys_user_role.service.SysUserRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserRoleService sysUserRoleService;

    /*
     * 新增用户
     * */
    @PostMapping
    public ResultVo addUser(@RequestBody SysUser sysUser) {
        //判断用户是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());
        SysUser one = sysUserService.getOne(queryWrapper);
        if (one != null) {
            return ResultUtils.error("账户已被占用");
        }
        //密码加密
        if (StringUtils.isNotEmpty(sysUser.getPassword())) {
            sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        }

        sysUser.setIsAdmin("0");
        sysUser.setCreateTime(new Date());
        sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        //存入数据库
        sysUserService.add(sysUser);

        return ResultUtils.success("新增用户成功！");

    }

    /*
     * 编辑员工
     * */
    @PutMapping
    public ResultVo editUser(@RequestBody SysUser sysUser) {
        //判断用户是否存在
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, sysUser.getUsername());
        SysUser one = sysUserService.getOne(queryWrapper);
        if (one != null && one.getUserId() != sysUser.getUserId()) {
            return ResultUtils.error("账户已被占用");
        }
        //密码加密
        if (StringUtils.isNotEmpty(sysUser.getPassword())) {
            sysUser.setPassword(DigestUtils.md5DigestAsHex(sysUser.getPassword().getBytes()));
        }
        sysUser.setUpdateTime(new Date());
        //存入数据库
        sysUserService.edit(sysUser);

        return ResultUtils.success("编辑用户成功！");

    }

    /*
     * 删除用户
     * */
    @DeleteMapping("/{userId}")
    public ResultVo deleteUser(@PathVariable("userId") Long userId) {
        boolean remove = sysUserService.removeById(userId);
        if (remove) {
            return ResultUtils.success("编辑用户成功！");
        }
        return ResultUtils.error("编辑用户失败！");
    }

    /*
     * 用户列表,分页
     * */
    @GetMapping("/list")
    public ResultVo getList(PageParm parm) {
        IPage<SysUser> list = sysUserService.list(parm);
        //密码不显示
        list.getRecords().stream().forEach(item -> {
            item.setPassword("");
        });
        return ResultUtils.success("查询成功", list);
    }

    /*
     * 查询角色列表
     * */
    @GetMapping("/roleList")
    public ResultVo getRoleList() {
        List<SysRole> list = sysRoleService.list();
        return ResultUtils.success("查询成功", list);
    }

    /*
    * 查询用户对应的角色
    * */
    @GetMapping("/getRoleByUserId")
    public ResultVo getRoleByUserId(Long userId){
        LambdaQueryWrapper<SysUserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUserRole::getUserId,userId);
        SysUserRole one = sysUserRoleService.getOne(queryWrapper);
        return ResultUtils.success("查询成功",one);
    }
}
