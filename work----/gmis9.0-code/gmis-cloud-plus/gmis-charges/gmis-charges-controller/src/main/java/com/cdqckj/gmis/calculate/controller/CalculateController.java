package com.cdqckj.gmis.calculate.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.calculate.CalculateIotService;
import com.cdqckj.gmis.calculate.CalculateService;
import com.cdqckj.gmis.calculate.vo.CalculateVO;
import com.cdqckj.gmis.calculate.vo.ConversionVO;
import com.cdqckj.gmis.calculate.vo.SettlementVO;
import com.cdqckj.gmis.common.domain.code.BizCodeBaseUtil;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.operateparam.util.CalRoundUtil;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/calculate")
@Api(value = "calculate", tags = "计费服务")
public class CalculateController {
    @Autowired
    private CalculateService calculateService;

    @Autowired
    private CalculateIotService calculateIotService;
    /**
     * 阶梯计费
     * @param calculateVO
     * @return
     */
    @PostMapping("/gasCost")
    @ApiOperation("计算燃气费用")
    @GlobalTransactional
    public R<SettlementVO> calculateGasCost(@RequestBody CalculateVO calculateVO) {
        return calculateService.calculateLadderGasCost(calculateVO);
    }

    /**
     * 结算
     * @param list
     * @return
     */
    @PostMapping("/settlement")
    @ApiOperation("结算燃气费用(谱表)")
    @CodeNotLost
    @GlobalTransactional
    public R<List<ReadMeterData>> settlement(@RequestBody List<ReadMeterData> list, @RequestParam(value = "type") int type) {
        return calculateService.settlement(list,type);
    }

    /**
     * 抵扣退费
     * @param list
     * @return
     */
    @PostMapping("/unSettlement")
    @ApiOperation("抵扣退费(谱表)")
    @CodeNotLost
    @GlobalTransactional
    public R<List<ReadMeterData>> unSettlement(@RequestBody List<ReadMeterData> list) {
        return calculateService.unSettlement(list);
    }

    /**
     * 结算
     * @param list
     * @return
     */
    @PostMapping("/iot/settlement")
    @ApiOperation("结算燃气费用(物联网表)")
    @CodeNotLost
    @GlobalTransactional
    public R<List<ReadMeterDataIot>> settlementIot(@RequestBody List<ReadMeterDataIot> list,@RequestParam(value = "type") int type) {
        return calculateIotService.settlement(list,type);
    }

    /**
     * 物联网表抵扣退费
     * @param list
     * @return
     */
    @PostMapping("/unSettlementIot")
    @ApiOperation("抵扣退费(物联网表)")
    @CodeNotLost
    @GlobalTransactional
    public R<List<ReadMeterDataIot>> unSettlementIot(@RequestBody List<ReadMeterDataIot> list) {
        return calculateIotService.unSettlementEX(list);
    }
    /**
     * 气量或金额换算
     * @param conversionVO
     * @return
     */
    @PostMapping("/conversion")
    @ApiOperation("气量或金额换算")
    @CodeNotLost
    @GlobalTransactional
    public R<BigDecimal> conversion(@RequestBody ConversionVO conversionVO){
        return calculateService.conversion(conversionVO);
    }

    /**
     * 计算滞纳金
     * @return
     */
    @GetMapping("/calculatePenalty")
    @ApiOperation("计算滞纳金")
    @GlobalTransactional
    public R<Boolean> calculatePenalty(@RequestParam(value = "gasMeterCode",required = false) String gasMeterCode){
        return calculateService.calculatePenalty(gasMeterCode);
    }

    /**
     * 查询气价方案
     * @param meterCode
     * @param activateDate
     * @return
     */
    @GetMapping("/queryPriceScheme")
    @ApiOperation("查询气价方案")
    R<PriceScheme> queryPriceScheme(@RequestParam("meterCode") String meterCode,
                                    @RequestParam("activateDate") LocalDate activateDate){
        return calculateService.queryPriceScheme(meterCode,activateDate);
    }

    @GetMapping("/test1")
    @ApiOperation("test1")
    R<BigDecimal> test1(){
        BigDecimal data = new BigDecimal(1.250121210);
        BigDecimal value = CalRoundUtil.bigDecimalRound(data);
        return R.success(value);
    }

    @GetMapping("/test2")
    @ApiOperation("test2")
    @CodeNotLost
    R test2(){

        String beginId = BaseContextHandler.getLocalMap().get(BizCodeBaseUtil.CODE_CREATE_BEGIN_ID);

//        String code = BizCodeBaseUtil.createAutoIncCode(Customer.class, "customerCode",10);
//        String code = BizCodeUtilBaseUtil.createRandomCode(Customer.class, "customerCode",10);
//        Integer a = null;
//        a.toString();

//        CodeInfo codeInfo = new CodeInfo();
//        codeInfo.setCodeTypeEnum(CodeTypeEnum.RANDOM_TYPE_ONE);
//        codeInfo.setCodeGroupKey("METERNO");
//        codeInfo.setTableName("da_gas_meter");
//        codeInfo.setColName("gas_meter_number");
//
//        String code = BizCodeBaseUtil.createCode(codeInfo,null,12,null);

        return R.success();
    }

    @PostMapping("/iot/settlementIotEX")
    @GlobalTransactional
    R<List<ReadMeterDataIot>> settlementIotEX(@RequestBody List<ReadMeterDataIot> list,@RequestParam(value = "type") int type
            ,@RequestParam(value = "executeDate") String executeDate){
        return calculateIotService.settlementIotEX(list,type,executeDate);
    }

    @GetMapping("/calculatePenaltyEX")
    @GlobalTransactional
    R<Boolean> calculatePenaltyEX(@RequestParam(value = "executeDate") String executeDate){
        return calculateService.calculatePenaltyEX(null,executeDate);
    }

    /**
     * 获去物联网表结算信息
     * @auther hc
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    @GetMapping("/findSettleData")
    List<ReadMeterDataIot> findSettleData(@RequestParam("gasMeterCode") String gasMeterCode,
                                          @RequestParam("customerCode") String customerCode){
        return calculateIotService.findSettleData(gasMeterCode,customerCode);
    }
}
