package com.cdqckj.gmis.bizcenter.charges.controller;

import cn.hutool.core.convert.Convert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.authority.api.UserBizApi;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.LimitStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.charges.dto.LimitLoadDTO;
import com.cdqckj.gmis.bizcenter.charges.dto.ReceiptLoadDTO;
import com.cdqckj.gmis.bizcenter.charges.service.ChargeService;
import com.cdqckj.gmis.calculate.api.CalculateApi;
import com.cdqckj.gmis.calculate.vo.ConversionVO;
import com.cdqckj.gmis.charges.api.*;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.charges.entity.RechargeRecord;
import com.cdqckj.gmis.charges.enums.ReceiptStatusEnum;
import com.cdqckj.gmis.charges.util.ChargeUtils;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.invoice.ReceiptPrintRecordBizApi;
import com.cdqckj.gmis.invoice.dto.ReceiptPrintRecordSaveDTO;
import com.cdqckj.gmis.invoice.enumeration.InvoiceTypeEnum;
import com.cdqckj.gmis.operateparam.BusinessHallBizApi;
import com.cdqckj.gmis.operateparam.FunctionSwitchPlusBizApi;
import com.cdqckj.gmis.operateparam.TollItemBizApi;
import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * 账户及费用相关接口
 * </p>
 *
 * @author tp
 * @date 2020-07-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/charges")
@Api(value = "charges", tags = "账户及费用相关接口")
/*
@PreAuth(replace = "charges:")*/
public class ChargesController {
    @Autowired
    public ChargeRecordBizApi chargeRecordBizApi;


    @Autowired
    public RechargeRecordBizApi rechargeRecordBizApi;

    @Autowired
    public TollItemBizApi tollItemBizApi;

    @Autowired
    public CustomerAccountBizApi customerAccountBizApi;

    @Autowired
    public CustomerAccountSerialBizApi customerAccountSerialBizApi;

    @Autowired
    public GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;

    @Autowired
    public CustomerSceneChargeOrderBizApi customerSceneChargeBizApi;
    @Autowired
    public ChargeItemRecordBizApi chargeItemRecordBizApi;
    @Autowired
    GasmeterSettlementDetailBizApi gasmeterSettlementDetailBizApi;
    @Autowired
    ReceiptPrintRecordBizApi receiptPrintRecordBizApi;
    @Autowired
    ChargeBizApi chargeBizApi;

    @Autowired
    ChargeService chargeService;

    @Autowired
    CalculateApi calculateApi;

    @Autowired
    FunctionSwitchPlusBizApi functionSwitchPlusBizApi;


    @Autowired
    UserBizApi userBizApi;

    @Autowired
    BusinessHallBizApi businessHallBizApi;

    @Autowired
    CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @ApiOperation(value = "气量金额换算")
    @PostMapping("/calculate/convert")
    public R<BigDecimal> calculateConvert(@RequestBody ConversionVO conversionVO){
        R<BigDecimal> result= calculateApi.conversion(conversionVO);
        if(result.getIsError()){
            return R.fail(result.getMsg(),"调用换算接口异常");
        }else{
            return R.success(result.getData());
        }
    }


    @ApiOperation(value = "缴费数据加载")
    @PostMapping("/charge/load")
    public R<ChargeLoadDTO> chargeLoad(@Validated  @RequestBody ChargeLoadReqDTO param){
        return chargeService.chargeLoad(param);

    }

    @ApiOperation(value = "不同场景数据加载")
    @PostMapping("/charge/loadByScene")
    public R<ChargeLoadDTO> chargeLoadByScene(@Validated  @RequestBody ChargeSceneLoadReqDTO param){
        return chargeBizApi.chargeLoadByScene(param);

    }

    @ApiOperation(value = "发起收费")
    @PostMapping("/charge")
    @GlobalTransactional
    public R<ChargeResultDTO> saveCharge(@RequestBody @Valid ChargeDTO chargeDTO){
        return chargeService.charge(chargeDTO);
    }

//    @ApiOperation(value = "三方支付成功后回调收费")
//    @PostMapping("/chargeComplete")
//    @GlobalTransactional
//    public R<Boolean> chargeComplete(@RequestBody @Valid ChargeCompleteDTO chargeDTO){
//        return chargeService.chargeComplete(chargeDTO);
//    }


