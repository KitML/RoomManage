package com.itwt.web.apply_change.entity;

import lombok.Data;


@Data
public class ApplyListParm {
    private Long currentPage;
    private Long pageSize;
    private String userType;
    private Long userId;
}
