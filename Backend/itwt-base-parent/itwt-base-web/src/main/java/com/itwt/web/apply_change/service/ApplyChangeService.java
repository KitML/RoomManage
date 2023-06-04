package com.itwt.web.apply_change.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itwt.web.apply_change.entity.ApplyChange;
import com.itwt.web.apply_change.entity.ApplyDoParm;
import com.itwt.web.apply_change.entity.ApplyParm;

public interface ApplyChangeService extends IService<ApplyChange> {
    void applySave(ApplyParm parm);
    void applyDo(ApplyDoParm change);
}
