package com.itwt.web.assign_bed.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Data
public class RoomVo {
    private Long roomId;
    private String rootType;
    private String roomCode;
    private List<BedVo> children = new ArrayList<>();
}
