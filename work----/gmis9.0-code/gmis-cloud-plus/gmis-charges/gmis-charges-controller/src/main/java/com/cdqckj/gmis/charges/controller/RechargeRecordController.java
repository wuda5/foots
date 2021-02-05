package com.cdqckj.gmis.charges.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.RechargeRecordPageDTO;
import com.cdqckj.gmis.charges.dto.RechargeRecordSaveDTO;
import com.cdqckj.gmis.charges.dto.RechargeRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import com.cdqckj.gmis.charges.enums.MoneyFlowStatusEnum;
import com.cdqckj.gmis.charges.service.ChargeService;
import com.cdqckj.gmis.charges.service.RechargeRecordService;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 充值记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/rechargeRecord")
@Api(value = "RechargeRecord", tags = "充值记录")
//@PreAuth(replace = "rechargeRecord:")
public class RechargeRecordController extends SuperController<RechargeRecordService, Long, RechargeRecord, RechargeRecordPageDTO, RechargeRecordSaveDTO, RechargeRecordUpdateDTO> {

    @Autowired
    ChargeService chargeService;
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<RechargeRecord> rechargeRecordList = list.stream().map((map) -> {
            RechargeRecord rechargeRecord = RechargeRecord.builder().build();
            //TODO 请在这里完成转换
            return rechargeRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(rechargeRecordList));
    }
    /**
     * 根据客户编号气表编号查询充值记录
     */
    @ApiOperation("根据客户编号气表编号查询充值记录")
    @GetMapping("/getListByMeterAndCustomerCode")
    public R<List<RechargeRecord>> getListByMeterAndCustomerCode(@RequestParam(value = "gasMeterCode") String gasMeterCode,
                                                                 @RequestParam(value = "customerCode") String customerCode){
            return R.success(baseService.getListByMeterAndCustomerCode(gasMeterCode,customerCode));
    }
    /**
     * 根据缴费编号查询充值记录
     */
    @ApiOperation("根据缴费编号查询充值记录")
    @GetMapping("/getByChargeNo")
    public  R<RechargeRecord> getByChargeNo(@RequestParam(value = "chargeNo")String chargeNo){
        return R.success(baseService.getByChargeNo(chargeNo));
    }
    /**
     * 根据客户编号气表编号分页查询充值记录
     */
    @ApiOperation("根据客户编号气表编号分页查询充值记录")
    @PostMapping("/getPageByMeterAndCustomerCode")
    public R<IPage> getPageByMeterAndCustomerCode(@RequestBody PageParams<RechargeRecordPageDTO> params){
        return R.success(baseService.getPageByMeterAndCustomerCode(params.getModel().getGasmeterCode(),
                params.getModel().getCustomerCode(),
                params.getPage()));
    }
    /**
     * 处理未完成的充值记录
     * @param gasMeterCode
     * @return
     */
    @ApiOperation(value = "处理未完成的充值记录")
    @PostMapping(value = "/dealUnCompleteRecord")
    @GlobalTransactional
    public R<Boolean> dealUnCompleteRecord(@RequestParam(value = "gasMeterCode") String gasMeterCode){
        return chargeService.dealUnCompleteRecord(gasMeterCode);
    }

