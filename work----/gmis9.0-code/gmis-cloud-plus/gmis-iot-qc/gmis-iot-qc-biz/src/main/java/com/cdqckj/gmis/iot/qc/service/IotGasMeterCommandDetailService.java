package com.cdqckj.gmis.iot.qc.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;


/**
 * <p>
 * 业务接口
 * 物联网气表指令明细数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
public interface IotGasMeterCommandDetailService extends SuperService<IotGasMeterCommandDetail> {
     IotGasMeterCommandDetail getByCommandId(Long id);
}
