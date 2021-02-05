package com.cdqckj.gmis.readmeter.controller;

import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.readmeter.dto.ReadMeterLatestRecordSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterLatestRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterLatestRecordPageDTO;
import com.cdqckj.gmis.readmeter.service.ReadMeterLatestRecordService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 每个表具最新抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-12-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readMeterLatestRecord")
@Api(value = "ReadMeterLatestRecord", tags = "每个表具最新抄表数据")
@PreAuth(replace = "readMeterLatestRecord:")
public class ReadMeterLatestRecordController extends SuperController<ReadMeterLatestRecordService, Long, ReadMeterLatestRecord, ReadMeterLatestRecordPageDTO, ReadMeterLatestRecordSaveDTO, ReadMeterLatestRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<ReadMeterLatestRecord> readMeterLatestRecordList = list.stream().map((map) -> {
            ReadMeterLatestRecord readMeterLatestRecord = ReadMeterLatestRecord.builder().build();
            //TODO 请在这里完成转换
            return readMeterLatestRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(readMeterLatestRecordList));
    }

    /**
     * 根据气表编号集合 查询对应所有的 表抄表最新数据集合
     *
     * @param gasCodes
     */
    @PostMapping(value = "/queryListByGasCodes")
    R<List<ReadMeterLatestRecord>> queryListByGasCodes(@RequestParam("gasCodes[]") List<String> gasCodes){
        LbqWrapper<ReadMeterLatestRecord> wp = Wraps.lbQ();
        wp.in(ReadMeterLatestRecord::getGasMeterCode,gasCodes);

        List<ReadMeterLatestRecord> list = baseService.list(wp);
        return success(list);
    }
}