    /**
     * 物联网表充值指撤销回调处理充值记录
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "iotRechargeOrderCancelCallBack")
    @ApiOperation("物联网表充值指撤销回调")
    public R<Boolean> iotRechargeOrderCancelCallBack(@RequestParam(value = "serialNo") String serialNo
    ){
        if(StringUtils.isBlank(serialNo) ){
            return R.success(false);
        }
        RechargeRecord rechargeRecord= baseService.getOne(new LbqWrapper<RechargeRecord>()
                .eq(RechargeRecord::getMoneyFlowSerialNo,serialNo)
                .in(RechargeRecord::getMoneyFlowStatus,
                        MoneyFlowStatusEnum.deal_failure.getCode(),
                        MoneyFlowStatusEnum.waite_to_meter.getCode(),
                        MoneyFlowStatusEnum.meter_failure.getCode()
                )
                .eq(RechargeRecord::getDataStatus,DataStatusEnum.NORMAL.getValue()));
        if(rechargeRecord==null){
            return R.success(true);
        }
        RechargeRecord update=new RechargeRecord();
        update.setId(rechargeRecord.getId());
        rechargeRecord.setMoneyFlowSerialNo(serialNo);
        update.setMoneyFlowStatus(MoneyFlowStatusEnum.waite_deal.getCode());
        baseService.updateById(update);
        return R.success(true);
    }

    /**
     * 物联网表充值写指令成功回调处理充值记录
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "iotRechargeOrderCallBack")
    @ApiOperation("物联网表充值写指令成功回调")
    public R<Boolean> iotRechargeOrderCallBack(@RequestParam(value = "chargeNo") String chargeNo,
                                               @RequestParam(value = "serialNo") String serialNo
                                               ){
        if(StringUtils.isBlank(serialNo) || StringUtils.isBlank(chargeNo)){
            return R.success(false);
        }
        RechargeRecord rechargeRecord= baseService.getOne(new LbqWrapper<RechargeRecord>()
                .eq(RechargeRecord::getChargeNo,chargeNo)
                .eq(RechargeRecord::getMoneyFlowStatus,MoneyFlowStatusEnum.waite_deal.getCode())
                .eq(RechargeRecord::getDataStatus,DataStatusEnum.NORMAL.getValue()));
        if(rechargeRecord==null){
            return R.success(true);
        }
        RechargeRecord update=new RechargeRecord();
        update.setId(rechargeRecord.getId());
        update.setMoneyFlowSerialNo(serialNo);
        update.setMoneyFlowStatus(MoneyFlowStatusEnum.waite_to_meter.getCode());
        baseService.updateById(update);
        return R.success(true);
    }

    /**
     * 物联网表充值下发指令重试成功回调处理充值记录
     * @return
     */
    @ApiOperation("物联网表充值下发指令重试成功回调")
    @RequestMapping(method = RequestMethod.POST,value = "iotRechargeOrderRetryCallBack")
    public R<Boolean> iotRechargeOrderRetryCallBack(@RequestParam(value = "serialNo") String serialNo
    ){
        if(StringUtils.isBlank(serialNo) || StringUtils.isBlank(serialNo)){
            return R.success(false);
        }
        RechargeRecord rechargeRecord= baseService.getOne(new LbqWrapper<RechargeRecord>()
                .eq(RechargeRecord::getMoneyFlowSerialNo,serialNo)
                .eq(RechargeRecord::getMoneyFlowStatus,
                        MoneyFlowStatusEnum.meter_failure.getCode()
                )
                .eq(RechargeRecord::getDataStatus,DataStatusEnum.NORMAL.getValue()));
        if(rechargeRecord==null){
            return R.success(true);
        }
        RechargeRecord update=new RechargeRecord();
        update.setId(rechargeRecord.getId());
        update.setMoneyFlowStatus(MoneyFlowStatusEnum.waite_to_meter.getCode());
        baseService.updateById(update);
        return R.success(true);
    }


    /**
     * 物联网表充值上表回调处理充值记录
     * @return
     */
    @ApiOperation("物联网表充值上表回调")
    @RequestMapping(method = RequestMethod.POST,value = "iotRechargeMeterCallBack")
    public R<Boolean> iotRechargeMeterCallBack(@RequestParam(value = "serialNo") String serialNo,
                                               @RequestParam(value = "dealStatus") Boolean dealStatus
    ){
        if(StringUtils.isBlank(serialNo)){
            return R.success(false);
        }
        if(dealStatus==null) {
            dealStatus=false;
        }
        RechargeRecord rechargeRecord= baseService.getOne(new LbqWrapper<RechargeRecord>()
                .eq(RechargeRecord::getMoneyFlowSerialNo,serialNo)
                .in(RechargeRecord::getMoneyFlowStatus,
                        MoneyFlowStatusEnum.waite_to_meter.getCode(),
                        MoneyFlowStatusEnum.meter_failure.getCode()
                )
                .eq(RechargeRecord::getDataStatus,DataStatusEnum.NORMAL.getValue()));
        if(rechargeRecord==null){
            return R.success(true);
        }
        RechargeRecord update=new RechargeRecord();
        update.setId(rechargeRecord.getId());
        if(dealStatus){
            update.setMoneyFlowStatus(MoneyFlowStatusEnum.success.getCode());
        }else{
            update.setMoneyFlowStatus(MoneyFlowStatusEnum.meter_failure.getCode());
        }
        baseService.updateById(update);
        return R.success(true);
    }

    /**
     * 查询表具是否有未完成的充值记录
     *
     * @param gasMeterCode 表具编号
     * @return 未完成的充值记录列表
     */
    @GetMapping("/queryUnfinishedRecord")
    public R<List<RechargeRecord>> queryUnfinishedRecord(@RequestParam("gasMeterCode") String gasMeterCode) {
        LbqWrapper<RechargeRecord> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(RechargeRecord::getGasmeterCode, gasMeterCode)
                .ne(RechargeRecord::getMoneyFlowStatus, MoneyFlowStatusEnum.success.getCode())
                .eq(RechargeRecord::getDataStatus, DataStatusEnum.NORMAL.getValue());
        return R.success(baseService.list(lbqWrapper));

    }

    /**
     * 根据气表编码和状态查询充值记录
     */
    @GetMapping("/getByGasMeterCodeAndStatus")
    public R<RechargeRecord> getByGasMeterCodeAndStatus(@RequestParam(value = "gasMeterCode")String gasMeterCode,
                                                        @RequestParam(value = "customerCode")String customerCode,
                                                 @RequestParam(value = "moneyFlowStatus")String moneyFlowStatus
    ){
        LbqWrapper<RechargeRecord> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(RechargeRecord::getGasmeterCode, gasMeterCode)
                .eq(RechargeRecord::getCustomerCode, customerCode)
                .eq(RechargeRecord::getMoneyFlowStatus, moneyFlowStatus)
                .eq(RechargeRecord::getDataStatus, DataStatusEnum.NORMAL.getValue());
        return R.success(baseService.getOne(lbqWrapper));
    }

}
