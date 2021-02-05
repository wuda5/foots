package com.cdqckj.gmis.readmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.readmeter.dao.ReadMeterDataMapper;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.service.ReadMeterDataService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.readmeter.vo.GasMeterReadStsVo;
import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
import com.cdqckj.gmis.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
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
public class ReadMeterDataServiceImpl extends SuperServiceImpl<ReadMeterDataMapper, ReadMeterData> implements ReadMeterDataService {
    @Autowired
    public ReadMeterDataMapper readMeterDataMapper;

    /**
     * 根据气表编码，抄表年月，查询抄表数据（默认查没有审核或者未通过审核的）
     * @param meterCodelist
     * @param yearlist
     * @param monthlist
     * @param process
     * @return
     */
    @Override
    public R<List<ReadMeterData>> getMeterDataByCode(List<String> meterCodelist, List<Integer> yearlist, List<Integer> monthlist,ProcessEnum process) {
        LambdaQueryWrapper<ReadMeterData> lambda = new LambdaQueryWrapper<>();
        lambda.in(ReadMeterData::getGasMeterCode, meterCodelist);
        lambda.in(ReadMeterData::getReadMeterYear, yearlist);
        lambda.in(ReadMeterData::getReadMeterMonth, monthlist);
        lambda.eq(ReadMeterData::getDataStatus,-1);
        if(null==process){
            lambda.or().eq(ReadMeterData::getProcessStatus, null).ne(ReadMeterData::getProcessStatus, ProcessEnum.APPROVED);
        }else{
            lambda.eq(ReadMeterData::getProcessStatus, process);
        }
        List<ReadMeterData> ReadMeterMonthGasList = baseMapper.getMeterDataByCode(lambda);
        return R.success(ReadMeterMonthGasList);
    }

    @Override
    public R<List<ReadMeterData>> getHistory(List<String> meterCodelist, LocalDate time) {
        LambdaQueryWrapper<ReadMeterData> lambda = new LambdaQueryWrapper<>();
        lambda.in(ReadMeterData::getGasMeterCode, meterCodelist);
        lambda.le(ReadMeterData::getReadTime, time);
        lambda.eq(ReadMeterData::getDataStatus,0);
        lambda.orderByDesc(ReadMeterData::getReadTime);
        lambda.last("LIMIT 3");
        List<ReadMeterData> dataList = baseMapper.getMeterDataByCode(lambda);
        return R.success(dataList);
    }

    @Override
    public R<Boolean> deleteData(List<ReadMeterData> list) {
        List<Long> idList = list.stream().map(ReadMeterData::getId).collect(Collectors.toList());
        baseMapper.deleteBatchIds(idList);
        return R.success();
    }

//    public LocalDate getDate(Integer year, Integer month) throws Exception {
//        DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
//        String m = month<10?month+"":"0"+month;
//        Date date = dateFormat1.parse(year+"-"+m+"-01");
//        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//    }

    @Override
    public IPage<ReadMeterData> pageReadMeterData(PageParams<ReadMeterDataPageDTO> params, List<String> codeList) throws Exception {
        ReadMeterDataPageDTO dto = params.getModel();
        List<Long> orgIds = UserOrgIdUtil.getUserDataScopeList();
        Integer year = dto.getReadMeterYear();
        Integer month = dto.getReadMeterMonth();
//        dto.setReadTime(getDate(year, month));
        dto.setReadTime(DateUtils.getDate8(year,month));
        Page<ReadMeterData> page = new Page<>(params.getCurrent(),params.getSize());
        return baseMapper.pageReadMeterData(page, dto,codeList.isEmpty()?null:codeList, orgIds.isEmpty()?null:orgIds);
    }

    @Override
    public R<List<ReadMeterData>> selectGasCode(List<String> codeList) {
        return R.success(baseMapper.selectGasCode(codeList));
    }

    @Override
    public R<Boolean> deleteByGasCode(List<String> list) {
        LambdaQueryWrapper<ReadMeterData> lambda = new LambdaQueryWrapper<>();
        lambda.in(ReadMeterData::getGasMeterCode, list);
        lambda.ne(ReadMeterData::getDataStatus, 0);
        baseMapper.delete(lambda);
        return R.success();
    }

    @Override
    public IPage<MeterPlanNowStsVo> stsReadMeter(StsSearchParam stsSearchParam) {
        Page<ReadMeterData> page = new Page<>(stsSearchParam.getPageNo(),stsSearchParam.getPageSize());
        return baseMapper.stsReadMeter(page,stsSearchParam);
    }

    @Override
    public GasMeterReadStsVo generalGasMeterReadSts(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        GasMeterReadStsVo vo = this.baseMapper.generalGasMeterReadSts(stsSearchParam, dataScope);
        if (vo == null){
            vo = new GasMeterReadStsVo();
        }
        vo.setLoseCount(vo.getTotalCount() - vo.getReadCount());
        return vo;
    }

    @Override
    public ReadMeterData getPreviousData(String gasMeterCode, String customerCode, LocalDate dataTime) {
        return baseMapper.queryPreviousData(gasMeterCode,customerCode,dataTime);
    }

    @Override
    public BigDecimal stsGeneralGasMeter(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsGeneralGasMeter(stsSearchParam, dataScope);
    }

    @Override
    public List<MeterPlanNowStsVo> stsReadMeterByMonth(Page resultPage, List<String> readMeterDate, StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        for (int i = 0; i < readMeterDate.size(); i++){
            String s = readMeterDate.get(i);
            stringBuilder.append("'").append(s).append("'");
            if (i != readMeterDate.size() -1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(")");
        List<MeterPlanNowStsVo> stsVos = this.baseMapper.stsReadMeterByMonth(resultPage, stringBuilder.toString(), stsSearchParam, dataScope);
        return stsVos;
    }
}
