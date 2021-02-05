package com.cdqckj.gmis.devicearchive.service;

import com.cdqckj.gmis.devicearchive.dto.GasMeterConfDTO;

public interface GasMeterConfService {

    /**
     * 根据档案号获取气表配置（厂家，版号，型号）信息
     *
     * @param gasMeterCode 档案号
     * @return 气表配置信息
     */
    GasMeterConfDTO findGasMeterConfInfoByCode(String gasMeterCode);
}
