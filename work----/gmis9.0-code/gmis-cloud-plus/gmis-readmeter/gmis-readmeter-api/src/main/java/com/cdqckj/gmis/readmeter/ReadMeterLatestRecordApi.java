package com.cdqckj.gmis.readmeter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.readmeter.dto.ReadMeterLatestRecordPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterLatestRecordSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterLatestRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "${gmis.feign.readmeter-server:gmis-readmeter-server}",fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/readMeterLatestRecord", qualifier = "ReadMeterLatestRecordApi")
public interface ReadMeterLatestRecordApi {
    /*新增
     * */
    @PostMapping
    R<ReadMeterLatestRecord> save(@RequestBody  ReadMeterLatestRecordSaveDTO saveDTO);

    /*批量新
     * */
    @PostMapping(value = "/saveList")
    R<ReadMeterLatestRecord> saveList(@RequestBody List<ReadMeterLatestRecordSaveDTO> list);

    /*修改工单
     * */
    @PutMapping
    R<ReadMeterLatestRecord> update(@RequestBody @Validated(SuperEntity.Update.class) ReadMeterLatestRecordUpdateDTO updateDTO);


    @PostMapping(value = "/page")
    R<Page<ReadMeterLatestRecord>> page(@RequestBody @Validated PageParams<ReadMeterLatestRecordPageDTO> params);

    @PostMapping(value = "/getById")
    R<ReadMeterLatestRecord> getById(@RequestParam("id") Long id);


    @PostMapping(value = "/queryList")
    R<List<ReadMeterLatestRecord>> queryList(@RequestParam("ids[]") List<Long> ids);


    @PostMapping("/query")
    R<List<ReadMeterLatestRecord>> query(@RequestBody ReadMeterLatestRecord query);

    @ApiOperation(value = "条件查询一条数据", notes = "条件查询一条数据")
    @PostMapping("/queryOne")
    @SysLog("条件查询一条数据")
    R<ReadMeterLatestRecord> queryOne(@RequestBody ReadMeterLatestRecord data) ;

    /**
     * 根据气表编号集合 查询对应所有的 表抄表最新数据集合
     *
     * @param gasCodes
     */
    @PostMapping(value = "/queryListByGasCodes")
    R<List<ReadMeterLatestRecord>> queryListByGasCodes(@RequestParam("gasCodes[]") List<String> gasCodes);
}
