package com.cdqckj.gmis.bizcenter.iot.strategy;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceBatVo;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceVo;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceParams;
import com.cdqckj.gmis.iot.qc.vo.*;

import java.text.ParseException;
import java.util.List;

public interface MeterStrategy {

    IotR setDeviceParamsQ(List<IotDeviceParams> IotDeviceParamsList);

    /**
     * 加入物联网设备
     * @param gasMeterList
     * @return
     */
    IotR addDevice(List<GasMeter> gasMeterList);

    /**
     * 阀控操作
     * @param valveControlList
     * @return
     */
    IotR valveControl(List<ValveControlVO> valveControlList);

    /**
     * 设备注册
     * @param registerDeviceVO
     * @return
     */
    IotR registerDevice(RegisterDeviceVO registerDeviceVO);

    /**
     * 移除表具
     * @param removeDeviceVO
     * @return
     */
    IotR removeDevice(RemoveDeviceVO removeDeviceVO);

    /**
     * 更新设备
     * @param updateDeviceVO
     * @return
     */
    IotR updateDevice(UpdateDeviceVO updateDeviceVO);

    /**
     * 开户
     * @param openAccountVO
     * @return
     */
    IotR openAccount(OpenAccountVO openAccountVO) throws ParseException;

    /**
     * 充值
     * @param rechargeVO
     * @return
     */
    IotR recharge(RechargeVO rechargeVO);

    /**
     * 调价
     * @param priceVo
     * @return
     */
    IotR changePrice(PriceVo priceVo);

    /**
     * 批量调价
     * @param priceBatVos
     * @return
     */
    IotR changeBatPrice(List<PriceBatVo> priceBatVos);

    /**
     * 设备清零，回到出厂状态
     * @param clearVO
     * @return
     */
    IotR clearMeter(ClearVO clearVO);

    /**
     * 更新设备金额
     * @param updateBalanceVO
     * @return
     */
    IotR updatebalance(UpdateBalanceVO updateBalanceVO);

    /**
     * 清零重新注册设备
     * @param orderListVO
     * @return
     */
    IotR clearAndRegisterMeter(String domainId, OrderListVO orderListVO);
}
