package com.cdqckj.gmis.securityed.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordUpdateDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckRecordPageDTO;
import com.cdqckj.gmis.securityed.service.SecurityCheckRecordService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;


/**
 * <p>
 * 前端控制器
 * 安检计划记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/securityCheckRecord")
@Api(value = "SecurityCheckRecord", tags = "安检计划记录")
@PreAuth(replace = "securityCheckRecord:")
public class SecurityCheckRecordController extends SuperController<SecurityCheckRecordService, Long, SecurityCheckRecord, SecurityCheckRecordPageDTO, SecurityCheckRecordSaveDTO, SecurityCheckRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<SecurityCheckRecord> securityCheckRecordList = list.stream().map((map) -> {
            SecurityCheckRecord securityCheckRecord = SecurityCheckRecord.builder().build();
            //TODO 请在这里完成转换
            return securityCheckRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(securityCheckRecordList));
    }

    @ApiOperation(value = "新增安检计划信息")
    @PostMapping("/saveSecurityCheckRecord")
    @CodeNotLost
    public R<Boolean> saveSecurityCheckRecord(@RequestBody @Validated List<SecurityCheckRecord> securityCheckRecord) {
        return R.success(baseService.saveSecurityCheckRecord(securityCheckRecord));
    }

    @ApiOperation(value = "审核")
    @PostMapping("/approvaled")
    public R<Integer> approvaledSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params) {
        return R.success(baseService.approvaledSecurityCheckRecord(params));
    }

    @ApiOperation(value = "驳回")
    @PostMapping(value = "/reject")
    R<Integer> rejectSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params) {
        return R.success(baseService.rejectSecurityCheckRecord(params));
    }

    @ApiOperation(value = "结束")
    @PostMapping(value = "/end")
    R<Integer> endSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params) {
        return R.success(baseService.endSecurityCheckRecord(params));
    }

    @ApiOperation(value = "派工")
    @PostMapping(value = "/leaflet")
    R<Integer> leafletSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params) {
        return R.success(baseService.leafletSecurityCheckRecord(params));
    }

    @ApiOperation(value = "接单")
    @PostMapping(value = "/giveOrder")
    R<Integer> giveOrder(@RequestBody @Validated SecurityCheckRecord params) {
        return R.success(baseService.giveOrder(params));
    }

    @ApiOperation(value = "完成工單")
    @PostMapping(value = "/completeOrder")
    R<Integer> completeOrder(@RequestBody @Validated SecurityCheckRecord params) {
        return R.success(baseService.completeOrder(params));
    }

    @Override
    public void query(PageParams<SecurityCheckRecordPageDTO> params, IPage<SecurityCheckRecord> page, Long defSize) {
        SecurityCheckRecordPageDTO model = params.getModel();
        if (CollectionUtils.isEmpty(model.getDataStatusList())) {
            super.query(params, page, null);
        }

//             QueryWrap<SecurityCheckRecord> wrap = Wraps.q();
//             QueryWrap<SecurityCheckRecord> wrapper = Wraps.q(model);
        SecurityCheckRecord model2 = BeanUtil.toBean(params.getModel(), SecurityCheckRecord.class);
        QueryWrap<SecurityCheckRecord> wp = Wraps.q(model2);
        handlerWrapper(wp, params);

        wp.lambda().in(SecurityCheckRecord::getDataStatus, model.getDataStatusList());

        baseService.page(page, wp);

    }
}
