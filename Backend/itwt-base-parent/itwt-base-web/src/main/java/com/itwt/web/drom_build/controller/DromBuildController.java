package com.itwt.web.drom_build.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.drom_build.entity.BuildEcharts;
import com.itwt.web.drom_build.entity.BuildParm;
import com.itwt.web.drom_build.entity.DromBuild;
import com.itwt.web.drom_build.service.DromBuildService;
import com.itwt.web.drom_storey.entity.DromStorey;
import com.itwt.web.drom_storey.service.DromStoreyService;
import com.itwt.web.sys_user.entity.SysUser;
import com.itwt.web.sys_user.service.SysUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/build")
public class DromBuildController {

    @Autowired
    private DromBuildService dromBuildService;
    @Autowired
    private DromStoreyService dromStoreyService;
    @Autowired
    private SysUserService sysUserService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody DromBuild dromBuild){
        if (dromBuild.getBuildStorey()>12){
            return ResultUtils.error("层数不能大于12层！你要上天吗？");
        }
        dromBuildService.addBuild(dromBuild);
        return ResultUtils.success("新增成功！");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody DromBuild dromBuild){
        boolean b = dromBuildService.updateById(dromBuild);
        if (b){
            return ResultUtils.success("编辑成功！");
        }
        return ResultUtils.error("编辑失败！");
    }

    //删除
    @DeleteMapping("/{buildId}")
    public ResultVo deleete(@PathVariable("buildId") Long buildId){
        //判断楼栋下面是否有层数
        LambdaQueryWrapper<DromStorey> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DromStorey::getBuildId,buildId);
        List<DromStorey> list = dromStoreyService.list(queryWrapper);
        if (list.size()>0){
            return ResultUtils.error("该楼栋存在层数，删除失败！");
        }
        boolean b = dromBuildService.removeById(buildId);
        if (b){
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }

    //查询管理员
    @GetMapping("/getUserList")
    public ResultVo getUserList(){
        List<SysUser> list = sysUserService
                .list(new QueryWrapper<SysUser>().select("user_id","nick_name"));
        return ResultUtils.success("查询成功！",list);
    }

    //列表查询
    @GetMapping("/list")
    public ResultVo getList(BuildParm parm){
        //构造分页对象
        IPage<DromBuild> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<DromBuild> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(parm.getBuildName())){
            queryWrapper.like(DromBuild::getBuildName,parm.getBuildName());
        }
        queryWrapper.orderByAsc(DromBuild::getOrderNum);
        IPage<DromBuild> page1 = this.dromBuildService.page(page,queryWrapper);
        //查询对应的楼层
        if (page1.getRecords().size()>0){
            for (int i =0;i<page1.getRecords().size();i++){
                //构造层数查询条件
                LambdaQueryWrapper<DromStorey> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(DromStorey::getBuildId,page1.getRecords().get(i).getBuildId());
                List<DromStorey> storeyList = dromStoreyService.list(queryWrapper1);
                //设置栋数的所有层数
                page1.getRecords().get(i).setStoreys(storeyList);
            }
        }
        return ResultUtils.success("查询成功",page1);
    }

    //查询楼栋
    @GetMapping("/getBuildList")
    public ResultVo getBuildList(){
        List<DromBuild> list = dromBuildService.list();
        return ResultUtils.success("查询成功！",list);
    }

    //获取楼栋列表，根据学生性别0 1
    @GetMapping("/getBuildListBySex")
    public ResultVo getBuildListBySex(String type){
        //构造查询条件
        LambdaQueryWrapper<DromBuild> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DromBuild::getSex,type);
        List<DromBuild> list = dromBuildService.list(queryWrapper);
        return ResultUtils.success("查询成功",list);
    }

    //获取首页统计
    @GetMapping("/getTotal")
    public ResultVo getTotal(){
        BuildEcharts listBuild = dromBuildService.getListBuild();
        return ResultUtils.success("查询成功",listBuild);
    }
}
