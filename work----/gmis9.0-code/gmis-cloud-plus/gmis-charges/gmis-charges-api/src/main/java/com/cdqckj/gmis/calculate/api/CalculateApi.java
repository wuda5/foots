package com.cdqckj.gmis.calculate.api;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.calculate.vo.ConversionVO;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory  = HystrixTimeoutFallbackFactory.class
        , path = "/calculate", qualifier = "calculateApi")
public interface CalculateApi {

    /**
     * 结算
     * @param list
     * @param type (0-普通抄表结算，1-拆表换表结算)
     * @return
     */
    @PostMapping("/settlement")
    R<List<ReadMeterData>> settlement(@RequestBody List<ReadMeterData> list, @RequestParam(value = "type") int type);

    /**
     * 结算燃气费用(物联网表)
     * @param list
     * @return
     */
    @PostMapping("/iot/settlement")
    R<List<ReadMeterDataIot>> settlementIot(@RequestBody List<ReadMeterDataIot> list,@RequestParam(value = "type") int type);

    /**
     * 结算燃气费用(物联网表)
     * @param list
     * @return
     */
    @PostMapping("/iot/settlementIotEX")
    R<List<ReadMeterDataIot>> settlementIotEX(@RequestBody List<ReadMeterDataIot> list,@RequestParam(value = "type") int type
            ,@RequestParam(value = "executeDate") String executeDate);

    /**
     * 计算滞纳金
     * @return
     */
    @GetMapping("/calculatePenalty")
    R<Boolean> calculatePenalty(@RequestParam(value = "gasMeterCode",required = false) String gasMeterCode);

    /**
     * 计算滞纳金
     * @return
     */
    @GetMapping("/calculatePenaltyEX")
    R<Boolean> calculatePenaltyEX(@RequestParam(value = "executeDate") String executeDate);

    /**
     * 抵扣退费
     * @param list
     * @return
     */
    @PostMapping("/unSettlement")
    R<List<ReadMeterData>> unSettlement(@RequestBody List<ReadMeterData> list);

    /**
     * 抵扣退费(物联网表)
     * @param list
     * @return
     */
    @PostMapping("/unSettlementIot")
    R<List<ReadMeterDataIot>> unSettlementIot(@RequestBody List<ReadMeterDataIot> list);

    /**
     * 气量或金额换算
     * @param conversionVO
     * @return
     */
    @PostMapping("/conversion")
    R<BigDecimal> conversion(@RequestBody ConversionVO conversionVO);

    /**
     * 查询价格方案
     * @param meterCode
     * @param activateDate
     * @return
     */
    @GetMapping("/queryPriceScheme")
    R<PriceScheme> queryPriceScheme(@RequestParam("meterCode") String meterCode,
                                    @RequestParam("activateDate") LocalDate activateDate);


    @GetMapping("/test1")
    @ApiOperation("我的测试代码1")
    R<BigDecimal> test1();

    @GetMapping("/test2")
    @ApiOperation("我的测试代码2")
    @CodeNotLost
    R test2();

    /**
     * 获去物联网表结算信息
     * @auther hc
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    @GetMapping("/findSettleData")
    List<ReadMeterDataIot> findSettleData(@RequestParam("gasMeterCode") String gasMeterCode,
                                          @RequestParam("customerCode") String customerCode);
}
