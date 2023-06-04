package com.itwt.web.assign_bed.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.assign_bed.entity.*;
import com.itwt.web.assign_bed.service.AssignBedService;
import com.itwt.web.school_student.entity.SchoolStudent;
import com.itwt.web.school_student.service.SchoolStudentService;
import com.itwt.web.select_bed.service.StuBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignBed")
public class AssignBedController {

    @Autowired
    private AssignBedService assignBedService;
    @Autowired
    private SchoolStudentService schoolStudentService;
    @Autowired
    private StuBedService stuBedService;

    //分配宿舍保存
    @PostMapping("/assignSaveBed")
    public ResultVo assignSaveBed(@RequestBody List<AssignBed> list){
        assignBedService.saveBatch(list);
        return ResultUtils.success("分配宿舍成功");
    }

    @GetMapping("/getAssignBedList")
    public ResultVo getAssignBedList(Long classId) {
        //宿舍列表
        List<RoomVo> roomVoList = assignBedService.getRoomVoList(classId);
        if (roomVoList.size() > 0) {
            for (int i = 0; i < roomVoList.size(); i++) {
                //查询宿舍的床位
                List<BedVo> bedVoList = assignBedService.getBedVoList(roomVoList.get(i).getRoomId());
                //设置宿舍的床位
                roomVoList.get(i).setChildren(bedVoList);
            }
        }
        return ResultUtils.success("查询成功", roomVoList);
    }

    //删除
    @DeleteMapping("/{bedId}/{classId}")
    public ResultVo delete(@PathVariable("bedId")Long bedId,@PathVariable("classId")Long classId){
        LambdaQueryWrapper<AssignBed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AssignBed::getBedId,bedId).eq(AssignBed::getClassId,classId);
        boolean b = assignBedService.remove(queryWrapper);
        if (b){
            return ResultUtils.success("删除成功！");
        }
        return ResultUtils.error("删除失败！");
    }

    //学生查询宿舍列表
    @GetMapping("/getRoomByClassId")
    public ResultVo getRoomByClassId(Long userId,String userType){
        if (!userType.equals("0")){
            return ResultUtils.error("学生才能参与选择宿舍");
        }
        //查询学生信息
        SchoolStudent student = schoolStudentService.getById(userId);
        List<SelectRoom> roomList = null;
        if (student != null){
            roomList = assignBedService.getRoomByClassId(student.getClassId());
            if (roomList.size()>0){
                for (int i=0;i< roomList.size();i++){
                    //床位查询
                    List<SelectBed> bedList = assignBedService.getBedByClassId(student.getClassId(), roomList.get(i).getRoomId());
                    roomList.get(i).setChildren(bedList);
                }
            }
        }
        return ResultUtils.success("查询成功",roomList);
    }

    //清空宿舍
    @PostMapping("/clearBed")
    public ResultVo clearBed(@RequestBody ClearBed clearBed){
        stuBedService.clearBed(clearBed.getClassId());
        return ResultUtils.success("清空成功");
    }

}
