package com.cdqckj.gmis.iot.qc.service;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.iot.qc.entity.IotDeviceParams;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterUploadData;
import com.cdqckj.gmis.iot.qc.vo.*;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 物联网气表上报数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
public interface IotGasMeterUploadDataService extends SuperService<IotGasMeterUploadData> {
     /**
      * 新增设备
      * @param domainId
      * @param deviceDataVOList
      * @return
      */
     IotR addMeter(String domainId,List<DeviceDataVO> deviceDataVOList) throws Exception;

     /**
      * 修改设备
      * @param domainId
      * @param updateDeviceVO
      * @return
      * @throws Exception
      */
     IotR updateMeter(String domainId, UpdateDeviceVO updateDeviceVO) throws Exception;
     /**
      * 注册设备
      * @param domainId
      * @param registerDeviceVO
      * @return
      * @throws Exception
      */
     IotR registerMeter(String domainId, RegisterDeviceVO registerDeviceVO) throws Exception;

     /**
      * 移除设备
      * @param domainId
      * @param removeDeviceVO
      * @return
      * @throws Exception
      */
     IotR removeMeter(String domainId, RemoveDeviceVO removeDeviceVO) throws Exception;

     /**
      * 开户
       * @param domainId
      * @param openAccountVO
      * @return
      */
     IotR openAccount(String domainId, OpenAccountVO openAccountVO) throws Exception;

     /**
      * 阀控操作
      * @param domainId
      * @param valveControlVO
      * @return
      * @throws Exception
      */
     IotR valveControl(String domainId, ValveControlVO valveControlVO) throws Exception;

     /**
      * 阀控批量操作
      * @param domainId
      * @param valveControlVOList
      * @return
      * @throws Exception
      */
     IotR valveBatControl(String domainId, List<ValveControlVO> valveControlVOList) throws Exception;

     /**
      * 充值
      * @param domainId
      * @param rechargeVO
      * @return
      * @throws Exception
      */
     IotR recharge(String domainId, RechargeVO rechargeVO) throws Exception;

     /**
      * 调价
      * @param domainId
      * @param priceChangeVO
      * @return
      * @throws Exception
      */
     IotR changePrice(String domainId, PriceChangeVO priceChangeVO) throws Exception;

     /**
      * 批量调价
      * @param domainId
      * @param priceChangeList
      * @return
      * @throws Exception
      */
     IotR changeBatPrice(String domainId, List<PriceChangeVO> priceChangeList) throws Exception;

     /**
      * 物联网抄表数据补抄
      * @param domainId
      * @param paramsVOList
      * @return
      * @throws Exception
      */
     IotR readIotMeter(String domainId,List<ParamsVO> paramsVOList) throws Exception;

     /**
      * 参数设置
      * @param domainId
      * @param iotDeviceParams
      * @return
      * @throws Exception
      */
     IotR setDeviceParams(String domainId,List<IotDeviceParams> iotDeviceParams) throws Exception;

     /**
      * 物联网表清零
      * @param domainId
      * @param clearVO
      * @return
      * @throws Exception
      */
     IotR clearmeter(String domainId,ClearVO clearVO) throws Exception;

     /**
      * 更新设备余额
      * @param domainId
      * @param updateBalanceVO
      * @return
      * @throws Exception
      */
     IotR updatebalance(String domainId,UpdateBalanceVO updateBalanceVO) throws Exception;

     /**
      * 查重
      * @param params
      * @return
      */
     Boolean check(IotGasMeterUploadData params);

     /**
      * 查询表具档案
      * @param gasMeterCode
      * @param customerCode
      * @return
      */
     IotGasMeterUploadData queryIotGasMeter(String gasMeterCode,String customerCode);

     /**
      * 更新表具档案
      * @param iotGasMeterUploadData
      * @return
      */
     IotR updateIotGasMeter(IotGasMeterUploadData iotGasMeterUploadData);


     /**
      * 设备清零再注册
      * @param domainId
      * @param orderListVO
      * @return
      */
     IotR clearAndRegisterMeter(String domainId, OrderListVO orderListVO) throws Exception;

     /**
      * 查询有效表具
      * @param gasMeterNumber
      * @return
      */
     IotGasMeterUploadData queryActivateMeter(String gasMeterNumber);

}
