package com.cdqckj.gmis.biztemporary.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.cdqckj.gmis.biztemporary.entity.OpenAccountRecord;
import com.cdqckj.gmis.biztemporary.dto.OpenAccountRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.OpenAccountRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.dto.OpenAccountRecordPageDTO;
import com.cdqckj.gmis.biztemporary.service.OpenAccountRecordService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.charges.entity.SummaryBill;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cdqckj.gmis.security.annotation.PreAuth;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/openAccountRecord")
@Api(value = "OpenAccountRecord", tags = "开户记录")
//@PreAuth(replace = "openAccountRecord:")
public class OpenAccountRecordController extends SuperController<OpenAccountRecordService, Long, OpenAccountRecord, OpenAccountRecordPageDTO, OpenAccountRecordSaveDTO, OpenAccountRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<OpenAccountRecord> openAccountRecordList = list.stream().map((map) -> {
            OpenAccountRecord openAccountRecord = OpenAccountRecord.builder().build();
            //TODO 请在这里完成转换
            return openAccountRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(openAccountRecordList));
    }
    /**
     * 通过关联id修改
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "通过关联id修改")
    @PutMapping("/updateByRelateId")
    public R<OpenAccountRecord> updateByRelateId(@RequestBody OpenAccountRecordUpdateDTO updateDTO){
        UpdateWrapper<OpenAccountRecord> updateWrapper =new UpdateWrapper<>();
        updateWrapper.lambda().eq(OpenAccountRecord::getRelateId,updateDTO.getRelateId());
        OpenAccountRecord openAccountRecord = OpenAccountRecord
                .builder()
                .status(updateDTO.getStatus())
                .build();
        baseService.update(openAccountRecord, updateWrapper);
        return R.success(openAccountRecord);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 15:44
     * @remark 开户的数量
     */
    @ApiOperation(value = "开户的数量")
    @PostMapping("/sts/stsOpenAccountRecord")
    R<Long> stsOpenAccountRecord(@RequestBody StsSearchParam stsSearchParam){
        Long num = this.baseService.stsOpenAccountRecord(stsSearchParam);
        if (num == null){
            num = 0L;
        }
        return R.success(num);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 15:44
     * @remark 开户的统计
     */
    @PostMapping("/sts/stsOpenCustomerType")
    R<List<StsInfoBaseVo<String,Long>>> stsOpenCustomerType(@RequestBody StsSearchParam stsSearchParam){
        List<StsInfoBaseVo<String,Long>> stsOpen = this.baseService.stsOpenCustomerType(stsSearchParam);
        return R.success(stsOpen);
    }
}
