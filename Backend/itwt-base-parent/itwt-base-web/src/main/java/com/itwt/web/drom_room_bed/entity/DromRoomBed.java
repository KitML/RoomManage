package com.itwt.web.drom_room_bed.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("drom_room_bed")
public class DromRoomBed {
    @TableId(type = IdType.AUTO)
    private Long bedId;

    private Long roomId;

    private String bedCode;
}