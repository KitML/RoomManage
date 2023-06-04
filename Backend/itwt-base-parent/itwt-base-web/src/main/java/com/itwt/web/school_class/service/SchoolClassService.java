package com.itwt.web.school_class.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itwt.web.school_class.entity.AssignClass;
import com.itwt.web.school_class.entity.AssignClassParm;
import com.itwt.web.school_class.entity.ListParm;
import com.itwt.web.school_class.entity.SchoolClass;

public interface SchoolClassService extends IService<SchoolClass> {
    SchoolClass getSchoolClassById(Long classId);
    //查询列表
    IPage<SchoolClass> getList(ListParm parm);
    //分配宿舍，查询班级列表
    IPage<AssignClass> getAssignClass(AssignClassParm parm);
}
