package com.itwt.web.assign_bed.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("assign_bed")
public class AssignBed {
    private Long assignId;
    private Long bedId;
    private Long classId;
}
