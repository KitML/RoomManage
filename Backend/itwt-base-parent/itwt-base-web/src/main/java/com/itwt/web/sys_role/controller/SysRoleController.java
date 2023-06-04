package com.itwt.web.sys_role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.sys_role.entity.*;
import com.itwt.web.sys_role.service.SysRoleService;
import com.itwt.web.sys_role_menu.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    /*
    * 新增角色
    * */
    @PostMapping
    public ResultVo addRole(@RequestBody SysRole sysRole){
        sysRole.setCreateTime(new Date());
        boolean save = sysRoleService.save(sysRole);
        if(save){
            return ResultUtils.success("新增角色成功!");
        }
        return ResultUtils.error("新增角色失败!");
    }

    /*
    * 编辑角色
    * */
    @PutMapping
    public ResultVo editRole(@RequestBody SysRole sysRole){
        sysRole.setUpdateTime(new Date());
        boolean edit = sysRoleService.updateById(sysRole);
        if(edit){
            return ResultUtils.success("编辑角色成功!");
        }
        return ResultUtils.error("编辑角色失败!");
    }

    /*
    * 删除角色
    * */
    @DeleteMapping("/{roleId}")
    public ResultVo deleteRole(@PathVariable("roleId") Long roleId){
        boolean b =sysRoleService.removeById(roleId);
        if(b){
            return ResultUtils.success("删除角色成功!");
        }
        return ResultUtils.error("删除角色失败!");
    }

    /*
    * 角色列表
    * */
    @GetMapping("/list")
    public ResultVo getList(RoleParm parm){
        IPage<SysRole> list = sysRoleService.list(parm);
        return ResultUtils.success("查询成功",list);
    }

    //分配权限回显
    @GetMapping("/getAssingShow")
    public ResultVo getAssingShow(AssignParm parm){
        AssignVo show = sysRoleService.getAssignShow(parm);
        return ResultUtils.success("查询成功",show);
    }

    //分配权限保存
    @PostMapping("/saveRoleMenu")
    public ResultVo saveRoleMenu(@RequestBody SaveRoleParm parm){
        sysRoleMenuService.saveRoleMenu(parm.getRoleId(),parm.getList());
        return ResultUtils.success("分配权限成功！");
    }
}
