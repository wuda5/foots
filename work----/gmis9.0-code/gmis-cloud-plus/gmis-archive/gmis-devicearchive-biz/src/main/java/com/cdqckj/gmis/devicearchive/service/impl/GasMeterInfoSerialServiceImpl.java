package com.cdqckj.gmis.devicearchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.devicearchive.dao.GasMeterInfoSerialMapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfoSerial;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoSerialService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 账户流水
 * </p>
 *
 * @author gmis
 * @date 2020-12-21
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasMeterInfoSerialServiceImpl extends SuperServiceImpl<GasMeterInfoSerialMapper, GasMeterInfoSerial> implements GasMeterInfoSerialService {
}
