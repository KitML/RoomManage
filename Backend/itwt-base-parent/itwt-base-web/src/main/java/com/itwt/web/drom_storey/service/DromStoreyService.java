package com.itwt.web.drom_storey.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itwt.web.drom_storey.entity.DromStorey;

import java.util.List;

public interface DromStoreyService extends IService<DromStorey> {
    //批量插入
    void addDrom(List<DromStorey> list);
    void add(DromStorey dromStorey);
    void edit(DromStorey dromStorey);
    void delete(Long buildId,Long storeyId);
    //初始化床位
    void initBed(DromStorey dromStorey);
}
