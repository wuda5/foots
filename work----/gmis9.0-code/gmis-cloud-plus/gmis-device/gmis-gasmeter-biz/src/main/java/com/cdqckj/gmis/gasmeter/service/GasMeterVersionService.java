package com.cdqckj.gmis.gasmeter.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 气表版本
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
public interface GasMeterVersionService extends SuperService<GasMeterVersion> {
    Boolean queryGasMeter(GasMeterVersion gasMeterVersion);
    GasMeterVersion queryVersion(GasMeterVersion gasMeterVersion);

    /**版号查询
     * @auther hc
     * @date 2021/01/06
     * **/
    List<GasMeterVersion> queryVersionEx(GasMeterVersion data);

}
