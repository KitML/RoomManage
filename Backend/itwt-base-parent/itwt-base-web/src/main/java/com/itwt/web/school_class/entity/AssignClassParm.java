package com.itwt.web.school_class.entity;

import lombok.Data;


@Data
public class AssignClassParm {
    private Long currentPage;
    private Long pageSize;
    private String className;
    private String classYear;
}
