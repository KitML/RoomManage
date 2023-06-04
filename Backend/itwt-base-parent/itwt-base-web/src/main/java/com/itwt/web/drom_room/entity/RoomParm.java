package com.itwt.web.drom_room.entity;

import lombok.Data;


@Data
public class RoomParm {
    private String area;

    private Integer start;

    private Integer end;

    private Integer stuNum;

    private Long storeyId;

    private String rootType;
}
