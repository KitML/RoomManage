package com.itwt.web.drom_leave.entity;

import lombok.Data;


@Data
public class LeaveParm {
    private String classes;
    private String stuName;
    private String dromName;
    private Long currentPage;
    private Long pageSize;
}
