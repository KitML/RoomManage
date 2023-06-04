package com.itwt.web.select_bed.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.school_student.entity.SchoolStudent;
import com.itwt.web.school_student.service.SchoolStudentService;
import com.itwt.web.select_bed.entity.ChangeStu;
import com.itwt.web.select_bed.entity.StuBed;
import com.itwt.web.select_bed.entity.StuBedVo;
import com.itwt.web.select_bed.service.StuBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stuBed")
public class StuBedController {

    @Autowired
    private StuBedService stuBedService;
    @Autowired
    private SchoolStudentService schoolStudentService;


    //选择床位保存
    @PostMapping("/selectBedSave")
    public ResultVo selectBedSave(@RequestBody StuBed stuBed){
        //构造查询条件,判断床位是否被占用
        LambdaQueryWrapper<StuBed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(StuBed::getBedId,stuBed.getBedId());
        StuBed one = stuBedService.getOne(queryWrapper);
        if (one != null){
            return ResultUtils.error("该床位已被占用");
        }
        //构造查询条件，判断学生是否已经选定床位
        LambdaQueryWrapper<StuBed> stuQueryWrapper = new LambdaQueryWrapper<>();
        stuQueryWrapper.eq(StuBed::getStuId,stuBed.getStuId());
        StuBed stu = stuBedService.getOne(stuQueryWrapper);
        if (stu != null){
            return  ResultUtils.error("你已经选择床位，不必再次选择");
        }
        boolean b = stuBedService.save(stuBed);
        if (b){
            return ResultUtils.success("选择成功！");
        }
        return ResultUtils.error("选择失败！");
    }

    //查询学生宿舍
    @GetMapping("/getStuBedList")
    public ResultVo getStuBedList(Long stuId,String userType){
        if(userType.equals("1")){
            return ResultUtils.success("查询成功",null);
        }
        List<StuBedVo> stuBedList = stuBedService.getStuBedList(stuId);
        return ResultUtils.success("查询成功",stuBedList);
    }

    //查询调换学生列表
    @GetMapping("/getStuList")
    public ResultVo getStuList(Long stuId){
        //根据学生id查询班级
        SchoolStudent student = schoolStudentService.getById(stuId);
        //根据班级id查询学生
        List<ChangeStu> stuList = stuBedService.getStuList(student.getClassId());
        return ResultUtils.success("查询成功",stuList);
    }

    //删除
    @DeleteMapping("/{stuId}/{bedId}")
    public ResultVo deleteBed(@PathVariable("stuId") Long stuId,@PathVariable("bedId") Long bedId){
        LambdaQueryWrapper<StuBed> query = new LambdaQueryWrapper<>();
        query.eq(StuBed::getStuId,stuId)
                .eq(StuBed::getBedId,bedId);
        boolean remove = stuBedService.remove(query);
        if(remove){
            return ResultUtils.success("删除成功!");
        }
        return ResultUtils.error("删除失败!");
    }

}
