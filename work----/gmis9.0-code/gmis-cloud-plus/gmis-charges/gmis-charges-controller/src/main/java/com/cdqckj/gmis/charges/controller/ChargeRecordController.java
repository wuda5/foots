package com.cdqckj.gmis.charges.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.*;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.enums.SummaryBillStatusEnum;
import com.cdqckj.gmis.charges.service.ChargeRecordService;
import com.cdqckj.gmis.charges.vo.StsCounterStaffVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderDetailFeeVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderFeeVo;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsDateVo;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.utils.LocalDateUtil;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.seata.common.util.CollectionUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 缴费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/chargeRecord")
@Api(value = "ChargeRecord", tags = "缴费记录")
//@PreAuth(replace = "chargeRecord:")
public class ChargeRecordController extends SuperController<ChargeRecordService, Long, ChargeRecord, ChargeRecordPageDTO, ChargeRecordSaveDTO, ChargeRecordUpdateDTO> {

    @Autowired
    private ChargeRecordService chargeRecordService;

    @Autowired
    private CustomerGasMeterRelatedService customerGasMeterRelatedService;
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
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<ChargeRecord> chargeRecordList = list.stream().map((map) -> {
            ChargeRecord chargeRecord = ChargeRecord.builder().build();
            //TODO 请在这里完成转换
            return chargeRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(chargeRecordList));
    }
    @ApiOperation(value = "查询一段时间内的缴费记录")
    @PostMapping("/queryChargesRecord")
    R<List<ChargeRecord>> queryChargesRecord(@RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                             @RequestParam(value = "endTime", required = false) LocalDateTime endTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        long chargeUserId = BaseContextHandler.getUserId();
        QueryWrapper<ChargeRecord> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .eq(ChargeRecord::getCreateUserId, chargeUserId)
                .eq(ChargeRecord::getSummaryBillStatus, SummaryBillStatusEnum.UNBILL.getCode());
        if(ObjectUtil.isNull(startTime) && ObjectUtil.isNotNull(endTime)){
            wrapper.lambda().and(dateSql -> dateSql.apply("unix_timestamp(create_time) <= unix_timestamp({0})", endTime.format(dateTimeFormatter)));
        }
        if(ObjectUtil.isNull(endTime) && ObjectUtil.isNotNull(startTime)){
            wrapper.lambda().and(dateSql -> dateSql.apply("unix_timestamp(create_time) >= unix_timestamp({0})", startTime.format(dateTimeFormatter)));
        }
        if(ObjectUtil.isNotNull(startTime) && ObjectUtil.isNotNull(endTime)){
            wrapper.lambda()
                    .and(dateSql -> dateSql.apply("unix_timestamp(create_time) >= unix_timestamp({0})", startTime.format(dateTimeFormatter))
                            .apply("unix_timestamp(create_time) <= unix_timestamp({0})", endTime.format(dateTimeFormatter)));
        }
        return success(getBaseService().list(wrapper));
    }
    @ApiOperation(value = "修改票据发票状态")
    @PostMapping("/updateReceiptOrInvoiceStatus")
    R<Boolean> updateReceiptOrInvoiceStatus(@RequestParam(value = "chargeNo") String chargeNo,
                                            @RequestParam(value = "invoiceType") String invoiceType,
                                            @RequestParam(value = "invoiceStatus") String invoiceStatus
    ){
        return success(chargeRecordService.updateReceiptOrInvoiceStatus(chargeNo,invoiceType,invoiceStatus));
    }

