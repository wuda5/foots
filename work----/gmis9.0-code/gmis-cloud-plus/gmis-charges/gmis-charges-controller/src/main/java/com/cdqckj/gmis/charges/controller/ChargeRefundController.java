package com.cdqckj.gmis.charges.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.enums.RefundStatusEnum;
import com.cdqckj.gmis.charges.service.ChargeRecordService;
import com.cdqckj.gmis.charges.service.ChargeRefundService;
import com.cdqckj.gmis.charges.service.RefundCheckService;
import com.cdqckj.gmis.charges.service.RefundService;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 退费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/chargeRefund")
@Api(value = "ChargeRefund", tags = "退费记录")
@PreAuth(replace = "chargeRefund:")
public class ChargeRefundController extends SuperController<ChargeRefundService, Long, ChargeRefund, ChargeRefundPageDTO, ChargeRefundSaveDTO, ChargeRefundUpdateDTO> {

    @Autowired
    ChargeRefundService chargeRefundService;

    @Autowired
    RefundService refundService;

    @Autowired
    RefundCheckService refundCheckService;
    @Autowired
    ChargeRecordService chargeRecordService;

    @Autowired
    CustomerGasMeterRelatedService customerGasMeterRelatedService;

    @Autowired
    private GasMeterService gasMeterService;
    @Autowired
    private GasMeterVersionService gasMeterVersionService;
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<ChargeRefund> chargeRefundList = list.stream().map((map) -> {
            ChargeRefund chargeRefund = ChargeRefund.builder().build();
            //TODO 请在这里完成转换
            return chargeRefund;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(chargeRefundList));
    }

    /**
     * 申请退费
     */
    @GetMapping("/getByRefundNo")
    R<ChargeRefund> getByRefundNo(@RequestParam(value = "refundNo") String refundNo){
        return success(refundService.getByRefundNo(refundNo));
    }

    @ApiOperation(value = "查询一段时间内的退费总额")
    @PostMapping("/sumChargeRefundByTime")
    R<BigDecimal> sumChargeRefundByTime(@RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                        @RequestParam(value = "endTime", required = false) LocalDateTime endTime) {
        return success(baseService.sumChargeRefundByTime(startTime, endTime));
    }

    @ApiOperation(value = "申请退费")
    @PostMapping("/apply")
    @GlobalTransactional
    @CodeNotLost
    public R<ChargeRefund> apply(@RequestBody @Valid RefundApplySaveReqDTO applyDTO){
        return refundService.apply(applyDTO);
    }

    @ApiOperation(value = "审核退费")
    @PostMapping("/audit")
    @GlobalTransactional
    public R<Boolean> audit(@RequestBody @Valid RefundAuditSaveReqDTO auditDTO){
        return refundService.audit(auditDTO);

    }

