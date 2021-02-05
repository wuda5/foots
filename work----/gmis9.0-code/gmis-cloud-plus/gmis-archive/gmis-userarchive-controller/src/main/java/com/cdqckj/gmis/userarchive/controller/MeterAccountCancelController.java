package com.cdqckj.gmis.userarchive.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.userarchive.dto.MeterAccountCancelPageDTO;
import com.cdqckj.gmis.userarchive.dto.MeterAccountCancelSaveDTO;
import com.cdqckj.gmis.userarchive.dto.MeterAccountCancelUpdateDTO;
import com.cdqckj.gmis.userarchive.entity.MeterAccountCancel;
import com.cdqckj.gmis.userarchive.service.MeterAccountCancelService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 销户记录
 * </p>
 *
 * @author gmis
 * @date 2020-12-14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/meterAccountCancel")
@Api(value = "MeterAccountCancel", tags = "销户记录")
@PreAuth(replace = "meterAccountCancel:")
public class MeterAccountCancelController extends SuperController<MeterAccountCancelService, Long, MeterAccountCancel, MeterAccountCancelPageDTO, MeterAccountCancelSaveDTO, MeterAccountCancelUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<MeterAccountCancel> meterAccountCancelList = list.stream().map((map) -> {
            MeterAccountCancel meterAccountCancel = MeterAccountCancel.builder().build();
            //TODO 请在这里完成转换
            return meterAccountCancel;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(meterAccountCancelList));
    }


    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 15:44
     * @remark 销户统计
     */
    @PostMapping("/sts/stsCancelCustomerType")
    R<List<StsInfoBaseVo<String,Long>>> stsCancelCustomerType(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<String,Long>> dataList = this.baseService.stsCancelCustomerType(stsSearchParam);
        return R.success(dataList);
    }
}
