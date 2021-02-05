package com.cdqckj.gmis.iot.qc.service;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import com.cdqckj.gmis.iot.qc.vo.RechargeVO;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 消息队列消费业务接口
 * @author: 秦川物联网科技
 * @date: 2020/10/15  13:01
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
public interface MessageService {

    /**
     * 新增设备信息
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR deviceAddEvent(String data, String tenant) throws Exception;

    /**
     * 更新设备
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR updateDeviceEvent(String data,String tenant) throws Exception;

    /**
     * 设备开户
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR openAccountEvent(String data, String tenant) throws Exception;

    /**
     * 阀控操作
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR valveControlEvent(String data, String tenant) throws Exception;

    /**
     * 阀控批量操作
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR valveBatControlEvent(String data, String tenant) throws Exception;

    /**
     * 设备注册
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR registerDeviceEvent(String data, String tenant) throws Exception;

    /**
     * 设备拆除
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR removeDeviceEvent(String data, String tenant) throws Exception;

    /**
     * 充值
     * @param rechargeVO
     * @param tenant
     * @return
     */
    IotR rechargeEvent(RechargeVO rechargeVO, String tenant) throws Exception;

    /**
     * 调价
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR changePriceEvent(String data,String tenant) throws Exception;

    /**
     * 批量调价
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR changeBatPriceEvent(String data,String tenant) throws Exception;
    /**
     * 设备数据
      * @param data
     * @param tenant
     * @return
     */
    IotR deviceDataEvent(String data,String tenant);

    /**
     * 业务数据
      * @param data
     * @param tenant
     * @return
     */
    IotR businessDataEvent(String data,String tenant);

    /**
     * 更新余额及单价
     * @param data
     * @param tenant
     * @return
     */
    IotR updateBalanceEvent(String data,String tenant) throws Exception;

    /**
     * 补抄
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR readIotMeterEvent(String data,String tenant) throws Exception;

    /**
     * 参数设置
     * @param data
     * @param tenant
     * @return
     * @throws Exception
     */
    IotR setDeviceParamsEvent(String data,String tenant) throws Exception;

    /**
     * 加入设备重试
     * @param data
     * @param domainId
     * @return
     * @throws Exception
     */
    IotR deviceAddEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception;

    /**
     * 注册设备重试
     * @param data
     * @param domainId
     * @return
     * @throws Exception
     */
    IotR registerDeviceEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception;

    /**
     * 更新设备
     * @param data
     * @param domainId
     * @return
     * @throws Exception
     */
    IotR updateDeviceEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception;

    /**
     * 移除设备重试
     * @param data
     * @param domainId
     * @return
     * @throws Exception
     */
    IotR removeDeviceEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception;

    /**
     * 移除逻辑域重试
     * @param data
     * @param domainId
     * @return
     * @throws Exception
     */
    IotR removeDomainEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception;

    /**
     * 充值指令重试
     * @param domainId
     * @param detail
     * @return
     * @throws Exception
     */
    IotR rechargeEventRetry(String domainId,IotGasMeterCommandDetail detail) throws Exception;
}