    @ApiOperation(value = "缴费记录查询")
    @PostMapping("/charge/page")
    public R<Page<ChargeRecord>> queryCharge(@RequestBody PageParams<ChargeRecordPageDTO> pageDTO ){
        return chargeRecordBizApi.page(pageDTO);
    }

    @ApiOperation(value = "分页查询缴费记录带是否申请退费标识")
    @PostMapping("/charge/pageList")
    public R<Page<ChargeRecordResDTO>> pageList(@RequestBody PageParams<ChargeListReqDTO> pageDTO ){
        return chargeService.pageList(pageDTO);
    }

    @ApiOperation(value = "根据收费单号加载项明细")
    @GetMapping("/charge/getItemDetailsByChargeNo")
    public R<List<ChargeItemRecord>> getItemDetailsByChargeNo(@RequestParam(value = "chargeNo") @NotBlank String chargeNo){
        return chargeItemRecordBizApi.query(new ChargeItemRecord().setChargeNo(chargeNo).setDataStatus(DataStatusEnum.NORMAL.getValue()));
    }

    @ApiOperation(value = "根据收费单号查询收费信息")
    @GetMapping("/charge/getChargeInfoByNo")
    public R<ChargeRecord> getChargeRecordByNo(@RequestParam(value = "chargeNo") @NotBlank String chargeNo){
        return chargeRecordBizApi.getChargeRecordByNo(chargeNo);
    }

    @ApiOperation(value = "根据表具编号分页加载项明细")
    @GetMapping("/charge/getItemDetailPageByMeterCode")
    public R<List<ChargeItemRecord>> getItemDetailPageByMeterCode(@RequestParam(value = "gasMeterCode") @NotBlank String gasMeterCode){
        return chargeItemRecordBizApi.query(new ChargeItemRecord()
                .setGasmeterCode(gasMeterCode)
                .setDataStatus(DataStatusEnum.NORMAL.getValue())
        );
    }

    @ApiOperation(value = "个人及营业厅限额加载")
    @GetMapping("/charge/limitLoad")
    public R<LimitLoadDTO> limitLoad(){
        //验证操作员限额
        LimitLoadDTO limit=new LimitLoadDTO()
                .setBizHallBalance(new BigDecimal("-1"))
                .setUserSingleLimit(new BigDecimal("0.00"))
                .setBizHallSingleLimit(new BigDecimal("0.00"))
                .setUserBalance(new BigDecimal("-1"));
        if(BaseContextHandler.getUserId()!=null) {
            User user = userBizApi.get(BaseContextHandler.getUserId()).getData();
            if (user != null) {
                if (user.getPointOfSale() != null) {
                    if(user.getBalance()!=null){
                        limit.setUserBalance(user.getBalance());
                    }
                }
                if (user.getSingleLimit() != null) {
                    if(user.getBalance()!=null){
                        limit.setUserSingleLimit(user.getSingleLimit());
                    }
                }
            }
        }
        Long orgId=BaseContextHandler.getOrgId();
        if(orgId!=null) {
            BusinessHall businessHall = businessHallBizApi.queryByOrgId(orgId);
            //有限额控制，分别校验营业厅余额和单笔限额
            if(businessHall!=null && businessHall.getRestrictStatus().intValue()== LimitStatusEnum.LIMIT.getValue()) {
                if (businessHall.getBalance() != null ) {
                   limit.setBizHallBalance(businessHall.getBalance());
                }
                if (businessHall.getSingleLimit() != null ) {
                    limit.setBizHallSingleLimit(businessHall.getSingleLimit());
                }
            }
        }
        return R.success(limit);
    }

    @ApiOperation(value = "票据编码是否人工录入 false 自动生成 true 人工录入")
    @GetMapping("/charge/receiptNoManualEntry")
    public R<Boolean> receiptNoManualEntry(){
        Integer val= GmisSysSettingUtil.getInvoiceCodeRule();
        if(val==1){
            return R.success(false);
        }else{
            return R.success(true);
        }
    }

