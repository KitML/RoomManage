package com.itwt.web.drom_room.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itwt.web.drom_room.entity.DromRoom;
import com.itwt.web.drom_room.entity.RoomTree;

import java.util.List;

public interface DromRoomService extends IService<DromRoom> {
    List<RoomTree> getRoomTree(Long storeyId);
}
