package com.itwt.web.drom_build.entity;

import lombok.Data;


@Data
public class BuildParm {
    private String buildName;
    private Long currentPage;
    private Long pageSize;
}
