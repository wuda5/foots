package com.cdqckj.gmis.biztemporary.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.RemoveMeterRecord;
import com.cdqckj.gmis.biztemporary.service.RemoveMeterRecordService;
import com.cdqckj.gmis.biztemporary.vo.RemoveMeterRecordVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 气表拆表记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-11
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/removeMeterRecord")
@Api(value = "RemoveMeterRecord", tags = "气表拆表记录")
@PreAuth(replace = "removeMeterRecord:")
public class RemoveMeterRecordController extends SuperController<RemoveMeterRecordService, Long, RemoveMeterRecord, RemoveMeterRecordPageDTO, RemoveMeterRecordSaveDTO, RemoveMeterRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<RemoveMeterRecord> removeMeterRecordList = list.stream().map((map) -> {
            RemoveMeterRecord removeMeterRecord = RemoveMeterRecord.builder().build();
            //TODO 请在这里完成转换
            return removeMeterRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(removeMeterRecordList));
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "gasMeterCode", value = "表具编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "customerCode", value = "用户编号", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "拆表前检查：是否已有记录", notes = "拆表前检查：是否已有记录")
    @GetMapping("/check")
    public R<BusinessCheckResultDTO<RemoveMeterRecord>> check(@RequestParam("gasMeterCode") String gasMeterCode,
                                                              @RequestParam("customerCode") String customerCode) {

        BusinessCheckResultDTO<RemoveMeterRecord> check = baseService.check(gasMeterCode, customerCode);
        return R.success(check);
    }

    @ApiOperation(value = "查询业务关注点数据", notes = "查询业务关注点数据")
    @GetMapping("/queryFocusInfo")
    public R<List<RemoveMeterRecordVO>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                                       @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode) {
        List<RemoveMeterRecordVO> removeMeterRecordVOS = baseService.queryFocusInfo(customerCode, gasMeterCode);
        return R.success(removeMeterRecordVOS);
    }

    /**
     * @param chargeNo 缴费编号
     * @return 拆表记录
     */
    @ApiOperation(value = "根据缴费编号查询拆表记录", notes = "根据缴费编号查询拆表记录")
    @GetMapping("/getOneByChargeNo")
    public R<RemoveMeterRecord> getOneByChargeNo(@RequestParam("chargeNo") String chargeNo) {
        LbqWrapper<RemoveMeterRecord> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(RemoveMeterRecord::getChargeNo, chargeNo);
        return R.success(baseService.getOne(lbqWrapper));
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 16:36
     * @remark 拆表的统计
     */
    @ApiOperation(value = "拆表的统计")
    @PostMapping("/sts/stsRemoveMeterNum")
    R<Long> stsRemoveMeterNum(@RequestBody StsSearchParam stsSearchParam){
        Long num = this.baseService.stsRemoveMeterNum(stsSearchParam);
        return R.success(num);
    }

}