    @ApiOperation(value = "打印票据数据加载")
    @GetMapping("/charge/printReceiptLoad")
    public R<ReceiptLoadDTO> printReceiptLoad(@RequestParam(value = "chargeNo") @NotBlank String chargeNo){
        ReceiptLoadDTO resultDTO=new ReceiptLoadDTO();
        ChargeRecord record=chargeRecordBizApi.getChargeRecordByNo(chargeNo).getData();
        if(record==null){
            //记录不存在
           throw BizException.wrap("error");
        }
        ChargeUtils.setNullFieldAsZero(record);
        resultDTO.setActualIncomeMoney(record.getActualIncomeMoney().setScale(2, RoundingMode.UP));
        resultDTO.setGiveChange(record.getGiveChange().setScale(2, RoundingMode.UP));
        resultDTO.setReceivableMoney(record.getReceivableMoney().setScale(2, RoundingMode.UP));
        resultDTO.setChargeNo(record.getChargeNo());
//        resultDTO.setPrechargeReductionMoney(record.getPrechargeDeductionMoney());
        resultDTO.setCustomerName(record.getCustomerName());
        resultDTO.setChargeUser(record.getCreateUserName());
//        resultDTO.setCustomerChargeNo("");
        resultDTO.setInvoiceNo(record.getInvoiceNo());
        resultDTO.setInvoiceCompanyName(GmisSysSettingUtil.getInvoiceCompanyName());
        resultDTO.setChargeNo(record.getChargeNo());
        resultDTO.setTotalMoney(record.getActualIncomeMoney().subtract(record.getGiveChange()).setScale(2,RoundingMode.UP));
        resultDTO.setTotalMoneyChinese(
                Convert.digitToChinese(resultDTO.getTotalMoney()));
//        CustomerGasMeterRelated related=customerGasMeterRelatedBizApi.findRelatedInfo(record.getGasMeterCode(),record.getCustomerCode()).getData();
//        resultDTO.setCustomerChargeNo(related.getCustomerChargeNo());
        resultDTO.setCustomerChargeNo("");
        resultDTO.setCreateTime(record.getCreateTime());
        List<ChargeItemRecord> items=chargeItemRecordBizApi.getChangeItemByChargeNo(chargeNo).getData();
//        resultDTO.setChargeRecord(record);
        resultDTO.setChargeItemRecords(items);
        return R.success(resultDTO);
    }

    @ApiOperation(value = "充值记录查询")
    @PostMapping(value = "/recharge/page")
    public R<Page<RechargeRecord>>queryRecharge(@RequestBody PageParams<RechargeRecordPageDTO> pageDTO){
        return rechargeRecordBizApi.page(pageDTO);
    }


    @ApiOperation(value = "欠费记录查询")
    @PostMapping("/arrears/page")
    public R<Page<GasmeterArrearsDetail>> queryArrears(
            @RequestBody PageParams<GasmeterArrearsDetailPageDTO> pageDTO){
        return gasmeterArrearsDetailBizApi.pageQueryFocusInfo(pageDTO);
    }



    @ApiOperation(value = "开收据后修改状态保存开票据记录")
    @PostMapping("/completeOpenReceipt")
    public R<Boolean> completeOpenReceipt(@RequestParam(value = "chargeNo") String chargeNo
    ){
        List<ChargeRecord> list= chargeRecordBizApi.query(new ChargeRecord().setChargeNo(chargeNo)).getData();
        if(list.size()==0) return R.success(true);
        ChargeRecord record=list.get(0);
        chargeRecordBizApi.updateReceiptOrInvoiceStatus(chargeNo, InvoiceTypeEnum.RECEIPT.getCode(), ReceiptStatusEnum.OPENED.getCode());
        receiptPrintRecordBizApi.save(new ReceiptPrintRecordSaveDTO()
        .setOperatorId(BaseContextHandler.getUserId()).setOperatorName(BaseContextHandler.getName())
                .setPrintDate(LocalDate.now())
                .setChargeNo(chargeNo)
                .setCustomerCode(record.getCustomerCode())
                .setCustomerName(record.getCustomerName())
        );
        return R.success(true);
    }

    @ApiOperation(value = "查询用户表具的收费项记录")
    @PostMapping("/pageChargeItemRecord")
    public R<Page<ChargeItemRecordResDTO>> pageChargeItemRecord(@RequestBody PageParams< ChargeItemRecordPageDTO> params){
        return chargeItemRecordBizApi.pageList(params);
    }


    @ApiOperation("场景是否收费")
    @GetMapping("/whetherSceneCharge")
    R<Boolean> whetherSceneCharge(@RequestParam("sceneCode") @NotNull String sceneCode){

        return tollItemBizApi.whetherSceneCharge(sceneCode);
    }
}
