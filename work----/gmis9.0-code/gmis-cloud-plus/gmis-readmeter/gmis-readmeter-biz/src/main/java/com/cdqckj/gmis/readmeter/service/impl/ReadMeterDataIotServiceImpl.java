package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.api.GasmeterArrearsDetailBizApi;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.readmeter.dao.ReadMeterDataIotMapper;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotPageDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.enumeration.DataTypeEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.enumeration.ProcessIotEnum;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataIotService;
import com.cdqckj.gmis.readmeter.vo.ReadMeterDataIotVo;
import com.cdqckj.gmis.readmeter.vo.SettlementArrearsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReadMeterDataIotServiceImpl extends SuperServiceImpl<ReadMeterDataIotMapper, ReadMeterDataIot> implements ReadMeterDataIotService {
    @Autowired
    public ReadMeterDataIotMapper readMeterDataIotMapper;
    @Autowired
    public GasMeterBizApi gasMeterBizApi;
    @Autowired
    public GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;
    /**
     * 根据气表编码，抄表年月，查询抄表数据（默认查没有审核或者未通过审核的）
     * @param meterCodelist
     * @param yearlist
     * @param monthlist
     * @param process
     * @return
     */
    @Override
    public R<List<ReadMeterDataIot>> getMeterDataByCode(List<String> meterCodelist, List<Integer> yearlist, List<Integer> monthlist,ProcessEnum process) {
        LambdaQueryWrapper<ReadMeterDataIot> lambda = new LambdaQueryWrapper<>();
        lambda.in(ReadMeterDataIot::getGasMeterCode, meterCodelist);
        lambda.in(ReadMeterDataIot::getReadMeterYear, yearlist);
        lambda.in(ReadMeterDataIot::getReadMeterMonth, monthlist);
        lambda.eq(ReadMeterDataIot::getDataStatus,-1);
        if(null==process){
            lambda.or().eq(ReadMeterDataIot::getProcessStatus, null).ne(ReadMeterDataIot::getProcessStatus, ProcessIotEnum.APPROVED);
        }else{
            lambda.eq(ReadMeterDataIot::getProcessStatus, process);
        }
        List<ReadMeterDataIot> ReadMeterMonthGasList = readMeterDataIotMapper.getMeterDataByCode(lambda);
        return R.success(ReadMeterMonthGasList);
    }

    @Override
    public R<List<ReadMeterDataIot>> getHistory(List<String> meterCodelist, Date time) {
        LambdaQueryWrapper<ReadMeterDataIot> lambda = new LambdaQueryWrapper<>();
        lambda.in(ReadMeterDataIot::getGasMeterCode, meterCodelist);
        lambda.le(ReadMeterDataIot::getReadTime, time);
        lambda.eq(ReadMeterDataIot::getDataStatus,0);
        lambda.orderByDesc(ReadMeterDataIot::getReadTime);
        lambda.last("LIMIT 3");
        List<ReadMeterDataIot> dataList = readMeterDataIotMapper.getMeterDataByCode(lambda);
        return R.success(dataList);
    }

    @Override
    public Boolean check(LocalDate sDate, String meterId,String customerCode,String gasMeterCode) {
        return super.count(Wraps.<ReadMeterDataIot>lbQ()
                .eq(ReadMeterDataIot::getGasMeterNumber,meterId)
                .eq(ReadMeterDataIot::getCustomerCode,customerCode)
                .eq(ReadMeterDataIot::getGasMeterCode,gasMeterCode)
                .eq(ReadMeterDataIot::getDataStatus,1)
                .eq(ReadMeterDataIot::getDataType,0)
                .eq(ReadMeterDataIot::getRecordTime,sDate)) > 0;
    }

    @Override
    public List<ReadMeterDataIot> getCQData(LocalDate sDate, String meterId, String customerCode, String gasMeterCode) {
        List<ReadMeterDataIot> cQData = baseMapper.selectList(Wraps.<ReadMeterDataIot>lbQ()
                .eq(ReadMeterDataIot::getDataStatus,0)
                .eq(ReadMeterDataIot::getGasMeterNumber,meterId)
                .eq(ReadMeterDataIot::getCustomerCode,customerCode)
                .eq(ReadMeterDataIot::getGasMeterCode,gasMeterCode)
                .eq(ReadMeterDataIot::getRecordTime,sDate)
                .and(wrapper -> wrapper.eq(ReadMeterDataIot::getDataType,2).or()
                        .eq(ReadMeterDataIot::getDataType,3))

                .orderByDesc(ReadMeterDataIot::getDataTime));
        return cQData;
    }

    @Override
    public List<ReadMeterDataIot> getSettlementCQData(String gasMeterCode,String customerCode) {
        List<ReadMeterDataIot> cQData = baseMapper.selectList(Wraps.<ReadMeterDataIot>lbQ()
                .eq(ReadMeterDataIot::getDataStatus,0)
                .eq(ReadMeterDataIot::getGasMeterCode,gasMeterCode)
                .eq(ReadMeterDataIot::getCustomerCode,customerCode)
                .eq(ReadMeterDataIot::getProcessStatus,ProcessIotEnum.SETTLED)
                .and(wrapper -> wrapper.eq(ReadMeterDataIot::getDataType,2).or()
                        .eq(ReadMeterDataIot::getDataType,3))
                .orderByDesc(ReadMeterDataIot::getDataTime));
        return cQData;
    }

    @Override
    public ReadMeterDataIot getDataByMeterNo(String gasMeterNumber) {
        LbqWrapper<ReadMeterDataIot> wrapper = new LbqWrapper<>();
        wrapper.eq(ReadMeterDataIot::getGasMeterNumber,gasMeterNumber);
        wrapper.orderByDesc(ReadMeterDataIot::getDataTime);
        Page<ReadMeterDataIot> page =new Page<>(1,1);
        Page<ReadMeterDataIot> pageRes = baseMapper.selectPage(page,wrapper);
        if(pageRes!=null&&pageRes.getRecords().size()==1){
            return pageRes.getRecords().get(0);
        }
        return null;
    }

    @Override
    public List<ReadMeterDataIot> getUnSettlementData() {
        LbqWrapper<ReadMeterDataIot> wrapper = new LbqWrapper<>();
        wrapper.isNull(ReadMeterDataIot::getSettlementTime);
        wrapper.eq(ReadMeterDataIot::getProcessStatus,ProcessIotEnum.APPROVED);
        wrapper.eq(ReadMeterDataIot::getDataStatus,1);
        wrapper.orderByAsc(ReadMeterDataIot::getDataTime);
        List<ReadMeterDataIot> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public ReadMeterDataIot getPreviousData(String gasMeterCode, String gasMeterNumber,String customerCode,LocalDateTime dataTime) {
        return baseMapper.queryPreviousData(gasMeterCode, gasMeterNumber,customerCode,dataTime);
    }

    @Override
    public List<ReadMeterDataIot> settlement() {
        List<ReadMeterDataIot> list = getUnSettlementData();
        if(list==null||list.size()==0){return null;}
        List<ReadMeterDataIot> settlementList = new CopyOnWriteArrayList<>();
        Map<String, List<ReadMeterDataIot>> listMeterNo = list.stream().
                collect(Collectors.groupingBy(ReadMeterDataIot::getGasMeterNumber));
        listMeterNo.forEach((k,v)->{
            settlementList.addAll(v);
        });
        return settlementList;
    }

    /**
     *  分页查询列表
     *
     * @param params 分页参数
     * @return 分页列表数据
     */
    @Override
    public Page<ReadMeterDataIotVo> pageList(PageParams<ReadMeterDataIotPageDTO> params) {
        IPage<ReadMeterDataIotVo> page = params.getPage();
        params.getModel().setDataType(DataTypeEnum.ORDINARY_DATA.getCode());
        return baseMapper.pageList(page, params.getModel());
    }

    @Override
    public List<ReadMeterDataIot> getSettlementArrears(SettlementArrearsVO arrearsVO) {
        LbqWrapper<ReadMeterDataIot> wrapper = new LbqWrapper<>();
        wrapper.eq(ReadMeterDataIot::getGasMeterCode,arrearsVO.getGasMeterCode());
        wrapper.eq(ReadMeterDataIot::getDataStatus,1);
//        wrapper.eq(ReadMeterDataIot::getChargeStatus,ProcessEnum);
        wrapper.between(ReadMeterDataIot::getRecordTime,arrearsVO.getStartDate(),arrearsVO.getEndDate());
        List<ReadMeterDataIot> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<ReadMeterDataIot> getArrearsRecord(LocalDate arrearsDate) {
        LbqWrapper<ReadMeterDataIot> wrapper = new LbqWrapper<>();
        wrapper.eq(ReadMeterDataIot::getDataStatus,1);
        wrapper.eq(ReadMeterDataIot::getIsCreateArrears,0);
        wrapper.le(ReadMeterDataIot::getRecordTime,arrearsDate);
        wrapper.eq(ReadMeterDataIot::getMeterType, OrderSourceNameEnum.REMOTE_READMETER.getCode());
        List<ReadMeterDataIot> list = baseMapper.selectList(wrapper);
        return list;
    }

    @Override
    public List<ReadMeterDataIot> findSettleData(String gasMeterCode, String customerCode) {
        return baseMapper.findSettleData(gasMeterCode,customerCode);
    }

    @Override
    public Boolean updateDataRefundIot(List<Long> idsList) {
        int counts = baseMapper.update(null,Wraps.<ReadMeterDataIot>lbU()
                .set(ReadMeterDataIot::getSettlementTime,null)
                .set(ReadMeterDataIot::getSettlementUserId,null)
                .set(ReadMeterDataIot::getSettlementUserName,null)
                .set(ReadMeterDataIot::getChargeStatus,null)
                .set(ReadMeterDataIot::getProcessStatus, ProcessIotEnum.APPROVED)
                .set(ReadMeterDataIot::getCycleTotalUseGas,null)
                .in(ReadMeterDataIot::getId,idsList));
        if(counts>0){
            return true;
        }else{
            return false;
        }

    }

    @Override
    public ReadMeterDataIot getLatestData(String gasMeterNumber, String gasMeterCode, String customerCode, LocalDateTime dataTime) {
        return baseMapper.getLatestData(gasMeterNumber,gasMeterCode, customerCode, dataTime);
    }

    @Override
    public int updateByGasOweCode(List<Long> idsList) {
        List<GasmeterArrearsDetail> gasmeterArrearsDetails = gasmeterArrearsDetailBizApi.queryList(idsList).getData();
        int res = 0;
        if(gasmeterArrearsDetails!=null&&gasmeterArrearsDetails.size()>0){
            for(GasmeterArrearsDetail gas:gasmeterArrearsDetails){
                res += baseMapper.updateByGasOweCode(gas.getSettlementNo());
            }
        }
        return res;
    }

    @Override
    public BigDecimal stsInternetGasMeterGas(StsSearchParam stsSearchParam, String type) {

        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        BigDecimal num = this.baseMapper.stsInternetGasMeterGas(stsSearchParam, type, dataScope);
        if (num == null){
            num = BigDecimal.ZERO;
        }
        return num;
    }
}
