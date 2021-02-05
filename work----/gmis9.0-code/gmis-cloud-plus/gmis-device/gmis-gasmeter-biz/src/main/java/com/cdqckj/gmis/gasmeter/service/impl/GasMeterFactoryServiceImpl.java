package com.cdqckj.gmis.gasmeter.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.gasmeter.dao.GasMeterFactoryMapper;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryPageDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import com.cdqckj.gmis.gasmeter.service.GasMeterFactoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 气表厂家
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasMeterFactoryServiceImpl extends SuperServiceImpl<GasMeterFactoryMapper, GasMeterFactory> implements GasMeterFactoryService {
    @Override
    public Boolean queryGasMeter(GasMeterFactory gasMeterFactory) {
       return super.count(Wraps.<GasMeterFactory>lbQ()
               .eq(GasMeterFactory::getGasMeterFactoryStatus,1)
               .ne(GasMeterFactory::getId,gasMeterFactory.getId())
               .eq(GasMeterFactory::getDeleteStatus,gasMeterFactory.getDeleteStatus())
               .and(i->i.eq(GasMeterFactory::getGasMeterFactoryName,gasMeterFactory.getGasMeterFactoryName())
               .or().eq(GasMeterFactory::getGasMeterFactoryCode,gasMeterFactory.getGasMeterFactoryCode())
               .or().eq(GasMeterFactory::getGasMeterFactoryAddress,gasMeterFactory.getGasMeterFactoryAddress()))
       ) > 0;
    }

    @Override
    public GasMeterFactory queryFactoryByName(GasMeterFactory gasMeterFactory) {
        return baseMapper.selectOne(Wraps.<GasMeterFactory>lbQ()
                    .eq(GasMeterFactory::getGasMeterFactoryName, gasMeterFactory.getGasMeterFactoryName()));
    }

    @Override
    public IPage<GasMeterFactory> pageQuery(PageParams<GasMeterFactoryPageDTO> params) {
        Page<GasMeterFactory> page = new Page(params.getCurrent(), params.getSize());
        GasMeterFactoryPageDTO queryModel = params.getModel();
        QueryWrapper<GasMeterFactory> query = Wrappers.query();
        if(StringUtils.isNotEmpty(queryModel.getGasMeterFactoryName())) {
            query.like("mf.gas_meter_factory_name", queryModel.getGasMeterFactoryName());
        }
        if(StringUtils.isNotEmpty(queryModel.getGasMeterFactoryCode())) {
            query.eq("mf.gas_meter_factory_code", queryModel.getGasMeterFactoryCode());
        }
        query.eq("mf.delete_status", DeleteStatusEnum.NORMAL.getValue());
        return baseMapper.pageQuery(page,query);
    }

}
