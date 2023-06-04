package com.itwt.web.school_collage.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.school_collage.entity.ListParm;
import com.itwt.web.school_collage.entity.SchoolCollage;
import com.itwt.web.school_collage.service.SchoolCollageService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/collage")
public class SchoolCollageController {

    @Autowired
    private SchoolCollageService schoolCollageService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody SchoolCollage schoolCollage){
        //设置创建时间
        schoolCollage.setCreateTime(new Date());
        boolean save = schoolCollageService.save(schoolCollage);
        if (save){
            return ResultUtils.success("新增学院成功！");
        }
        return ResultUtils.error("新增学院失败！");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody SchoolCollage schoolCollage){
        boolean edit = schoolCollageService.updateById(schoolCollage);
        if (edit){
            return ResultUtils.success("编辑学院成功！");
        }
        return ResultUtils.error("编辑学院失败！");
    }

    //删除
    @DeleteMapping("/{collageId}")
    public ResultVo delete(@PathVariable("collageId") Long collageId){
        boolean b = schoolCollageService.removeById(collageId);
        if (b){
            return ResultUtils.success("删除学院成功！");
        }
        return ResultUtils.error("删除学院失败！");
    }

    //列表
    @GetMapping("/list")
    public ResultVo getList(ListParm listParm){
        //构造查询条件
        LambdaQueryWrapper<SchoolCollage> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(listParm.getCollageName())){
            queryWrapper.like(SchoolCollage::getCollageName,listParm.getCollageName());
        }
        //构造分页对象
        IPage<SchoolCollage> page = new Page<>(listParm.getCurrentPage(), listParm.getPageSize());
        //查询
        IPage<SchoolCollage> list = schoolCollageService.page(page,queryWrapper);
        return ResultUtils.success("查询成功",list);
    }
}
