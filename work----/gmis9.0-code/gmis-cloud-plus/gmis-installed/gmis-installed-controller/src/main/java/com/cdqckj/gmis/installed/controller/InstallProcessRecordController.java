package com.cdqckj.gmis.installed.controller;

import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.installed.entity.InstallProcessRecord;
import com.cdqckj.gmis.installed.dto.InstallProcessRecordSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallProcessRecordUpdateDTO;
import com.cdqckj.gmis.installed.dto.InstallProcessRecordPageDTO;
import com.cdqckj.gmis.installed.service.InstallProcessRecordService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 报装流程操作记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/installProcessRecord")
@Api(value = "InstallProcessRecord", tags = "报装流程操作记录")
public class InstallProcessRecordController extends SuperController<InstallProcessRecordService, Long, InstallProcessRecord, InstallProcessRecordPageDTO, InstallProcessRecordSaveDTO, InstallProcessRecordUpdateDTO> {

//    /**
//     * Excel导入后的操作
//     *
//     * @param list
//     */
//    @Override
//    public R<Boolean> handlerImport(List<Map<String, Object>> list){
//        List<InstallProcessRecord> installProcessRecordList = list.stream().map((map) -> {
//            InstallProcessRecord installProcessRecord = InstallProcessRecord.builder().build();
//            //TODO 请在这里完成转换
//            return installProcessRecord;
//        }).collect(Collectors.toList());
//
//        return R.success(baseService.saveBatch(installProcessRecordList));
//    }

    @Override
    public R<List<InstallProcessRecord>> query(@RequestBody InstallProcessRecord data) {
        QueryWrap<InstallProcessRecord> wrapper = Wraps.q(data);
        wrapper.orderByDesc("create_time");
        return success(getBaseService().list(wrapper));
    }
}
