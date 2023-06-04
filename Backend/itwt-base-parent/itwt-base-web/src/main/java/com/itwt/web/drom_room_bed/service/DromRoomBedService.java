package com.itwt.web.drom_room_bed.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itwt.web.drom_room_bed.entity.DromRoomBed;
import com.itwt.web.drom_room_bed.entity.DromRoomVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DromRoomBedService extends IService<DromRoomBed> {
    List<DromRoomBed> getBedList(Long roomId);

    List<DromRoomVo> getSelectBedList(Long roomId);
}
