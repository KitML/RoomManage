import http from "@/utils/http";
//根据层数id查询宿舍
export const getRoomByStoreyIdApi = async(parm)=>{
    return await http.get("/api/dormRoom/getRoomByStoreyId",parm)
}
//分配宿舍保存
export const assignSaveBedApi = async(parm)=>{
    return await http.post("/api/assignBed/assignSaveBed",parm)
}
//分配宿舍列表
export const getAssignBedListApi = async(parm)=>{
    return await http.get("/api/assignBed/getAssignBedList",parm)
}
//删除
export const deleteAssignApi = async(parm)=>{
    return await http.delete("/api/assignBed",parm)
}
//学生查询宿舍列表
export const getRoomByClassIdApi = async(parm)=>{
    return await http.get("/api/assignBed/getRoomByClassId",parm)
}
//保存选择宿舍
export const saveSelectBedApi = async(parm)=>{
    return await http.post("/api/stuBed/selectBedSave",parm);
}
//清空宿舍
export const clearBedApi = async(parm)=>{
    return await http.post("/api/assignBed/clearBed",parm)
}