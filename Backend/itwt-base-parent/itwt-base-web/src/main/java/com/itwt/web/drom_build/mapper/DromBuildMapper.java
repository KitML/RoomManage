package com.itwt.web.drom_build.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itwt.web.drom_build.entity.BuildVo;
import com.itwt.web.drom_build.entity.DromBuild;

import java.util.List;

public interface DromBuildMapper extends BaseMapper<DromBuild> {
    List<BuildVo> getListBuild();
}
