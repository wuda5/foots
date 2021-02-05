package com.cdqckj.gmis.gasmeter.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 气表厂家
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Repository
public interface GasMeterFactoryMapper extends SuperMapper<GasMeterFactory> {

    /**
     * 联合查询分页
     * @author hc
     * @param page
     * @param query
     * @return
     */
    IPage<GasMeterFactory> pageQuery(Page<GasMeterFactory> page, @Param(Constants.WRAPPER) Wrapper<GasMeterFactory> query);
}
