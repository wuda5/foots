package com.cdqckj.gmis.iot.qc.service;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribeSaveDTO;
import com.cdqckj.gmis.iot.qc.dto.IotSubscribeUpdateDTO;
import com.cdqckj.gmis.iot.qc.entity.IotSubscribe;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-10-12
 */
public interface IotSubscribeService extends SuperService<IotSubscribe> {
    /**
     * 返回是否订阅
     * @return
     * @throws Exception
     */
    IotR isSubscribe(String domainId) throws Exception;

    /**
     * 订阅
     * @return
     * @throws Exception
     */
    IotR subscribe(String domainId) throws Exception;

    /**
     * 退订
     * @return
     * @throws Exception
     */
    IotR unsubscribe(String domainId) throws Exception;

    /**
     * 更具租户编码和厂家id查询
      * @param tenant
     * @param domainId
     * @return
     */
    IotSubscribe queryByTenant(String tenant,String domainId);

    Boolean check(IotSubscribeUpdateDTO iotSubscribeUpdateDTO);

    Boolean checkAdd(IotSubscribeSaveDTO iotSubscribeSaveDTO);

    /**
     * 根据厂家编号获取配置
     * @param factoryCode
     * @return
     */
    IotSubscribe queryByFactoryCode(String factoryCode);
}
