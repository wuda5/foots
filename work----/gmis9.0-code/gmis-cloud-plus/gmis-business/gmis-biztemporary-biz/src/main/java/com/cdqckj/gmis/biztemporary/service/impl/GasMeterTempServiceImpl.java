package com.cdqckj.gmis.biztemporary.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.biztemporary.dao.GasMeterTempMapper;
import com.cdqckj.gmis.biztemporary.entity.GasMeterTemp;
import com.cdqckj.gmis.biztemporary.service.GasMeterTempService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 气表档案临时表
 * </p>
 *
 * @author songyz
 * @date 2021-01-04
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasMeterTempServiceImpl extends SuperServiceImpl<GasMeterTempMapper, GasMeterTemp> implements GasMeterTempService {

    @Override
    public Boolean check(GasMeterTemp params) {
        return super.count(Wraps.<GasMeterTemp>lbQ()
                .eq(GasMeterTemp::getGasCode,params.getGasCode()))>0;
    }
}