    @ApiOperation(value = "退费")
    @PostMapping("/refund")
    @GlobalTransactional
    @CodeNotLost
    public R<RefundResultDTO> refund(@RequestParam(value = "refundId") Long refundId){
        return refundService.refund(refundId);
    }
    @ApiOperation(value = "三方支付退费完成")
    @PostMapping("/refundComplete")
    @GlobalTransactional
    @CodeNotLost
    public R<RefundResultDTO> refundComplete(@RequestBody RefundCompleteDTO refundInfo){
        return refundService.refundComplete(refundInfo);
    }
    /**
     * 分页查询退费列表
     * @param params
     * @return
     */
    @PostMapping(value = "/pageList")
    @ApiOperation(value = "分页查询退费列表")
    public R<IPage<ChargeRefundResDTO>> pageList(@RequestBody @Validated PageParams<RefundListReqDTO> params){
        RefundListReqDTO req = params.getModel();
        LbqWrapper<ChargeRefund> wrapper= new LbqWrapper<>();
        if(StringUtils.isNotBlank(req.getRefundNo())){
            wrapper=wrapper.eq(ChargeRefund::getRefundNo,req.getRefundNo());
        }
        if(StringUtils.isNotBlank(req.getChargeNo())){
            wrapper=wrapper.eq(ChargeRefund::getChargeNo,req.getChargeNo());
        }
        if(StringUtils.isNotBlank(req.getCustomerCode())){
            wrapper=wrapper.eq(ChargeRefund::getCustomerCode,req.getCustomerCode());
        }
        if(StringUtils.isNotBlank(req.getCustomerName())){
            wrapper=wrapper.eq(ChargeRefund::getCustomerName,req.getCustomerName());
        }
        if(StringUtils.isNotBlank(req.getCustomerChargeNo())){
            wrapper=wrapper.eq(ChargeRefund::getCustomerChargeNo,req.getCustomerChargeNo());
        }

        if(StringUtils.isNotBlank(req.getAuditStatus())){
            if(RefundStatusEnum.AUDITED.eq(req.getAuditStatus())){
                List<String> list=new ArrayList<>();
                for (RefundStatusEnum value : RefundStatusEnum.values()) {
                    if(!RefundStatusEnum.WAITE_AUDIT.eq(value.getCode()) &&
                            !RefundStatusEnum.APPLY.eq(value.getCode())
                    ){
                        list.add(value.getCode());
                    }
                }
                wrapper=wrapper.in(ChargeRefund::getRefundStatus,list);
            }else{
                wrapper=wrapper.eq(ChargeRefund::getRefundStatus,req.getAuditStatus());
            }
        }
        if(req.getStart()!=null){
            wrapper=wrapper.ge(ChargeRefund::getCreateTime,req.getStart().atTime(0,0,0,0));
        }
        if(req.getEnd()!=null){
            wrapper=wrapper.le(ChargeRefund::getCreateTime,req.getEnd().atTime(23,59,59,999));
        }
        wrapper.in(ChargeRefund::getOrgId, UserOrgIdUtil.getUserDataScopeList());
        IPage<ChargeRefund> queryPage=refundService.page(params.getPage(),wrapper);
        List<ChargeRefund> queryList=queryPage.getRecords();
        List<ChargeRefundResDTO> resultList=new ArrayList();
        if(CollectionUtils.isNotEmpty(queryList)){
            ChargeRefundResDTO dto;
            for (ChargeRefund refund : queryList) {
                dto=new ChargeRefundResDTO();
                BeanPlusUtil.copyProperties(refund,dto);
                resultList.add(dto);
            }
            List<ChargeRecord> recordList=chargeRecordService.list(new LbqWrapper< ChargeRecord >()
                    .select(ChargeRecord::getChargeNo,
                            ChargeRecord::getGasMeterCode,
                            ChargeRecord::getCustomerCode)
                .in(ChargeRecord::getChargeNo)
            );
            Set<String> gasMeterCodeSet=new HashSet<>();
            Set<String> customerCodeSet=new HashSet<>();
            for (ChargeRecord record : recordList) {
                gasMeterCodeSet.add(record.getGasMeterCode());
                customerCodeSet.add(record.getCustomerCode());
            }
            //关系表
//            List<CustomerGasMeterRelated> rlist=customerGasMeterRelatedService.list(new LbqWrapper<CustomerGasMeterRelated>()
//                    .in(CustomerGasMeterRelated::getCustomerCode,customerCodeSet)
//                    .eq(CustomerGasMeterRelated::getDataStatus, DataStatusEnum.NORMAL.getValue())
//            );
            List<GasMeter> mlist=gasMeterService.list(new LbqWrapper<GasMeter>()
                    .in(GasMeter::getGasCode,gasMeterCodeSet)
            );
            List<Long> vids=new ArrayList<>();
            Map<String,Long> meterVersion=new HashMap<>();
            for (GasMeter meter : mlist) {
                vids.add(meter.getGasMeterVersionId());
                meterVersion.put(meter.getGasCode(),meter.getGasMeterVersionId());
            }
            List<GasMeterVersion> vlist=gasMeterVersionService.list(new LbqWrapper<GasMeterVersion>()
                    .in(GasMeterVersion::getId,vids)
            );
            for (ChargeRefundResDTO re : resultList) {
//                for (CustomerGasMeterRelated related : rlist) {
//                    if(re.getGasMeterCode().equals(related.getGasMeterCode())
//                            && re.getCustomerCode().equals(related.getCustomerCode())){
//                        re.setCustomerChargeNo(related.getCustomerChargeNo());
//                        break;
//                    }
//                }
                for (GasMeterVersion version : vlist) {
                    if(version.getId().equals(meterVersion.get(re.getGasMeterCode()))){
                        re.setOrderSourceName(version.getOrderSourceName());
                        re.setOrderSourceNameDesc(OrderSourceNameEnum.get(version.getOrderSourceName()).getDesc());
                        break;
                    }
                }
            }
        }
        IPage<ChargeRefundResDTO> resultPage=refundService.page(params.getPage(),wrapper);
        resultPage.setPages(queryPage.getPages());
        resultPage.setTotal(queryPage.getTotal());
        resultPage.setSize(queryPage.getSize());
        resultPage.setCurrent(queryPage.getCurrent());
        resultPage.setRecords(resultList);
        return R.success(resultPage);
    }


    /**
     * 取消退费
     */
    @PostMapping("/cancelRefund")
    @ApiOperation("取消退费")
    public R<Boolean> cancelRefund(@RequestParam(value = "refundId") Long refundId){
        return refundService.cancelRefund(refundId);
    }

    /**
     * 检测是否可以退费申请
     */
    @PostMapping("/checkRefundApply")
    @ApiOperation("检测是否可以退费申请")
    public R<RefundCheckDTO> checkRefundApply(@RequestParam(value = "chargeNo") String chargeNo){
        return R.success(refundCheckService.validRefund(chargeNo));
//        return R.success(true);
    }

    /**
     * 判断是否可扎帐
     * @return
     */
    @ApiOperation("判断是否可扎帐")
    @PostMapping("/isSummaryBill")
    public R<Boolean> isSummaryBill(@RequestParam("chargeUserId") long chargeUserId, @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                    @RequestParam(value = "endTime", required = false) LocalDateTime endTime){
        return R.success(baseService.isSummaryBill(chargeUserId, startTime, endTime));
    }

    @ApiOperation(value = "退费详细加载")
    @GetMapping("/loadRefundAllInfo")
    public R<RefundLoadDTO> loadRefundAllInfo(@RequestParam(value = "refundId") Long refundId){
        return R.success(refundCheckService.loadInfo(refundId));

    }

}
