package com.itwt.web.drom_room_bed.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwt.web.drom_room_bed.entity.DromRoomBed;
import com.itwt.web.drom_room_bed.entity.DromRoomVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DromRoomBedMapper extends BaseMapper<DromRoomBed> {
    List<DromRoomBed> getBedList(@Param("roomId")Long roomId);

    List<DromRoomVo> getSelectBedList(@Param("roomId") Long roomId);
}
