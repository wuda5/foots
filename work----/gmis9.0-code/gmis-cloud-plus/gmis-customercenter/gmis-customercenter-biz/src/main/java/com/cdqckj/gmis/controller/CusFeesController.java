package com.cdqckj.gmis.controller;

import com.alibaba.fastjson.JSON;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.calculate.vo.ConversionVO;
import com.cdqckj.gmis.charges.dto.ChargeLoadDTO;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.entity.dto.CusFeesPayDTO;
import com.cdqckj.gmis.entity.dto.FeesGasMeterInfoVO;
import com.cdqckj.gmis.entity.vo.CusFeesInfoVO;
import com.cdqckj.gmis.entity.vo.CusFeesPayVO;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.service.CusFeesService;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户缴费管理模块
 * @auther HC
 * @date 2020/10/19
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/cusFees")
@Api(value = "cusFees",tags = "客户缴费管理")
public class CusFeesController {

    @Autowired
    private CusFeesService cusFeesService;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;

    @GetMapping("/detail")
    @ApiOperation("缴费加载")
    @ApiImplicitParam(name = "cusCode",value = "客户编码",required = true)
    public R<CusFeesInfoVO> feesDetail(@RequestParam("cusCode") @NotNull String cusCode,
                                       @RequestParam("gascode") @NotNull String gascode) throws InvocationTargetException, IllegalAccessException {

        CusFeesInfoVO resultData = new CusFeesInfoVO();

        //判断当前表具是否存在尚未完成的订单

        //缴费数据加载
        R<ChargeLoadDTO> loadDTOR = cusFeesService.chargeLoad(cusCode,gascode);
        if(loadDTOR.getIsError()){
            return R.fail(loadDTOR.getMsg());
        }else if(null == loadDTOR.getData()){
            return R.fail("缴费数据加载失败");
        }
        ChargeLoadDTO chargeData = loadDTOR.getData();

        // 获取表具信息
        R<HashMap<String,Object>> gasMeterInfoR = gasMeterBizApi.findGasMeterInfoByCode(gascode);
        if(gasMeterInfoR.getIsError()){
            return R.fail(gasMeterInfoR.getMsg());
        }else if(null == gasMeterInfoR.getData()){
            return R.fail("表具信息获取失败");
        }
        HashMap<String,Object> gasMeterInfo = gasMeterInfoR.getData();
        //气表详情
        FeesGasMeterInfoVO feesGasMeterInfoVO = JSON.parseObject(JSON.toJSONString(gasMeterInfo), FeesGasMeterInfoVO.class);

        resultData.setGasCode(feesGasMeterInfoVO.getGasCode());
        resultData.setGasMeterAddress(feesGasMeterInfoVO.getMoreGasMeterAddress());

        resultData.setCustomerName(feesGasMeterInfoVO.getCustomerName());
        resultData.setCustomerCode(feesGasMeterInfoVO.getCustomerCode());
        resultData.setTelPhone(feesGasMeterInfoVO.getTelphone());
        resultData.setItemList(chargeData.getItemList());
        resultData.setAccountBalance(chargeData.getAccountMoney());
        resultData.setArrearage(chargeData.getTotalMoney());
        resultData.setCusId(feesGasMeterInfoVO.getCustomerId());
        resultData.setGasMeterNumber(feesGasMeterInfoVO.getGasMeterNumber());
        resultData.setCustomerChargeNo(feesGasMeterInfoVO.getCustomerChargeNo());
        return R.success(resultData);
    }

    /**
     * 下单
     * @auther hc
     * @param cusFeesPayDTO
     * @return
     */
    @PostMapping("/placeOrder")
    @ApiOperation("下单")
    @SysLog("燃气缴费下单")
    @GlobalTransactional
    public R<CusFeesPayVO> placeOrder(@RequestBody @Valid CusFeesPayDTO cusFeesPayDTO){
        //所有类型的表具：下单都视为预存账户，再从账户里面扣除
        // 获取表具信息
        R<GasMeter> gasMeterInfo = gasMeterBizApi.findGasMeterByCode(cusFeesPayDTO.getGasCode());
        if(gasMeterInfo.getIsError()){
            return R.fail(gasMeterInfo.getMsg());
        }else if(null == gasMeterInfo.getData()){
            return  R.fail("该表具信息不存在");
        }
        GasMeter gasMeter = gasMeterInfo.getData();
        //充值(预付费，只能充一种)
        if(cusFeesPayDTO.getRechargeGas().compareTo(BigDecimal.ZERO)>0
                && cusFeesPayDTO.getRechargeMoney().compareTo(BigDecimal.ZERO)>0){
            return R.fail("表端充值一次只能充一种类型");
        }

        return cusFeesService.generalCharges(cusFeesPayDTO,gasMeter);
    }

    @ApiOperation(value = "订单查询接口", notes = "订单查询接口")
    @GetMapping("/orderquery")
    @SysLog("燃气缴费订单查询")
    public R<Map<String, String>> orderquery(@RequestParam("orderNumber") String orderNumber){
        return cusFeesService.orderquery(orderNumber);
    }

    /**
     * 气量金额换算值
     * @param conversionVO
     * @return
     */
    @ApiOperation(value = "气量或金额换算",notes = "气量金额换算")
    private R<BigDecimal> conversion(@RequestBody ConversionVO conversionVO){

        return cusFeesService.conversion(conversionVO);
    }
}
