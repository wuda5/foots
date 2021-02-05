package com.cdqckj.gmis.gasmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.gasmeter.dao.GasMeterModelMapper;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import com.cdqckj.gmis.gasmeter.service.GasMeterModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 气表型号
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasMeterModelServiceImpl extends SuperServiceImpl<GasMeterModelMapper, GasMeterModel> implements GasMeterModelService {
    @Override
    public Boolean queryGasMeterModelCheck(GasMeterModel gasMeterModel) {
        //同一厂商下的版本号下，规格名同时，型号名不可重复
        return super.count(Wraps.<GasMeterModel>lbQ()
                .ne(GasMeterModel::getId, gasMeterModel.getId())
                .eq(GasMeterModel::getDataStatus, 1)

                .eq(GasMeterModel::getCompanyId,gasMeterModel.getCompanyId())
                .eq(GasMeterModel::getGasMeterVersionId,gasMeterModel.getGasMeterVersionId())
                .eq(GasMeterModel::getSpecification,gasMeterModel.getSpecification())
                .eq(GasMeterModel::getModelName, gasMeterModel.getModelName())
        ) > 0;
    }

    @Override
    public GasMeterModel queryModel(GasMeterModel gasMeterModel) {
        return baseMapper.selectOne(Wraps.<GasMeterModel>lbQ()
                .eq(GasMeterModel::getCompanyId, gasMeterModel.getCompanyId())
                .eq(GasMeterModel::getGasMeterVersionId, gasMeterModel.getGasMeterVersionId())
                .eq(GasMeterModel::getModelName, gasMeterModel.getModelName()));
    }
}
