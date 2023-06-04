package com.itwt.web.select_bed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itwt.web.select_bed.entity.ChangeStu;
import com.itwt.web.select_bed.entity.StuBed;
import com.itwt.web.select_bed.entity.StuBedVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StuBedService extends IService<StuBed> {
    List<StuBedVo> getStuBedList(Long stuId);

    List<ChangeStu> getStuList(Long classId);

    void clearBed(Long classId);
}
