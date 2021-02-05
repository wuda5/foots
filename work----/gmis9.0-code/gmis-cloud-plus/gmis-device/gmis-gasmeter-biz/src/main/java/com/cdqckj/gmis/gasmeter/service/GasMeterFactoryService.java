package com.cdqckj.gmis.gasmeter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryPageDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;

/**
 * <p>
 * 业务接口
 * 气表厂家
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
public interface GasMeterFactoryService extends SuperService<GasMeterFactory> {
    Boolean queryGasMeter(GasMeterFactory gasMeterFactory);
    GasMeterFactory queryFactoryByName(GasMeterFactory gasMeterFactory);

    /**
     * 厂家配置分页查询
     * @author hc
     * @date 2020/12/01
     * @param params
     * @return
     */
    IPage<GasMeterFactory> pageQuery(PageParams<GasMeterFactoryPageDTO> params);

}
