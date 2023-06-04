package com.itwt.web.drom_look.entity;

import lombok.Data;


@Data
public class LookParm {
     private String lookRoom;
    private String userName;
    private Long currentPage;
    private Long pageSize;
}
