package com.itwt.web.drom_into.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.drom_into.entity.DromInto;
import com.itwt.web.drom_into.entity.DromIntoParm;
import com.itwt.web.drom_into.service.DromIntoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/into")
public class DromIntoController {

    @Autowired
    private DromIntoService dromIntoService;

    //新增
    @PostMapping
    public ResultVo add(@RequestBody DromInto dromInto){
        boolean save = dromIntoService.save(dromInto);
        if(save){
            return ResultUtils.success("保存成功!");
        }
        return ResultUtils.error("操作失败!");
    }

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody DromInto dromInto){
        boolean save = dromIntoService.updateById(dromInto);
        if(save){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{intoId}")
    public ResultVo delete(@PathVariable("intoId") Long intoId){
        boolean save = dromIntoService.removeById(intoId);
        if(save){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

    //列表
    @GetMapping("/list")
    public ResultVo list(DromIntoParm drom){
        //构造分页对象
        IPage<DromInto> page = new Page<>(drom.getCurrentPage(),drom.getPageSize());
        //构造查询条件
        LambdaQueryWrapper<DromInto> query = new LambdaQueryWrapper<>();
        if(StringUtils.isNotEmpty(drom.getName())){
            query.like(DromInto::getName,drom.getName());
        }
        if(StringUtils.isNotEmpty(drom.getUserName())){
            query.like(DromInto::getUserName,drom.getUserName());
        }
        query.orderByDesc(DromInto::getIntoTime);
        //查询列表
        IPage<DromInto> list = dromIntoService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

}
