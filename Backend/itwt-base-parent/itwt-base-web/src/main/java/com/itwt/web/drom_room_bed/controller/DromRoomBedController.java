package com.itwt.web.drom_room_bed.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.drom_room_bed.entity.DromRoomBed;
import com.itwt.web.drom_room_bed.service.DromRoomBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/roomBed")
public class DromRoomBedController {

    @Autowired
    private DromRoomBedService dromRoomBedService;

    //编辑
    @PutMapping
    public ResultVo edit(@RequestBody DromRoomBed dromRoomBed){
        //判断编号是否重复
        LambdaQueryWrapper<DromRoomBed> query = new LambdaQueryWrapper<>();
        query.eq(DromRoomBed::getBedCode,dromRoomBed.getBedCode());
        DromRoomBed one = dromRoomBedService.getOne(query);
        if(one != null && one.getBedId() != dromRoomBed.getBedId()){
            return ResultUtils.error("编号重复!");
        }
        boolean b = dromRoomBedService.updateById(dromRoomBed);
        if(b){
            return ResultUtils.success("编辑成功!");
        }
        return ResultUtils.error("编辑失败!");
    }

    //删除
    @DeleteMapping("/{bedId}")
    public ResultVo delete(@PathVariable("bedId") Long bedId){
        //判断床位是否有人住，不能删除
        boolean b = dromRoomBedService.removeById(bedId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败!");
    }
}
