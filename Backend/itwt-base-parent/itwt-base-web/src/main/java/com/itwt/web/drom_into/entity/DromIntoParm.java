package com.itwt.web.drom_into.entity;

import lombok.Data;


@Data
public class DromIntoParm {
    private String name;
    private String userName;
    private Long currentPage;
    private Long pageSize;
}
