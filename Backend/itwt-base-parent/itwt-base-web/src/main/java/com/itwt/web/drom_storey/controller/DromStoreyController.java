package com.itwt.web.drom_storey.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.drom_storey.entity.DromStorey;
import com.itwt.web.drom_storey.service.DromStoreyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/storey")
public class DromStoreyController {

    @Autowired
    private DromStoreyService dromStoreyService;


    //新增
    @PostMapping
    public ResultVo add(@RequestBody DromStorey dromStorey){
        dromStoreyService.add(dromStorey);
        return ResultUtils.success("新增成功");
    }

    //新增
    @PutMapping
    public ResultVo edit(@RequestBody DromStorey dromStorey){
        dromStoreyService.edit(dromStorey);
        return ResultUtils.success("编辑成功");
    }

    //删除
    @DeleteMapping("/{buildId}/{storeyId}")
    public ResultVo delete(@PathVariable("buildId") Long buildId,@PathVariable("storeyId") Long storeyId){
        //判断层下面，是否有宿舍，如果有宿舍，不能删除
        dromStoreyService.delete(buildId,storeyId);
        return ResultUtils.success("删除成功");
    }

    //根据楼栋查询层数列表
    @GetMapping("/getDestoryList")
    public ResultVo getDestoryList(Long buildId){
        LambdaQueryWrapper<DromStorey> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DromStorey::getBuildId,buildId);
        List<DromStorey> list = dromStoreyService.list(queryWrapper);
        return ResultUtils.success("查询成功！",list);
    }

    //初始化床位
    @PostMapping("/initBed")
    public ResultVo initBed(@RequestBody DromStorey dromStorey){
        dromStoreyService.initBed(dromStorey);
        return ResultUtils.success("初始化床位成功");
    }
}
