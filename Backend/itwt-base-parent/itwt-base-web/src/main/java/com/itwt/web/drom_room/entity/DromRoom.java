package com.itwt.web.drom_room.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
//import com.itwt.web.drom_room_bed.entity.DromRoomVo;
import com.itwt.web.drom_room_bed.entity.DromRoomBed;
import com.itwt.web.drom_room_bed.entity.DromRoomVo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
@TableName("drom_room")
public class DromRoom {
    @TableId(type = IdType.AUTO)
    private Long roomId;

    private Long storeyId;

    private String rootType;

    private String roomCode;

    private Integer totalBed;
    //床位列表
    @TableField(exist = false)
    List<DromRoomVo> bedList = new ArrayList<>();
}
