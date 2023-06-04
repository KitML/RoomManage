package com.itwt.web.school_class.entity;

import lombok.Data;


@Data
public class ListParm {
    private String className;
    private String majorName;
    private String collageName;
    private Long currentPage;
    private Long pageSize;
}
