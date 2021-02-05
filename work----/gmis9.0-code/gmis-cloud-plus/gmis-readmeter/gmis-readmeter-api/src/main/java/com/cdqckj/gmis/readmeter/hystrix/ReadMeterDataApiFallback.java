package com.cdqckj.gmis.readmeter.hystrix;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.dto.*;
import com.cdqckj.gmis.readmeter.dtoex.OneReadDataInputDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterBook;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.statistics.vo.MeterPlanNowStsVo;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import feign.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @author 65427
 */
@Component
public abstract class ReadMeterDataApiFallback implements ReadMeterDataApi {

    @Override
    public R<ReadMeterData> saveList(List<ReadMeterDataSaveDTO> readMeterDataList) {
        return R.timeout();
    }

    @Override
    public R<Page<ReadMeterData>> page(PageParams<ReadMeterDataPageDTO> params) {
        return null;
    }

    @Override
    public R<Page<ReadMeterData>> pageExistData(PageParams<ReadMeterDataPageDTO> params) {
        return null;
    }

    @Override
    public R<List<ReadMeterData>> queryList(List<Long> ids) {
        return null;
    }

    @Override
    public R<ReadMeterData> save(ReadMeterDataSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterData>> query(ReadMeterData data) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterData> queryOne(ReadMeterData data) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterData> getById(Long id) {
        return null;
    }

    @Override
    public R<ReadMeterData> input(ReadMeterData updateDTO) {
        return null;
    }

    @Override
    public R<Boolean> inputList(List<ReadMeterData> updateDTO) {
        return null;
    }

    @Override
    public R<List<ReadMeterData>> getDataByBookId(List<Long> id) {
        return null;
    }

    @Override
    public R<List<ReadMeterData>> getDataByCustomerCode(List<String> list) {
        return null;
    }

    @Override
    public R<Boolean> deleteByGasCode(List<String> list) {
        return null;
    }

    @Override
    public Response exportCombobox(PageParams<ReadMeterDataPageDTO> params) {
        return null;
    }

    @Override
    public Response exportReadMeterList(List<ReadMeterPlanScope> list) {
        return null;
    }

    @Override
    public Response exportReadMeterData(ReadMeterData readMeterData) {
        return null;
    }

    @Override
    public R<Map<Integer,ReadMeterData>> importReadMeterData(MultipartFile simpleFile) {
        return R.timeout();
    }

    @Override
    public R<Map<String,Object>> importExcelByTime(MultipartFile simpleFile, String readTime) {
        return null;
    }

    @Override
    public R<List<ReadMeterData>> getMeterDataByCode(List<ReadMeterData> list, ProcessEnum process) {
        return null;
    }

    @Override
    public Response export(PageParams<ReadMeterDataPageDTO> params) {
        return null;
    }

    @Override
    public R<ReadMeterData> update(ReadMeterDataUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBatch(List<ReadMeterDataUpdateDTO> list) {
        return R.timeout();
    }

    @Override
    public R<Map<String, BigDecimal>> getHistory(List<ReadMeterDataUpdateDTO> meterCodeList) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterData>> examine(List<ReadMeterData> list) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterData>> settlement(List<Long> list) {
        return null;
    }

    @Override
    public R<Boolean> withdraw(List<Long> ids) {
        return null;
    }

    @Override
    public R<ReadMeterData> inputData(ReadMeterDataUpdateDTO readMeterDataUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> ids) {
        return null;
    }

    @Override
    public R<Boolean> delete(List<Long> ids) {
        return null;
    }

    @Override
    public R<Page<ReadMeterData>> pageReadMeterData(PageParams<ReadMeterDataPageDTO> params) {
        return null;
    }

    @Override
    public Response exportReadMeterByBook(Long book) {
        return null;
    }

    /**
     * 根据表具编号查询还未缴费的抄表数据
     *
     * @param gasMeterCode 表具编号
     * @return 未缴费的抄表数据
     */
    @Override
    public R<List<ReadMeterData>> queryUnChargedData(String gasMeterCode) {
        return R.timeout();
    }

    /**
     * 查询抄表次数
     *
     * @param gasMeterCode 表具编号
     * @return 表具抄表次数
     */
    @Override
    public R<Integer> countReadMeterData(String gasMeterCode) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterData> checkInitOneMeterData(OneReadDataInputDTO dto) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateDataByCustomer(ReadMeterDataUpdateDTO updateDTO) {
        return null;
    }

    @Override
    public R<Boolean> updateDataByGasType(ReadMeterDataUpdateDTO updateDTO) {
        return null;
    }

    @Override
    public R<Boolean> updateDataRefund(List<Long> list) {
        return null;
    }

    @Override
    public R<ReadMeterData> costSettlement(ReadMeterData data) {
        return null;
    }

    @Override
    public R<Boolean> isFinished(CustomerGasDto gasMeter) {
        return null;
    }

    @Override
    public R<Page<MeterPlanNowStsVo>> stsReadMeter(StsSearchParam stsSearchParam) {
        return null;
    }

}
