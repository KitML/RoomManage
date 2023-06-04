package com.itwt.web.apply_change.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.apply_change.entity.ApplyChange;
import com.itwt.web.apply_change.entity.ApplyDoParm;
import com.itwt.web.apply_change.entity.ApplyListParm;
import com.itwt.web.apply_change.entity.ApplyParm;
import com.itwt.web.apply_change.service.ApplyChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/applyChange")
public class ApplyChangeController {

    @Autowired
    private ApplyChangeService applyChangeService;

    //申请提交
    @PostMapping("/applySave")
    public ResultVo applySave(@RequestBody ApplyParm parm){
        //构造查询条件
        LambdaQueryWrapper<ApplyChange> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApplyChange::getApplyUserId,parm.getApplyId())
                .eq(ApplyChange::getStatus,"0");
        ApplyChange one = applyChangeService.getOne(queryWrapper);
        if (one!=null){
            return ResultUtils.error("申请正在审批中，不要重复提交申请");
        }
        applyChangeService.applySave(parm);
        return ResultUtils.success("申请成功");
    }

    //列表查询
    @GetMapping("/getList")
    public ResultVo getList(ApplyListParm parm) {
        IPage<ApplyChange> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        if (parm.getUserType().equals("0")) {
            LambdaQueryWrapper<ApplyChange> query = new LambdaQueryWrapper<>();
            query.eq(ApplyChange::getApplyUserId, parm.getUserId());
            IPage<ApplyChange> list = applyChangeService.page(page, query);
            return ResultUtils.success("查询成功", list);
        } else {
            IPage<ApplyChange> list = applyChangeService.page(page);
            return ResultUtils.success("查询成功", list);
        }
    }

    @PostMapping("/applyDo")
    public ResultVo applyDo(@RequestBody ApplyDoParm change) {
        if(change.getUserType().equals("0")){
            return ResultUtils.error("您无权限操作!");
        }
        applyChangeService.applyDo(change);
        return ResultUtils.success("操作成功");
    }

}
