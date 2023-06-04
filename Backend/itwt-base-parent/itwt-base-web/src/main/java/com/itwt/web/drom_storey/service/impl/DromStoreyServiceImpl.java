package com.itwt.web.drom_storey.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itwt.exception_advice.BusinessException;
import com.itwt.web.drom_build.entity.DromBuild;
import com.itwt.web.drom_build.service.DromBuildService;
import com.itwt.web.drom_room.entity.DromRoom;
import com.itwt.web.drom_room.service.DromRoomService;
import com.itwt.web.drom_room_bed.entity.DromRoomBed;
import com.itwt.web.drom_room_bed.service.DromRoomBedService;
import com.itwt.web.drom_storey.entity.DromStorey;
import com.itwt.web.drom_storey.mapper.DromStoreyMapper;
import com.itwt.web.drom_storey.service.DromStoreyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class DromStoreyServiceImpl extends ServiceImpl<DromStoreyMapper, DromStorey> implements DromStoreyService {

    @Autowired
    private DromBuildService dromBuildService;
    @Autowired
    private DromRoomService dromRoomService;
    @Autowired
    private DromRoomBedService dromRoomBedService;

    @Override
    public void addDrom(List<DromStorey> list) {
        this.baseMapper.addDrom(list);
    }

    @Override
    @Transactional
    public void add(DromStorey dromStorey) {
        int insert = this.baseMapper.insert(dromStorey);
        if (insert > 0) {
            DromBuild byId = dromBuildService.getById(dromStorey.getBuildId());
            DromBuild newId = new DromBuild();
            newId.setBuildId(byId.getBuildId());
            newId.setBuildStorey(byId.getBuildStorey() + 1L);
            dromBuildService.updateById(newId);
        }
    }

    @Override
    @Transactional
    public void edit(DromStorey dromStorey) {
        this.baseMapper.updateById(dromStorey);
//        if (insert>0){
//            DromBuild byId = dromBuildService.getById(dromStorey.getBuildId());
//            DromBuild newId = new DromBuild();
//            newId.setBuildId(byId.getBuildId());
//            newId.setBuildStorey(byId.getBuildStorey()-1L);
//            dromBuildService.updateById(newId);
//        }
    }

    @Override
    @Transactional
    public void delete(Long buildId, Long storeyId) {
        int i = this.baseMapper.deleteById(storeyId);
        if (i > 0) {
            DromBuild byId = dromBuildService.getById(buildId);
            DromBuild newId = new DromBuild();
            newId.setBuildId(byId.getBuildId());
            newId.setBuildStorey(byId.getBuildStorey() - 1L);
            dromBuildService.updateById(newId);
        }
    }


    @Override
    public void initBed(DromStorey dromStorey) {
        //根据层数，查询所有的宿舍
        LambdaQueryWrapper<DromRoom> query = new LambdaQueryWrapper<>();
        query.eq(DromRoom::getStoreyId,dromStorey.getStoreyId());
        List<DromRoom> list = dromRoomService.list(query);
        if(list.size() == 0) throw  new BusinessException(500,"请先设置宿舍编号");
        //循环处理宿舍的床位
        List<DromRoomBed> bedList = new ArrayList<>();
        for (int i=0;i<list.size();i++){
            //获取宿舍的人数
            int bedTotal = list.get(i).getTotalBed();
            for (int j = 1;j<=bedTotal;j++){
                //查询床位是否已经设置
                QueryWrapper<DromRoomBed> queryBed = new QueryWrapper<>();
                queryBed.lambda().eq(DromRoomBed::getRoomId,list.get(i).getRoomId())
                        .eq(DromRoomBed::getBedCode,list.get(i).getRoomCode()+"-"+j); //A102-1
                DromRoomBed one = dromRoomBedService.getOne(queryBed);
                if(one == null){ //没有查到，设置床位
                    DromRoomBed bed = new DromRoomBed();
                    bed.setRoomId(list.get(i).getRoomId());
                    bed.setBedCode(list.get(i).getRoomCode()+"-"+j);
                    bedList.add(bed);
                }
            }
        }
        //存入数据库
        if(bedList.size() >0){
            dromRoomBedService.saveBatch(bedList);
        }
    }
}
