package com.cdqckj.gmis.bizcenter.read.meter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.base.utils.ExecuteStatus;
import com.cdqckj.gmis.base.utils.RecordYear;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterPlanBizService;
import com.cdqckj.gmis.readmeter.*;
import com.cdqckj.gmis.readmeter.dto.*;
import com.cdqckj.gmis.readmeter.entity.*;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.vo.ReadMeterPlanScopeParam;
import com.cdqckj.gmis.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReadMeterPlanBizServiceImpl extends SuperCenterServiceImpl implements ReadMeterPlanBizService {

    @Autowired
    public ReadMeterPlanApi readMeterPlanApi;
    @Autowired
    public ReadMeterPlanScopeApi readMeterPlanScopeApi;
    @Autowired
    public ReadMeterDataApi readMeterDataApi;
    @Autowired
    public ReadMeterBookApi readMeterBookApi;
    @Autowired
    public CustomerBizApi customerBizApi;
    @Autowired
    public GasMeterBookRecordApi gasMeterBookRecordApi;
    @Autowired
    public ReadMeterMonthGasApi readMeterMonthGasApi;

    private final Integer MONTH_LENGTH = 3;

    @Override
    public R<ReadMeterPlan> updateSatus(ReadMeterPlanUpdateDTO readMeterPlanUpdateDTO) {

        Integer code = readMeterPlanUpdateDTO.getDataStatus();
        ExecuteStatus status = ExecuteStatus.getType(code);
        switch (status){
            case FINISH:
                return R.fail("计划不能手动完成");
            case NOT_STARTED:
                return R.fail("计划不能手动未执行");
            case EXECUTING:
                //根据计划获取抄表册关联客户列表
                List<GasMeterBookRecord> recordList = getGasMeterBookRecords(readMeterPlanUpdateDTO, status);
                if(recordList.size()==0){
                    return R.fail("请先关联有效抄表册");
                }
                executePlan(readMeterPlanUpdateDTO, recordList);
                break;
            case SUSPEND:
                suspendPlan(readMeterPlanUpdateDTO);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + status);
        }
        return readMeterPlanApi.update(readMeterPlanUpdateDTO);
    }

    /**
     * 暂停计划（修改抄表数据状态）
     * @param readMeterPlanUpdateDTO
     */
    public void suspendPlan(ReadMeterPlanUpdateDTO readMeterPlanUpdateDTO) {
        renewReadMeterData(readMeterPlanUpdateDTO);
        renewPlanScope(readMeterPlanUpdateDTO);
    }

    /**
     * 更新抄表任务分配表
     * @param readMeterPlanUpdateDTO
     */
    public void renewPlanScope(ReadMeterPlanUpdateDTO readMeterPlanUpdateDTO) {
        Long planId = readMeterPlanUpdateDTO.getId();
        readMeterPlanScopeApi.updateByPlanId(planId, ExecuteStatus.SUSPEND);
    }

    /**
     * 跟新抄表数据
     * @param readMeterPlanUpdateDTO
     */
    public void renewReadMeterData(ReadMeterPlanUpdateDTO readMeterPlanUpdateDTO) {
        ReadMeterData readMeterData = new ReadMeterData();
        readMeterData.setPlanId(readMeterPlanUpdateDTO.getId());
        List<ReadMeterData> dataList = readMeterDataApi.query(readMeterData).getData();
        List<ReadMeterDataUpdateDTO> list = dataList.stream().map(dto -> {
            ReadMeterDataUpdateDTO updateDTO = BeanUtil.toBean(dto, ReadMeterDataUpdateDTO.class);
            updateDTO.setId(dto.getId());
            updateDTO.setDataStatus(ExecuteStatus.SUSPEND.getCode());
            return updateDTO;
        }).collect(Collectors.toList());
        readMeterDataApi.updateBatch(list);
    }

    /**
     * 执行计划
     * @param readMeterPlanUpdateDTO
     * @param recordList
     */
    @Override
    public void executePlan(ReadMeterPlanUpdateDTO readMeterPlanUpdateDTO, List<GasMeterBookRecord> recordList) {
        Integer year = readMeterPlanUpdateDTO.getReadMeterYear();
        Integer month = readMeterPlanUpdateDTO.getReadMeterMonth();
        Integer lastMonth = month- RecordYear.JAN.getCode() ==0 ? RecordYear.DEC.getCode():month-1;
        Integer lastYear = lastMonth==RecordYear.DEC.getCode()? year-1:year;
        /*//根据计划获取抄表册关联客户列表
        List<GasMeterBookRecord> recordList = getGasMeterBookRecords(readMeterPlanUpdateDTO, status);*/
        //从客户列表获取气表编码集合
        List<String> meterCodeList = recordList.stream().map(GasMeterBookRecord :: getGasMeterCode).collect(Collectors.toList());
        //根据气表编码计划获取年度每月用气止数
        List<ReadMeterMonthGas> list = readMeterMonthGasApi.getByYearAndGasCode(lastYear,meterCodeList).getData();
        recordList = filterMonthData(lastYear, lastMonth, recordList, list);
        savePlanData(readMeterPlanUpdateDTO.getId(), month, year, recordList, list);
    }

    /**
     * 根据月份获取前三个月月份值
     * @param lastMonth
     */
    public List<Integer> getMonths(Integer lastMonth) {
        List<Integer> monthList = new ArrayList<>();
        for(int i=1;i<=MONTH_LENGTH;i++){
            int a = lastMonth-i;
            if(a<=0){
                a = 12+a;
            }
            monthList.add(a);
        }
        return monthList;
    }

    /**
     * 根据计划获取抄表册关联客户列表
     * @param readMeterPlanUpdateDTO
     * @param status
     * @return
     */
    public List<GasMeterBookRecord> getGasMeterBookRecords(ReadMeterPlanUpdateDTO readMeterPlanUpdateDTO, ExecuteStatus status) {
        //拼接抄表册与抄表计划关联实体
        Long planId = readMeterPlanUpdateDTO.getId();
        //根据抄表计划id获取抄表计划中间表
        List<ReadMeterPlanScope> scopeList = readMeterPlanScopeApi.updateByPlanId(planId, status).getData();
        //根据关联表获取抄表册id集合
        List<Long> bookIds = scopeList.stream().map(ReadMeterPlanScope :: getBookId).collect(Collectors.toList());
        //根抄表册id集合获取抄表册关联客户信息
        return bookIds.size()>0?gasMeterBookRecordApi.queryByBookId(bookIds).getData():new ArrayList<GasMeterBookRecord>();
    }

    /**
     * 气表月指数统计表筛选和更新
     * @param lastYear
     * @param recordList 客户列表
     * @param list 年度每月用气止数
     * @return
     */
    public List<GasMeterBookRecord> filterMonthData(Integer lastYear, Integer lastMonth, List<GasMeterBookRecord> recordList, List<ReadMeterMonthGas> list) {
        if(list.size()!=recordList.size()){
            recordList = recordList.stream().filter((GasMeterBookRecord s) -> !list.contains(s.getGasMeterCode())).collect(Collectors.toList());
            List<ReadMeterMonthGasSaveDTO> gasList = recordList.stream().map(data ->{
                ReadMeterMonthGasSaveDTO gasSaveDTO = new ReadMeterMonthGasSaveDTO();
                gasSaveDTO.setCustomerCode(data.getCustomerCode());
                gasSaveDTO.setCustomerId(data.getCustomerId());
                gasSaveDTO.setCustomerName(data.getCustomerName());
                gasSaveDTO.setGasMeterCode(data.getGasMeterCode());
                gasSaveDTO.setYear(lastYear);
                setOrGetLastTotalGas(lastMonth, gasSaveDTO, null,null);
                return gasSaveDTO;
            }).collect(Collectors.toList());
            readMeterMonthGasApi.saveList(gasList);
        }
        return recordList;
    }

    /**
     * 生成抄表数据
     * @param lastMonth
     * @param lastYear
     * @param recordList
     * @param list
     */
    public void savePlanData(Long planId, Integer lastMonth, Integer lastYear, List<GasMeterBookRecord> recordList, List<ReadMeterMonthGas> list) {
        ReadMeterData data = new ReadMeterData();
        data.setPlanId(planId);
        List<ReadMeterData> examineList = readMeterDataApi.query(data).getData();
        List<ReadMeterDataUpdateDTO> updateDTOList = examineList.stream().map(dto ->{
            ReadMeterDataUpdateDTO updateDTO = BeanUtil.toBean(dto, ReadMeterDataUpdateDTO.class);
            updateDTO.setId(dto.getId());
            if(updateDTO.getCurrentTotalGas()==null){
                updateDTO.setDataStatus(ExecuteStatus.NOT_STARTED.getCode());
            }else{
                updateDTO.setDataStatus(ExecuteStatus.FINISH.getCode());
            }
            return updateDTO;
        }).collect(Collectors.toList());
        List<String> gasCodeList = updateDTOList.stream().map(ReadMeterDataUpdateDTO::getGasMeterCode).collect(Collectors.toList());
        Map<String,ReadMeterMonthGas> map = list.stream().collect(Collectors.toMap(ReadMeterMonthGas::getGasMeterCode, a -> a,(k1, k2)->k1));
        //recordList = recordList.stream().filter(item -> (!gasCodeList.contains(item.getGasMeterCode()))).collect(Collectors.toList());
        List<ReadMeterDataSaveDTO> addList = recordList.stream().filter(item -> (!gasCodeList.contains(item.getGasMeterCode()))).map(record -> {
            ReadMeterDataSaveDTO readMeterData = new ReadMeterDataSaveDTO();
            readMeterData.setCustomerCode(record.getCustomerCode());
            readMeterData.setCustomerName(record.getCustomerName());
            readMeterData.setGasMeterCode(record.getGasMeterCode());
            readMeterData.setMoreGasMeterAddress(record.getMoreGasMeterAddress());
            readMeterData.setBookId(record.getReadMeterBook());
            readMeterData.setGasMeterNumber(record.getGasMeterNumber());
            readMeterData.setUseGasTypeId(record.getUseGasTypeId());
            readMeterData.setUseGasTypeName(record.getUseGasTypeName());
            // 关联抄表员id
            readMeterData.setReadMeterUserId(record.getReadMeterUser());
            readMeterData.setReadMeterUserName(record.getReadMeterUserName());
            readMeterData.setPlanId(planId);
            readMeterData.setReadMeterYear(lastYear);
            readMeterData.setReadMeterMonth(lastMonth);
            readMeterData.setProcessStatus(ProcessEnum.TO_BE_REVIEWED);
            //readMeterData.setChargeStatus(null);
            try {
//                Date date = getDate(lastYear,lastMonth);
                readMeterData.setReadTime(DateUtils.getDate8(lastYear,lastMonth));
            } catch (Exception e) {
                e.printStackTrace();
            }
            ReadMeterMonthGas gas = map.get(record.getGasMeterCode());
            setOrGetLastTotalGas(lastMonth - 1,null, readMeterData, gas);
            return readMeterData;
        }).collect(Collectors.toList());
        readMeterDataApi.updateBatch(updateDTOList);
        readMeterDataApi.saveList(addList);
    }

    /**
     * 根据月份查询字段并赋值
     * @param lastMonth
     * @param gasSaveDTO
     * @param readMeterData
     * @param gas
     */
    public void setOrGetLastTotalGas(Integer lastMonth, ReadMeterMonthGasSaveDTO gasSaveDTO, ReadMeterDataSaveDTO readMeterData, ReadMeterMonthGas gas) {
        String str = RecordYear.getType(lastMonth);
        try {
            if(null!=gasSaveDTO){
                Field field = ReadMeterMonthGasSaveDTO.class.getDeclaredField(str);
                field.setAccessible(true);
                field.set(gasSaveDTO,new BigDecimal(0));
            }else{
                Field field = ReadMeterMonthGas.class.getDeclaredField(str);
                field.setAccessible(true);
                BigDecimal number =  gas==null? new BigDecimal(0):(BigDecimal)field.get(gas);
                readMeterData.setLastTotalGas(number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public R<ReadMeterPlanScope> saveReadMeterPlanScope(ReadMeterPlanScopeSaveDTO readMeterPlanScopeSaveDTO) {
        Long planId = readMeterPlanScopeSaveDTO.getReadmeterPlanId();
        ReadMeterPlan plan = readMeterPlanApi.getById(planId).getData();
        ExecuteStatus status = ExecuteStatus.getType(plan.getDataStatus());
        if(status!=ExecuteStatus.NOT_STARTED){
            return R.fail("当前任务"+status.getDescription()+",不能分配");
        }else{
            //可以省略
            if (checkPlanAndBook(readMeterPlanScopeSaveDTO, plan)) {
                return R.fail("同一抄表册每年每月只能被加入抄表计划一次");
            }
        }
        Long bookId = readMeterPlanScopeSaveDTO.getBookId();
        ReadMeterBook book = readMeterBookApi.getById(bookId).getData();
        Integer count = book.getTotalReadMeterCount();
        readMeterPlanScopeSaveDTO.setTotalReadMeterCount(count);

        renewPlan(planId, plan, count);

        return readMeterPlanScopeApi.save(readMeterPlanScopeSaveDTO);
    }

    @Override
    public R<Boolean> savePlanScopeList(ReadMeterPlanScopeParam param) {
        Long planId = param.getPlanId();
        List<Long> bookIds = param.getBookIds();
        ReadMeterPlan plan = readMeterPlanApi.getById(planId).getData();
        ExecuteStatus status = ExecuteStatus.getType(plan.getDataStatus());
        if(status==ExecuteStatus.FINISH){
            return R.fail("当前任务已完结,不能分配");
        }
        int countAll =0;
        List<ReadMeterPlanScopeSaveDTO> saveList = new ArrayList<>();
        ReadMeterPlanScopeSaveDTO scopeSaveDTO = null;
        for(Long bookId:bookIds){
            scopeSaveDTO = new ReadMeterPlanScopeSaveDTO();
            ReadMeterBook book = readMeterBookApi.getById(bookId).getData();
            int count = book.getTotalReadMeterCount();
            countAll += count;
            scopeSaveDTO.setTotalReadMeterCount(count);
            scopeSaveDTO.setBookId(bookId);
            scopeSaveDTO.setReadmeterPlanId(planId);
            scopeSaveDTO.setBookName(book.getBookName());
            scopeSaveDTO.setReadMeterUserName(book.getReadMeterUserName());
            scopeSaveDTO.setReadMeterUser(book.getReadMeterUser());
            saveList.add(scopeSaveDTO);
        }
        ReadMeterPlanUpdateDTO updateDTO = renewPlan(planId, plan, countAll);
        readMeterPlanScopeApi.saveList(saveList);
        switch (status){
            case EXECUTING:
                updateSatus(updateDTO);
                break;
            case SUSPEND:
                updateDTO.setDataStatus(ExecuteStatus.EXECUTING.getCode());
                updateSatus(updateDTO);
                updateDTO.setDataStatus(ExecuteStatus.SUSPEND.getCode());
                updateSatus(updateDTO);
                break;
            default:
                break;
        }
        return R.success();
    }

    /**
     * 更新计划总户数
     * @param planId
     * @param plan
     * @param countAll
     */
    public ReadMeterPlanUpdateDTO renewPlan(Long planId, ReadMeterPlan plan, int countAll) {
        ReadMeterPlanUpdateDTO planDTO = new ReadMeterPlanUpdateDTO();
        planDTO.setId(planId);
        BeanUtils.copyProperties(plan, planDTO);
        planDTO.setTotalReadMeterCount(countAll + plan.getTotalReadMeterCount());
        readMeterPlanApi.update(planDTO);
        return planDTO;
    }

    public boolean checkPlanAndBook(ReadMeterPlanScopeSaveDTO readMeterPlanScopeSaveDTO, ReadMeterPlan plan) {
        List<ReadMeterPlanScope> scopeList = getPlanScopes(readMeterPlanScopeSaveDTO, plan);
        Integer count = scopeList.size();
        if(count==1){
            return true;
        }
        return false;
    }

    @Override
    public R<Page<ReadMeterBook>> pageUnboundBook(PageParams<ReadMeterPlan> params) {
        return readMeterBookApi.queryByWrap(params);
    }

    /**
     * 获取同年月下的抄表计划和抄表册关联信息（只允许存在一条）
     * @param readMeterPlanScopeSaveDTO
     * @param plan
     * @return
     */
    public List<ReadMeterPlanScope> getPlanScopes(ReadMeterPlanScopeSaveDTO readMeterPlanScopeSaveDTO, ReadMeterPlan plan) {
        Integer month = plan.getReadMeterMonth();
        Integer year = plan.getReadMeterYear();
        ReadMeterPlan planDto = new ReadMeterPlan();
        planDto.setReadMeterMonth(month).setReadMeterYear(year);
        List<ReadMeterPlan> list = readMeterPlanApi.query(planDto).getData();
        List<Long> idsList = list.stream().map(ReadMeterPlan::getId).collect(Collectors.toList());
        return readMeterPlanScopeApi.queryByWrap(idsList,readMeterPlanScopeSaveDTO.getBookId()).getData();
    }

    public Date getDate(Integer year,Integer month) throws Exception {
        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        String m = month<10?month+"":"0"+month;
        return dateFormat1.parse(year+"-"+m+"-01");
    }
}