package com.itwt.web.drom_room_bed.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwt.web.drom_room_bed.entity.DromRoomBed;
import com.itwt.web.drom_room_bed.entity.DromRoomVo;
import com.itwt.web.drom_room_bed.mapper.DromRoomBedMapper;
import com.itwt.web.drom_room_bed.service.DromRoomBedService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DromRoomBedServiceImpl extends ServiceImpl<DromRoomBedMapper, DromRoomBed> implements DromRoomBedService {
    @Override
    public List<DromRoomBed> getBedList(Long roomId) {
        return this.baseMapper.getBedList(roomId);
    }

    @Override
    public List<DromRoomVo> getSelectBedList(Long roomId) {
        return this.baseMapper.getSelectBedList(roomId);
    }
}
