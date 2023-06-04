package com.itwt.web.drom_room.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwt.web.drom_room.entity.DromRoom;
import com.itwt.web.drom_room.entity.RoomTree;
import com.itwt.web.drom_room.mapper.DromRoomMapper;
import com.itwt.web.drom_room.service.DromRoomService;
import com.itwt.web.drom_room_bed.entity.DromRoomBed;
import com.itwt.web.drom_room_bed.service.DromRoomBedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DromRoomServiceImpl extends ServiceImpl<DromRoomMapper, DromRoom> implements DromRoomService {

    @Autowired
    private DromRoomBedService dromRoomBedService;

    @Override
    public List<RoomTree> getRoomTree(Long storeyId) {
        //根据层数id查询宿舍
        List<RoomTree> list = this.baseMapper.getRoomTree(storeyId);
        //根据宿舍查询对应的床位
        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                //宿舍id
                Long roomId = list.get(i).getRoomId();
                list.get(i).setType("1");
                //床位
                List<DromRoomBed> bedList = dromRoomBedService.getBedList(roomId);
                if(bedList.size()>0){
                    for (int j=0;j<bedList.size();j++){
                        RoomTree tree = new RoomTree();
                        tree.setRoomId(bedList.get(j).getBedId());
                        tree.setRoomCode(bedList.get(j).getBedCode());
                        tree.setType("0");
                        //把床位放到宿舍
                        list.get(i).getChildren().add(tree);
                    }
                }
            }
        }
        return list;
    }
}
