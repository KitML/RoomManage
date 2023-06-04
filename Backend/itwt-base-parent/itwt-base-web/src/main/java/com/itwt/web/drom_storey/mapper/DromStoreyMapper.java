package com.itwt.web.drom_storey.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwt.web.drom_storey.entity.DromStorey;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DromStoreyMapper extends BaseMapper<DromStorey> {
    //批量插入
    void addDrom(@Param("list")List<DromStorey> list);
}
