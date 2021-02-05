package com.cdqckj.gmis.bizcenter.iot.service;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.bizcenter.iot.vo.AutoAddVo;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceBatVo;
import com.cdqckj.gmis.bizcenter.iot.vo.PriceVo;
import com.cdqckj.gmis.iot.qc.vo.*;

import java.text.ParseException;
import java.util.List;

public interface BusinessService extends SuperCenterService {
    /**
     * 阀控操作
     * @param valveControlList
     * @return
     */
    IotR valveControl(List<ValveControlVO> valveControlList);

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
     * @param priceBatVo
     * @return
     */
    IotR changebatprice(PriceBatVo priceBatVo);
    /**
     * 物联网表补抄
     * @param paramsVOList
     * @return
     */
    IotR readiotmeter(List<ParamsVO> paramsVOList);

    /**
     * 自动填充
     * @param autoAddVoList
     * @return
     */
    IotR autoAddIotMeterData(List<AutoAddVo> autoAddVoList);

    /**
     * 物联网表清零
     * @param clearVO
     * @return
     */
    IotR clearMeter(ClearVO clearVO);

    /**
     * 物联网更新设备金
     * @param updateBalanceVO
     * @return
     */
    IotR updatebalance(UpdateBalanceVO updateBalanceVO);

    /**
     * 设备清零再注册
     * @param clearVO
     * @param registerDeviceVO
     * @return
     */
    IotR clearAndRegisterMeter(ClearVO clearVO, RegisterDeviceVO registerDeviceVO);
}
