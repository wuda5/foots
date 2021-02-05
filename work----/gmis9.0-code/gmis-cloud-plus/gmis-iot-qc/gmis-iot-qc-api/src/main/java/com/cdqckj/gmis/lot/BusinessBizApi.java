package com.cdqckj.gmis.lot;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceParams;
import com.cdqckj.gmis.iot.qc.vo.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@FeignClient(name = "${gmis.feign.iot-qc-server:gmis-iot-qc-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/business", qualifier = "businessBizApi")
public interface BusinessBizApi {

    /**
     * 阀控操作
     * @param domainId
     * @param valveControlVO
     * @return
     */
    @PostMapping(value = "/valvecontrol")
    IotR valueControl(@RequestHeader("domainId")String domainId, @RequestBody ValveControlVO valveControlVO);

    /**
     * 开户
     * @param domainId
     * @param openAccountVO
     * @return
     */
    @PostMapping(value = "/openaccount")
    IotR openAccount(@RequestHeader("domainId")String domainId, @RequestBody OpenAccountVO openAccountVO);

    /**
     * 充值
     * @param domainId
     * @param rechargeVO
     * @return
     */
    @PostMapping(value = "/recharge")
    IotR recharge(@RequestHeader("domainId")String domainId, @RequestBody RechargeVO rechargeVO);

    /**
     * 调价
     * @param domainId
     * @param priceChangeVO
     * @return
     */
    @PostMapping(value = "/changeprice")
    IotR changeprice(@RequestHeader("domainId")String domainId, @RequestBody PriceChangeVO priceChangeVO);

    /**
     * 批量调价
     * @param domainId
     * @param priceChangeList
     * @return
     */
    @PostMapping(value = "/changebatprice")
    IotR changebatprice(@RequestHeader("domainId")String domainId, @RequestBody List<PriceChangeVO> priceChangeList);
    /**
     * 物联网表补抄数据
     * @param domainId
     * @param paramsVOList
     * @return
     */
    @PostMapping(value = "/readiotmeter")
    IotR readiotmeter(@RequestHeader("domainId")String domainId, @RequestBody List<ParamsVO> paramsVOList);

    /**
     * 设备清零
     * @param domainId
     * @param clearVO
     * @return
     */
    @PostMapping(value = "/clearmeter")
    IotR clearmeter(@RequestHeader("domainId")String domainId, @RequestBody ClearVO clearVO);

    /**
     * 更新设备余额
     * @param domainId
     * @param updateBalanceVO
     * @return
     */
    @PostMapping(value = "/updatebalance")
    IotR updatebalance(@RequestHeader("domainId")String domainId, @RequestBody UpdateBalanceVO updateBalanceVO);



    /**
     * 向mq发送物联网参数设置消息
     * @param domainId
     * @param iotDeviceParamsList
     * @return
     */
    @PostMapping(value = "/setDeviceParams")
    IotR setDeviceParams(@RequestHeader("domainId")String domainId, @RequestBody List<IotDeviceParams> iotDeviceParamsList);


    /**
     * 顺序执行清零再注册指令
     * @param domainId
     * @param orderListVO
     * @return
     */
    @PostMapping(value = "/clearAndRegisterMeter")
    IotR clearAndRegisterMeter(@RequestHeader("domainId")String domainId, @RequestBody OrderListVO orderListVO);
}
