package com.itwt.web.drom_room.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwt.web.drom_room.entity.DromRoom;
import com.itwt.web.drom_room.entity.RoomTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DromRoomMapper extends BaseMapper<DromRoom> {
    List<RoomTree> getRoomTree(@Param("storeyId")Long storeyId);
}
