package com.cdqckj.gmis.gasmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.gasmeter.dao.GasMeterVersionMapper;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 气表版本
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasMeterVersionServiceImpl extends SuperServiceImpl<GasMeterVersionMapper, GasMeterVersion> implements GasMeterVersionService {
    @Override
    public Boolean queryGasMeter(GasMeterVersion gasMeterVersion) {
        return super.count(Wraps.<GasMeterVersion>lbQ()
                .eq(GasMeterVersion::getDeleteStatus, 0)
                .ne(GasMeterVersion::getId, gasMeterVersion.getId())
                .eq(GasMeterVersion::getGasMeterVersionName, gasMeterVersion.getGasMeterVersionName())
                .eq(GasMeterVersion::getCompanyCode,gasMeterVersion.getCompanyCode())
        ) > 0;
    }

    @Override
    public GasMeterVersion queryVersion(GasMeterVersion gasMeterVersion) {
        LbqWrapper<GasMeterVersion> gasMeterVersionWrapper = Wraps.<GasMeterVersion>lbQ();
        gasMeterVersionWrapper.eq(GasMeterVersion::getCompanyId, gasMeterVersion.getCompanyId());
        gasMeterVersionWrapper.eq(GasMeterVersion::getGasMeterVersionName, gasMeterVersion.getGasMeterVersionName());
        return baseMapper.selectOne(gasMeterVersionWrapper);
    }

    @Override
    public List<GasMeterVersion> queryVersionEx(GasMeterVersion data) {
        LbqWrapper<GasMeterVersion> gasMeterVersionWrapper = Wraps.<GasMeterVersion>lbQ();
        gasMeterVersionWrapper.eq(GasMeterVersion::getCompanyId,data.getCompanyId());
        gasMeterVersionWrapper.eq(GasMeterVersion::getVersionState,data.getVersionState());
        gasMeterVersionWrapper.eq(GasMeterVersion::getDeleteStatus,data.getDeleteStatus());
        gasMeterVersionWrapper.eq(GasMeterVersion::getCompanyCode,data.getCompanyCode());
        gasMeterVersionWrapper.eq(GasMeterVersion::getGasMeterVersionName,data.getGasMeterVersionName());
        if(data.getOrderSourceName()!=null) {
            gasMeterVersionWrapper.in(GasMeterVersion::getOrderSourceName, data.getOrderSourceName().split(","));
        }
        return baseMapper.selectList(gasMeterVersionWrapper);
    }
}
