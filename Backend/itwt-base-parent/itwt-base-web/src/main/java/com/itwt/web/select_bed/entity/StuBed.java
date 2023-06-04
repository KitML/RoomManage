package com.itwt.web.select_bed.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@Data
@TableName("select_bed")
public class StuBed {
    @TableId(type = IdType.AUTO)
    private Long selectId;
    private Long bedId;
    private Long stuId;
}
