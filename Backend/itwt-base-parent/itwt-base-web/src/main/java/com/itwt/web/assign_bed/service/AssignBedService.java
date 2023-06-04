package com.itwt.web.assign_bed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itwt.web.assign_bed.entity.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AssignBedService extends IService<AssignBed> {
    //根据班级id查询分配的宿舍
    List<RoomVo> getRoomVoList(Long classId);
    //根据宿舍id查询床位
    List<BedVo> getBedVoList(Long roomId);
    //根据班级id查询分配的宿舍
    List<SelectRoom> getRoomByClassId(Long classId);
    //查询床位
    List<SelectBed> getBedByClassId(Long classId,Long roomId);
}
