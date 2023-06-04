package com.itwt.web.school_student.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itwt.web.school_student.entity.SchoolStudent;
import com.itwt.web.school_student.entity.StuParm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SchoolStudentService extends IService<SchoolStudent> {
    //根据id查询信息
    SchoolStudent getStuById(Long stuId);
    //分页列表查询
    IPage<SchoolStudent> getList(StuParm parm);
    //新增
    void addStu(SchoolStudent schoolStudent);
    //编辑
    void editStu(SchoolStudent schoolStudent);
    //删除
    void deleteStu(Long studId);
    //查询学生信息
    SchoolStudent getById(Long studId);
    //导出学生
    List<SchoolStudent> exportStu(StuParm parm);
    void importStu(List<SchoolStudent> list);
}
