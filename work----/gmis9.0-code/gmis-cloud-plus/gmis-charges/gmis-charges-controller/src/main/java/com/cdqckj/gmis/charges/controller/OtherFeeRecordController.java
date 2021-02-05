package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.charges.entity.OtherFeeRecord;
import com.cdqckj.gmis.charges.dto.OtherFeeRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.OtherFeeRecordUpdateDTO;
import com.cdqckj.gmis.charges.dto.OtherFeeRecordPageDTO;
import com.cdqckj.gmis.charges.service.OtherFeeRecordService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 附加费记录
 * </p>
 *
 * @author tp
 * @date 2020-12-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/otherFeeRecord")
@Api(value = "OtherFeeRecord", tags = "附加费记录")
@PreAuth(replace = "otherFeeRecord:")
public class OtherFeeRecordController extends SuperController<OtherFeeRecordService, Long, OtherFeeRecord, OtherFeeRecordPageDTO, OtherFeeRecordSaveDTO, OtherFeeRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<OtherFeeRecord> otherFeeRecordList = list.stream().map((map) -> {
            OtherFeeRecord otherFeeRecord = OtherFeeRecord.builder().build();
            //TODO 请在这里完成转换
            return otherFeeRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(otherFeeRecordList));
    }
}
