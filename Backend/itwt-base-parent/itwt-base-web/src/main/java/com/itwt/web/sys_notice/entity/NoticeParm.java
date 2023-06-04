package com.itwt.web.sys_notice.entity;

import lombok.Data;


@Data
public class NoticeParm {
    private Long currentPage;
    private Long pageSize;
    private String noticeTitle;
}
