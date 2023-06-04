package com.itwt.web.school_major.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itwt.web.school_major.entity.MajorList;
import com.itwt.web.school_major.entity.SchoolMajor;
import org.apache.ibatis.annotations.Param;

public interface SchoolMajorService extends IService<SchoolMajor> {
    IPage<SchoolMajor> getList(MajorList majorList);
}
