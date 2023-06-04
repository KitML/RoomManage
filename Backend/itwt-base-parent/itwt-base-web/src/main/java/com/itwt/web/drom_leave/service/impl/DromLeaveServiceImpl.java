package com.itwt.web.drom_leave.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwt.web.drom_leave.entity.DromLeave;
import com.itwt.web.drom_leave.mapper.DromLeaveMapper;
import com.itwt.web.drom_leave.service.DromLeaveService;
import org.springframework.stereotype.Service;

@Service
public class DromLeaveServiceImpl extends ServiceImpl<DromLeaveMapper, DromLeave> implements DromLeaveService {
}
