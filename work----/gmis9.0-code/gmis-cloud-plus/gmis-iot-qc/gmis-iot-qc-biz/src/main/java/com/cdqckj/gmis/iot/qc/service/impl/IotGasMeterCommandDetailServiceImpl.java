package com.cdqckj.gmis.iot.qc.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.iot.qc.dao.IotGasMeterCommandDetailMapper;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterCommandDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 业务实现类
 * 物联网气表指令明细数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class IotGasMeterCommandDetailServiceImpl extends SuperServiceImpl<IotGasMeterCommandDetailMapper, IotGasMeterCommandDetail> implements IotGasMeterCommandDetailService {
    @Override
    public IotGasMeterCommandDetail getByCommandId(Long id) {
        return baseMapper.selectById(id);
    }
}
