package com.itwt.web.apply_change.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwt.web.apply_change.entity.ApplyChange;
import com.itwt.web.apply_change.entity.StuInfoVo;
import org.apache.ibatis.annotations.Param;

public interface ApplyChangeMapper extends BaseMapper<ApplyChange> {
    StuInfoVo getStuInfo(@Param("stuId") Long stuId);
}
