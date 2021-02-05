package com.cdqckj.gmis.bizcenter.read.meter.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.base.utils.ExecuteStatus;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterBookBizService;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterPlanBizService;
import com.cdqckj.gmis.readmeter.*;
import com.cdqckj.gmis.readmeter.dto.*;
import com.cdqckj.gmis.readmeter.entity.*;
import com.cdqckj.gmis.readmeter.vo.GasMeterBookRecordVo;
import com.cdqckj.gmis.userarchive.vo.GasMeterCustomerDto;
import com.cdqckj.gmis.userarchive.vo.GasMeterCustomerParme;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReadMeterBookBizServiceImpl extends SuperCenterServiceImpl implements ReadMeterBookBizService {

    @Autowired
    public ReadMeterPlanApi readMeterPlanApi;
    @Autowired
    public ReadMeterPlanScopeApi readMeterPlanScopeApi;
    @Autowired
    public GasMeterBookRecordApi gasMeterBookRecordApi;
    @Autowired
    public ReadMeterBookApi readMeterBookApi;
    @Autowired
    public CustomerBizApi customerBizApi;
    @Autowired
    public ReadMeterDataApi readMeterDataApi;
    @Autowired
    public ReadMeterPlanBizService readMeterPlanService;

    @Autowired
    public ReadMeterLatestRecordApi readMeterLatestApi;
    @Autowired
    public GasMeterBizApi gasMeterBizApi;

    @Override
    public R<Boolean> addByQuery(GasMeterBookRecordPageDTO pageDTO) {
        Long bookId = pageDTO.getReadMeterBook();
        List<GasMeterBookRecordSaveDTO> customerList = saveBookRecord(pageDTO);
        updateBook(bookId);
        //updatePlanAndScope(bookId, customerList);
        return R.success();
    }

    public void updatePlanAndScope(Long bookId, List<GasMeterBookRecordSaveDTO> customerList) {
        List<GasMeterBookRecord> recordList = customerList.stream().map(item -> {
            return BeanUtil.toBean(item, GasMeterBookRecord.class);
        }).collect(Collectors.toList());
        //需要修改抄表计划总户数和计划关联表总户数，以及抄表数据
        List<ReadMeterPlanScope> list= readMeterPlanScopeApi.queryByBookId(Arrays.asList(bookId)).getData();
        Integer addCount = recordList.size();
        Map<Long, Long> planCountMap = list.stream().collect(Collectors.groupingBy(ReadMeterPlanScope::getReadmeterPlanId, Collectors.counting()));
        //修改的计划关联表
        if(addCount>0){
            List<ReadMeterPlanScopeUpdateDTO> scopeUpdateList = list.stream().map(item -> {
                ReadMeterPlanScopeUpdateDTO updateDTO = BeanUtil.toBean(item, ReadMeterPlanScopeUpdateDTO.class);
                updateDTO.setTotalReadMeterCount(updateDTO.getTotalReadMeterCount()+addCount);
                updateDTO.setId(item.getId());
                return updateDTO;
            }).collect(Collectors.toList());
            List<Long> planIds = scopeUpdateList.stream().map(ReadMeterPlanScopeUpdateDTO::getReadmeterPlanId).distinct().collect(Collectors.toList());
            if(planIds.size()>0){
                List<ReadMeterPlan> planList = readMeterPlanApi.queryList(planIds).getData();
                List<ReadMeterPlanUpdateDTO> planUpdateList = planList.stream().map(item -> {
                    ReadMeterPlanUpdateDTO updateDTO = BeanUtil.toBean(item, ReadMeterPlanUpdateDTO.class);
                    updateDTO.setTotalReadMeterCount(updateDTO.getTotalReadMeterCount()+ planCountMap.get(item.getId()).intValue());//addCount
                    updateDTO.setId(item.getId());
                    if(updateDTO.getDataStatus().equals(ExecuteStatus.EXECUTING.getCode())){
                        readMeterPlanService.executePlan(updateDTO, recordList);
                    }
                    return updateDTO;
                }).collect(Collectors.toList());
                readMeterPlanApi.updateBatch(planUpdateList);
                readMeterPlanScopeApi.updateBatch(scopeUpdateList);
            }
        }
    }

    @Override
    public R<Boolean> saveBookRecord(List<GasMeterBookRecordSaveDTO> list) {
        Long bookId = list.get(0).getReadMeterBook();
        gasMeterBookRecordApi.saveRecordList(list);
        // 初始生成客户关联表具的 最新抄表记录，
        this.saveInitMeterLatest(list);
        updateBook(bookId);
        //updatePlanAndScope(bookId, list);
        return R.success();
    }

    /**
     * 初始生成客户关联表具的 最新抄表记录，
     * 可能需要初始选择表具的 最新抄表数据；1.先判断哪些表具(编号)是已经是在系统存在最新抄表数据了,把这些已存在的先排除掉剩下的 表具xx 才是需要初始生成的
     *  ****/
    private void saveInitMeterLatest(List<GasMeterBookRecordSaveDTO> list) {
        // 可能需要初始选择表具的 最新抄表数据；
        // 1.先判断哪些表具(编号)是已经是在系统存在最新抄表数据了,把这些已存在的先排除掉剩下的 表具xx 才是需要初始生成的
        List<ReadMeterLatestRecord> latestRecords = readMeterLatestApi
                .queryListByGasCodes(list.stream().map(GasMeterBookRecordSaveDTO::getGasMeterCode).collect(Collectors.toList())).getData();

        List<GasMeterBookRecordSaveDTO> collectToSaveLatestRecord = list.stream().filter(x -> {
            // x 去latestRecords 的 表codes 匹配，匹配不到才留下，--》
            return latestRecords.stream().allMatch(latest -> !Objects.equals(x.getGasMeterCode(), latest.getGasMeterCode()));
        }).collect(Collectors.toList());
        // 2.组装构造数据
        List<ReadMeterLatestRecordSaveDTO> collect = collectToSaveLatestRecord.stream().map(lt -> {
            ReadMeterLatestRecordSaveDTO save = BeanPlusUtil.toBean(lt, ReadMeterLatestRecordSaveDTO.class);

            save.setYear(2000).setMonth(1);// 还得 设置此 表具初始的年月（2000.01） 和 --》底数（从表具档案获得）
            save.setReadTime(DateUtils.getDate8(2000, 1));
            BigDecimal gasMeterBase = gasMeterBizApi.findGasMeterByCode(lt.getGasMeterCode()).getData().getGasMeterBase();
            save.setCurrentTotalGas(gasMeterBase!=null?gasMeterBase:new BigDecimal(0.0000));

            return save;
        }).collect(Collectors.toList());
        // 保存表对应的最新抄表数据
        readMeterLatestApi.saveList(collect);
    }

    @Override
    public R<Boolean> deleteBookRecord(List<Long> ids) {
        List<GasMeterBookRecord> list = gasMeterBookRecordApi.queryList(ids).getData();
        Boolean bool = gasMeterBookRecordApi.logicalDelete(ids).getIsSuccess();
        if(bool){
            Long bookId = list.get(0).getReadMeterBook();
            List<String> codeList = list.stream().map(GasMeterBookRecord::getGasMeterCode).collect(Collectors.toList());
            readMeterDataApi.deleteByGasCode(codeList);
            updateBook(bookId);
        }
        return R.success();
    }

    @Override
    public R<Page<GasMeterBookRecordVo>> pageBookRecord(PageParams<GasMeterBookRecordPageDTO> params) {
        Integer getType = params.getModel().getGetType();
        if(getType==0){
            return gasMeterBookRecordApi.pageBookRecord(params);
        }else{
            Integer pageNo = (int) params.getCurrent();
            Integer pageSize = (int) params.getSize();
            R<Page<GasMeterBookRecordVo>> result = getUncorrelatedRecord(pageNo, pageSize,params.getModel());
            return result;
        }
    }

    @Override
    public R<Boolean> deleteReadMeterBook(List<Long> ids) {
        ReadMeterBook readMeterBook = readMeterBookApi.queryList(ids).getData().get(0);
        if(readMeterBook.getCitedCount()>0){
            return R.fail("抄表计划进行中，无法删除");
        }
        //删除之前的关联关系
        GasMeterBookRecord bookrecord  = new GasMeterBookRecord();
        bookrecord.setId(readMeterBook.getId());
        R<Boolean> res = gasMeterBookRecordApi.deleteByDto(bookrecord);
        return res.getIsSuccess()? readMeterBookApi.logicalDelete(ids):res;
    }

    /**
     * 修改抄表册的关联总户数
     * @param readMeterBook
     */
    public void updateBook(Long readMeterBook) {
        ReadMeterBookUpdateDTO book = new ReadMeterBookUpdateDTO();
        book.setId(readMeterBook);
        ReadMeterBook readMeter = readMeterBookApi.getById(readMeterBook).getData();
        List<GasMeterBookRecord> list = gasMeterBookRecordApi.queryByBookId(Arrays.asList(readMeterBook)).getData();
        int count = gasMeterBookRecordApi.queryByBookId(Arrays.asList(readMeterBook)).getData().size();
        BeanUtils.copyProperties(readMeter,book);
        book.setTotalReadMeterCount(count);
        readMeterBookApi.update(book);
    }

    /**
     * 根据条件查询出所有未绑定抄表册的用户并保存与抄表册关联关系
     * @param pageDTO
     * @return
     */
    private List<GasMeterBookRecordSaveDTO> saveBookRecord(GasMeterBookRecordPageDTO pageDTO) {
        Integer pageNo = 1;
        Integer pageSize = 10000;
        Page<GasMeterBookRecordVo> pageRecord = getUncorrelatedRecord(pageNo, pageSize,pageDTO).getData();
        if(null!=pageRecord){
            List<GasMeterBookRecordVo> result = pageRecord.getRecords();
            Long communityId = pageDTO.getCommunityId();
            Long bookId = pageDTO.getReadMeterBook();
            ReadMeterBook book = readMeterBookApi.getById(bookId).getData();
            if(null!=bookId){
                //初始化number的值应该是原来总户数加1
                int initNUm = book.getTotalReadMeterCount()+1;
                List<GasMeterBookRecordSaveDTO> list = new ArrayList<>();
                for(int i=0;i<result.size();i++){
                    GasMeterBookRecordVo gasMeterBookRecord = result.get(i);
                    GasMeterBookRecordSaveDTO record = BeanUtil.toBean(gasMeterBookRecord, GasMeterBookRecordSaveDTO.class);
                    record.setReadMeterBook(bookId);
                    record.setNumber(initNUm);
                    record.setGasMeterType("GENERAL_GASMETER");
                    record.setCommunityId(communityId);
                    record.setReadMeterUserName(book.getReadMeterUserName());
                    initNUm++;
                    list.add(record);
                }
                gasMeterBookRecordApi.saveList(list);
                return list;
            }
        }
        return new ArrayList<>();
    }

    /**
     * 获取未关联的用户列表信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    public R<Page<GasMeterBookRecordVo>> getUncorrelatedRecord(Integer pageNo, Integer pageSize,GasMeterBookRecordPageDTO pageDto) {
        R<Page<GasMeterCustomerDto>> pageResult = getGasMeterCustomer(pageNo, pageSize,pageDto);

        Page<GasMeterBookRecordVo> result = new Page<>();
        result.setCurrent(pageNo);
        result.setSize(pageSize);
        result.setTotal(pageResult.getData().getTotal());

        List<GasMeterBookRecordVo> resultList = new ArrayList<>();
        GasMeterBookRecordVo record = null;
        List<GasMeterCustomerDto> list = pageResult.getData().getRecords();
        for(GasMeterCustomerDto dto:list){
            record = new GasMeterBookRecordVo();
            customerDtoToRecord(dto, record);
            resultList.add(record);
        }
        result.setRecords(resultList);
        return R.success(result);
    }

    /**
     * 用户信息对象转抄表关联关系
     * @param dto
     * @param record
     */
    public void customerDtoToRecord(GasMeterCustomerDto dto, GasMeterBookRecordVo record) {
        record.setCustomerName(dto.getCustomerName());
        record.setCustomerCode(dto.getCustomerCode());
        record.setGasMeterCode(dto.getGasCode());
        record.setUseGasTypeId(dto.getUseGasTypeID());
        record.setGasMeterNumber(dto.getGasMeterNumber());
        record.setUseGasTypeName(dto.getUseGasTypeName());
        record.setGasMeterAddress(dto.getGasMeterAddress());
        record.setCommunityName(dto.getCommunityName());
        record.setCustomerChargeNo(dto.getCustomerChargeNo());
        record.setMoreGasMeterAddress(dto.getMoreGasMeterAddress());
    }

    /**
     * 获取没有关联抄表册的用户气表档案信息
     * @param pageNo
     * @param pageSize
     * @return
     */
    public R<Page<GasMeterCustomerDto>> getGasMeterCustomer(Integer pageNo, Integer pageSize,GasMeterBookRecordPageDTO dto) {
        GasMeterBookRecord record = new GasMeterBookRecord();
        record.setDeleteStatus(0);
        // 加上 抄表册id 或者 抄表员用户id 限制查询-->不应该加此条件（因设计就是一个表具只能被一个抄表员去管辖，所以这里查全部）
//        record.setReadMeterBook(dto.getReadMeterBook());
        List<GasMeterBookRecord> recordList = gasMeterBookRecordApi.query(record).getData();
        List<String> idsList = recordList.stream().map(GasMeterBookRecord::getGasMeterCode).collect(Collectors.toList());

        GasMeterCustomerParme para = new GasMeterCustomerParme();
        para.setPageNo(pageNo);
        para.setPageSize(pageSize);
        para.setGasMeterTypeCode("GENERAL_GASMETER");//普表
        // 其余的查询条件
        para.setCustomerName(dto.getCustomerName());
        para.setMoreGasMeterAddress(dto.getMoreGasMeterAddress());
        para.setGasMeterNumber(dto.getGasMeterNumber());
        para.setUseGasTypeId(dto.getUseGasTypeId());
        para.setStreetId(dto.getStreetId());
        if(idsList.size()>0){
            para.setGasCodeNotInList(idsList);
        }
        return getCusmeter(para);
    }

    /**
     * 调用接口获取开户表具和用户信息
     * @param gasMeterCustomerParme 查询条件
     * @return
     */
    private R<Page<GasMeterCustomerDto>> getCusmeter(GasMeterCustomerParme gasMeterCustomerParme) {
        return customerBizApi.findGasMeterCustomer(gasMeterCustomerParme);
    }


//    private static Date getDate(Integer year, Integer month)  {
//        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//        String m = month<10?month+"":"0"+month;
//        Date result =null;
//        try {
//            result = dateFormat1.parse(year + "-" + m + "-01");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }


}