package com.cdqckj.gmis.biztemporary.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import com.cdqckj.gmis.biztemporary.service.ChangeMeterRecordService;
import com.cdqckj.gmis.biztemporary.vo.ChangeMeterRecordVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.seata.spring.annotation.GlobalTransactional;
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
 * 气表换表记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-11
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/changeMeterRecord")
@Api(value = "ChangeMeterRecord", tags = "气表换表记录")
@PreAuth(replace = "changeMeterRecord:")
public class ChangeMeterRecordController extends SuperController<ChangeMeterRecordService, Long, ChangeMeterRecord, ChangeMeterRecordPageDTO, ChangeMeterRecordSaveDTO, ChangeMeterRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<ChangeMeterRecord> changeMeterRecordList = list.stream().map((map) -> {
            ChangeMeterRecord changeMeterRecord = ChangeMeterRecord.builder().build();
            //TODO 请在这里完成转换
            return changeMeterRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(changeMeterRecordList));
    }

    @ApiOperation("新增换表记录")
    @PostMapping("/addWithMeterInfo")
    @GlobalTransactional
    public R<ChangeMeterRecord> addWithMeterInfo(@RequestBody @Validated ChangeMeterRecordSaveDTO saveDTO) {
        ChangeMeterRecord record = baseService.addWithMeterInfo(saveDTO);
        return R.success(record);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(name = "gasMeterCode", value = "表具编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "customerCode", value = "用户编号", dataType = "string", paramType = "query")
    })
    @ApiOperation(value = "换表前检查：是否已有记录", notes = "换表前检查：是否已有记录")
    @GetMapping("/check")
    public R<BusinessCheckResultDTO<ChangeMeterRecord>> check(@RequestParam("gasMeterCode") String gasMeterCode,
                                                              @RequestParam("customerCode") String customerCode) {

        BusinessCheckResultDTO<ChangeMeterRecord> check = baseService.check(gasMeterCode, customerCode);
        return R.success(check);
    }

    @ApiOperation(value = "保存前校验新表数据是否可用")
    @GetMapping("/validateNewMeter")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldMeterCode", required = true, value = "旧表编号", dataType = "string", paramType = "query"),
            @ApiImplicitParam(name = "newMeterCode", required = true, value = "新表编号", dataType = "string", paramType = "query")
    })
    R<Boolean> validateNewMeter(@RequestParam("oldMeterCode") String oldMeterCode,
                                @RequestParam("newMeterCode") String newMeterCode) {
        Boolean result = baseService.validateNewMeter(oldMeterCode, newMeterCode);
        return R.success(result);
    }

    /**
     * 支付成功后更新数据
     *
     * @param id
     * @return
     */
    @PostMapping(value = "/updateAfterPaid")
    @GlobalTransactional
    public R<ChangeMeterRecord> updateAfterPaid(@RequestBody Long id) {
        return R.success(baseService.updateAfterPaid(id));
    }

    @ApiOperation(value = "查询业务关注点数据", notes = "查询业务关注点数据")
    @GetMapping("/queryFocusInfo")
    public R<List<ChangeMeterRecordVO>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                                       @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode) {
        List<ChangeMeterRecordVO> changeMeterRecordVOS = baseService.queryFocusInfo(customerCode, gasMeterCode);
        return R.success(changeMeterRecordVOS);
    }

    /**
     * @param chargeNo 缴费编号
     * @return 换表记录
     */
    @ApiOperation(value = "根据缴费编号查询换表记录", notes = "根据缴费编号查询换表记录")
    @GetMapping("/getOneByChargeNo")
    public R<ChangeMeterRecord> getOneByChargeNo(@RequestParam("chargeNo") String chargeNo) {
        LbqWrapper<ChangeMeterRecord> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(ChangeMeterRecord::getChargeNo, chargeNo);
        return R.success(baseService.getOne(lbqWrapper));
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 16:49
     * @remark 换表数据统计
     */
    @ApiOperation(value = "换表数据统计")
    @PostMapping("/sts/stsChangeMeterNum")
    R<Long> stsChangeMeterNum(@RequestBody StsSearchParam stsSearchParam){
        Long num = this.baseService.stsChangeMeterNum(stsSearchParam);
        if (num == null){
            num = 0L;
        }
        return R.success(num);
    }
}
