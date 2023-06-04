package com.itwt.web.school_student.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itwt.web.school_student.entity.SchoolStudent;
import com.itwt.web.school_student.entity.StuParm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolStudentMapper extends BaseMapper<SchoolStudent> {
    //根据id查询学生信息
    SchoolStudent getStuById(@Param("stuId") Long stuId);
    //分页列表查询
    IPage<SchoolStudent> getList(IPage<SchoolStudent> page, @Param("parm")StuParm parm);
    //查询id学生
    SchoolStudent getById(@Param("stuId") Long stuId);
    //导出学生
    List<SchoolStudent> exportStu(@Param("parm")StuParm parm);
}
