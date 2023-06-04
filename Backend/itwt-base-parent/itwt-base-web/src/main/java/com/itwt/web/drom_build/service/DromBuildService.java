package com.itwt.web.drom_build.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itwt.web.drom_build.entity.BuildEcharts;
import com.itwt.web.drom_build.entity.DromBuild;

public interface DromBuildService extends IService<DromBuild> {
    //新增
    void addBuild(DromBuild dromBuild);

    //楼栋统计
    BuildEcharts getListBuild();
}
