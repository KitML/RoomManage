package com.itwt.web.drom_room.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itwt.utils.ResultUtils;
import com.itwt.utils.ResultVo;
import com.itwt.web.drom_room.entity.DromRoom;
import com.itwt.web.drom_room.entity.RoomListParm;
import com.itwt.web.drom_room.entity.RoomParm;
import com.itwt.web.drom_room.entity.RoomTree;
import com.itwt.web.drom_room.service.DromRoomService;
import com.itwt.web.drom_room_bed.entity.DromRoomBed;
import com.itwt.web.drom_room_bed.entity.DromRoomVo;
import com.itwt.web.drom_room_bed.service.DromRoomBedService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dormRoom")
public class DromRoomController {

    @Autowired
    private DromRoomService dromRoomService;
    @Autowired
    private DromRoomBedService dromRoomBedService;


    //新增
    @PostMapping
    public ResultVo add(@RequestBody RoomParm parm){
        //起始编号
        Integer start = parm.getStart();
        List<DromRoom> list = new ArrayList<>();
        while (start <= parm.getEnd()){
            //房间实体类创建
            DromRoom dromRoom = new DromRoom();
            dromRoom.setTotalBed(parm.getStuNum());
            dromRoom.setRootType(parm.getRootType());
            dromRoom.setStoreyId(parm.getStoreyId());
            //构造编号 A100
            if (StringUtils.isNotEmpty(parm.getArea())){
                dromRoom.setRoomCode(parm.getArea()+start);
            }else{
                //100
                dromRoom.setRoomCode(Integer.toString(start));
            }
            //判断是否重复
            LambdaQueryWrapper<DromRoom> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DromRoom::getRoomCode,dromRoom.getRoomCode());
            DromRoom one = dromRoomService.getOne(queryWrapper);
            if (one == null){//没有重复就放入list
                list.add(dromRoom);
            }
            //退出循环
            start = start + 1;
        }
        //保存房间
        if (list.size()>0){
            dromRoomService.saveBatch(list);
        }
        return ResultUtils.success("设置成功！");
    }

    //编辑房间
    @PutMapping
    public ResultVo edit(@RequestBody DromRoom room){
        //判断编号是否重复
        LambdaQueryWrapper<DromRoom> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DromRoom::getRoomCode,room.getRoomCode());
        DromRoom one = dromRoomService.getOne(queryWrapper);
        if (one != null){
            return ResultUtils.error("编号重复");
        }
        boolean b = dromRoomService.updateById(room);
        if (b){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }

    //删除
    @DeleteMapping("/{roomId}")
    public ResultVo delete(@PathVariable("roomId")Long roomId){
        //判断房间下是否住人
        //无人住宿才能删除
        boolean b = dromRoomService.removeById(roomId);
        if (b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }

//    //列表查询
//    @GetMapping("/list")
//    public ResultVo getList(RoomListParm parm){
//        //构造分页对象
//        IPage<DromRoom> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
//        LambdaQueryWrapper<DromRoom> queryWrapper = new LambdaQueryWrapper<>();
//        queryWrapper.eq(DromRoom::getStoreyId,parm.getStoreyId());
//        IPage<DromRoom> page1 = dromRoomService.page(page, queryWrapper);
//        //查询宿舍对应的床位
//        if (page1.getRecords().size()>0){
//            for (int i=0;i<page1.getRecords().size();i++){
//                //获取宿舍id
//                Long roomId = page1.getRecords().get(i).getRoomId();
//                //查询宿舍对应的床位
//                LambdaQueryWrapper<DromRoomBed> wrapper = new LambdaQueryWrapper<>();
//                wrapper.eq(DromRoomBed::getRoomId,roomId);
//                List<DromRoomBed> list = dromRoomBedService.list(wrapper);
//                page1.getRecords().get(i).setBedList(list);
//            }
//        }
//        return ResultUtils.success("查询成功",page1);
//    }

    @GetMapping("/list")
    public ResultVo getList(RoomListParm parm) {
        //构造分页对象
        IPage<DromRoom> page = new Page<>(parm.getCurrentPage(), parm.getPageSize());
        LambdaQueryWrapper<DromRoom> query = new LambdaQueryWrapper<>();
        query.eq(DromRoom::getStoreyId, parm.getStoreyId());
        IPage<DromRoom> list = dromRoomService.page(page, query);
        //查询宿舍对应的床位
        if (list.getRecords().size() > 0) {
            for (int i = 0; i < list.getRecords().size(); i++) {
                //获取宿舍id
                Long roomId = list.getRecords().get(i).getRoomId();
                //查询宿舍对应的床位
                LambdaQueryWrapper<DromRoomBed> queryBed = new LambdaQueryWrapper<>();
                queryBed.eq(DromRoomBed::getRoomId, roomId);
                List<DromRoomVo> bedList = dromRoomBedService.getSelectBedList(roomId);
                list.getRecords().get(i).setBedList(bedList);
            }
        }
        return ResultUtils.success("查询成功", list);
    }

    //根据层数id查询宿舍和床位
    @GetMapping("/getRoomByStoreyId")
    public ResultVo getRoomByStoreyId(Long storeyId){
        List<RoomTree> roomTree = dromRoomService.getRoomTree(storeyId);
        return ResultUtils.success("查询成功",roomTree);
    }
}