    /**
     * 分页查询缴费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/pageList")
    @ApiOperation(value = "分页查询缴费记录")
    public R<Page<ChargeRecordResDTO>> pageList(@RequestBody @Validated PageParams<ChargeListReqDTO> params){
        ChargeListReqDTO req = params.getModel();
        LbqWrapper<ChargeRecord> wrapper= new LbqWrapper<>();
        if(StringUtils.isNotBlank(req.getChargeNo())){
            wrapper=wrapper.eq(ChargeRecord::getChargeNo,req.getChargeNo());
        }
        if(StringUtils.isNotBlank(req.getCustomerCode())){
            wrapper=wrapper.eq(ChargeRecord::getCustomerCode,req.getCustomerCode());
        }
        if(StringUtils.isNotBlank(req.getGasMeterCode())){
            wrapper=wrapper.eq(ChargeRecord::getGasMeterCode,req.getGasMeterCode());
        }
        if(StringUtils.isNotBlank(req.getCustomerName())){
            wrapper=wrapper.eq(ChargeRecord::getCustomerName,req.getCustomerName());
        }
        if(StringUtils.isNotBlank(req.getCustomerChargeNo())){
            wrapper=wrapper.eq(ChargeRecord::getCustomerChargeNo,req.getCustomerChargeNo());
        }
        if(req.getStart()!=null){
            wrapper=wrapper.ge(ChargeRecord::getCreateTime,req.getStart().atTime(0,0,0,0));
        }
        if(req.getEnd()!=null){
            wrapper=wrapper.le(ChargeRecord::getCreateTime,req.getEnd().atTime(23,59,59,999));
        }
        wrapper.in(ChargeRecord::getOrgId, UserOrgIdUtil.getUserDataScopeList());
        IPage<ChargeRecord> queryPage=chargeRecordService.page(params.getPage(),wrapper);
        Page<ChargeRecordResDTO> resultPage=new Page<>();
        List<ChargeRecordResDTO> resultList=new ArrayList<>();
        List<ChargeRecord> list=queryPage.getRecords();
        Set<String> gasMeterCodeSet=new HashSet<>();
        Set<String> customerCodeSet=new HashSet<>();
        if(CollectionUtils.isNotEmpty(list)) {
            ChargeRecordResDTO temp;
            for (ChargeRecord chargeRecord : list) {
                temp=new ChargeRecordResDTO();
                BeanPlusUtil.copyProperties(chargeRecord,temp);
//                if(set.contains(temp.getChargeNo())){
//                    temp.setIsApplyRefund(true);
//                }else{
//                    temp.setIsApplyRefund(false);
//                }

                gasMeterCodeSet.add(chargeRecord.getGasMeterCode());
                customerCodeSet.add(chargeRecord.getCustomerCode());
                resultList.add(temp);
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
            for (ChargeRecordResDTO re : resultList) {
//                for (CustomerGasMeterRelated related : rlist) {
//                    if(re.getGasMeterCode().equals(related.getGasMeterCode())
//                            && re.getCustomerCode().equals(related.getCustomerCode())){
//                            re.setCustomerChargeNo(related.getCustomerChargeNo());
//                            break;
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
        resultPage.setPages(queryPage.getPages());
        resultPage.setTotal(queryPage.getTotal());
        resultPage.setSize(queryPage.getSize());
        resultPage.setCurrent(queryPage.getCurrent());
        resultPage.setRecords(resultList);
        return R.success(resultPage);
    }

    @ApiOperation(value = "根据收费单号查询收费信息")
    @GetMapping("/getChargeInfoByNo")
    public R<ChargeRecord> getChargeRecordByNo(@RequestParam(value = "chargeNo") @NotBlank String chargeNo){
       return R.success(chargeRecordService.getChargeRecordByNo(chargeNo));
    }
    /**
     * 是否可扎帐
     * @return
     */
    @ApiOperation(value = "是否可扎帐")
    @PostMapping("/isSummaryBill")
    public R<Boolean> isSummaryBill(@RequestParam(value = "chargeUserId") long chargeUserId,
                                    @RequestParam(value = "startTime", required = false) LocalDateTime startTime,
                                    @RequestParam(value = "endTime", required = false) LocalDateTime endTime){
        return R.success(chargeRecordService.isSummaryBill(chargeUserId, startTime, endTime));
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/29 10:07
     * @remark 业绩看板_柜台人员统计
     */
    @PostMapping("/counterStaff")
    @ApiOperation(value = "业绩看板_柜台人员统计")
    public R<Page<StsCounterStaffVo>> counterStaff(@RequestBody StsSearchParam stsSearchParam){

        Page<StsCounterStaffVo> resultPage = new Page<>();
        if (stsSearchParam.getPageNo() == null){
            resultPage.setCurrent(1L);
        }else {
            resultPage.setCurrent(stsSearchParam.getPageNo());
        }
        // 这里前端传过来的数据清空
        stsSearchParam.setBusinessHallId(null);
//        stsSearchParam.setOrgId(null);
        List<StsCounterStaffVo> dataList = chargeRecordService.counterStaff(resultPage, stsSearchParam);
        resultPage.setRecords(dataList);
        return R.success(resultPage);
    }

    @ApiOperation(value = "统计收费的信息")
    @PostMapping(value = "/stsGasLadderFee")
    R<List<StsGasLadderFeeVo>> stsGasLadderFee(@RequestBody StsSearchParam stsSearchParam){
        List<Long> idList = (List<Long>) stsSearchParam.getDataMap().get("idList");
        List<StsGasLadderFeeVo> feeVoList = chargeRecordService.stsGasLadderFee(stsSearchParam, idList);
        return R.success(feeVoList);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/13 16:12
    * @remark 统计阶梯收费的详细信息
    */
    @ApiOperation(value = "统计阶梯收费的详细信息")
    @PostMapping(value = "/stsGasLadderDetailFee")
    R<List<StsGasLadderDetailFeeVo>> stsGasLadderDetailFee(@RequestBody StsSearchParam stsSearchParam){
        List<Long> idList = (List<Long>) stsSearchParam.getDataMap().get("idList");
        List<StsGasLadderDetailFeeVo> feeVoList = chargeRecordService.stsGasLadderDetailFee(stsSearchParam, idList);
        return R.success(feeVoList);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/15 15:24
     * @remark 统计收费数据
     */
    @PostMapping("sts/feeByChargeMethod")
    R<List<StsInfoBaseVo<String, BigDecimal>>> stsFeeByChargeMethod(@RequestBody StsSearchParam stsSearchParam){
        List<StsInfoBaseVo<String, BigDecimal>> dataList = chargeRecordService.stsFeeByChargeMethod(stsSearchParam);
        return R.success(dataList);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/15 15:24
     * @remark 收费的类型
     */
    @PostMapping("sts/chargeFeeItemType")
    R<List<StsInfoBaseVo<String, BigDecimal>>> stsChargeFeeItemType(@RequestBody StsSearchParam stsSearchParam){
        List<StsInfoBaseVo<String, BigDecimal>> dataList = chargeRecordService.stsChargeFeeItemType(stsSearchParam);
        return R.success(dataList);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/22 15:01
     * @remark 预付费表(IC卡表 物联网预付费 物联网表端计费)
     */
    @PostMapping("sts/stsBeforeGasMeter")
    public R<StsDateVo> stsBeforeGasMeter(@RequestBody StsSearchParam stsSearchParam){

        String gasMeterType = stsSearchParam.getSearchKeyValue("gasMeterType");
        StsDateVo vo = new StsDateVo();
        LocalDate now = LocalDate.now();
        stsSearchParam.setStartDay(now);
        stsSearchParam.setEndDay(LocalDateUtil.nextDay(now));
        BigDecimal today = this.baseService.stsBeforeGasMeter(stsSearchParam, gasMeterType);

        stsSearchParam.setStartDay(LocalDateUtil.monthBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextMonthBegin(now));
        BigDecimal month = this.baseService.stsBeforeGasMeter(stsSearchParam, gasMeterType);

        stsSearchParam.setStartDay(LocalDateUtil.yearBegin(now));
        stsSearchParam.setEndDay(LocalDateUtil.nextYearBegin(now));
        BigDecimal year = this.baseService.stsBeforeGasMeter(stsSearchParam, gasMeterType);

        stsSearchParam.setStartDay(null);
        stsSearchParam.setEndDay(null);
        BigDecimal all = this.baseService.stsBeforeGasMeter(stsSearchParam, gasMeterType);

        vo.setTodayNum(today);
        vo.setMonthNum(month);
        vo.setYearNum(year);
        vo.setAllNum(all);
        return R.success(vo);
    }

    /**
     * 右侧业务关注点分页查询缴费记录
     * @param params
     * @return
     */
    @ApiOperation(value = "右侧业务关注点分页查询缴费记录")
    @PostMapping(value = "/pageQueryFocusInfo")
    R<Page<ChargeRecord>> pageQueryFocusInfo(@RequestBody PageParams<ChargeRecordPageDTO> params){
        Page<ChargeRecord> page = new Page<>(params.getCurrent(), params.getSize());
        ChargeRecord model = BeanUtil.toBean(params.getModel(), ChargeRecord.class);
        LbqWrapper<ChargeRecord> wrapper = Wraps.lbQ(model);
        wrapper.in(ChargeRecord::getOrgId, UserOrgIdUtil.getUserDataScopeList())
                .orderByDesc(Entity::getUpdateTime);
        this.baseService.page(page, wrapper);
        return success(page);
    }

}
