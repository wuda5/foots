package com.cdqckj.gmis.authority.service.auth.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.authority.dao.auth.SystemApiMapper;
import com.cdqckj.gmis.authority.entity.auth.SystemApi;
import com.cdqckj.gmis.authority.dto.auth.SystemApiSaveDTO;
import com.cdqckj.gmis.authority.dto.auth.SystemApiScanSaveDTO;
import com.cdqckj.gmis.authority.service.auth.SystemApiService;
import com.cdqckj.gmis.base.service.SuperCacheServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cdqckj.gmis.common.constant.CacheKey.SYSTEM_API;

/**
 * <p>
 * 业务实现类
 * API接口
 * </p>
 *
 * @author gmis
 * @date 2019-12-15
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SystemApiServiceImpl extends SuperCacheServiceImpl<SystemApiMapper, SystemApi> implements SystemApiService {
    @Override
    protected String getRegion() {
        return SYSTEM_API;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchSave(SystemApiScanSaveDTO data) {
        List<SystemApiSaveDTO> list = data.getSystemApiList();
        if (CollUtil.isEmpty(list)) {
            return false;
        }

        list.forEach((dto) -> {
            try {
                SystemApi systemApi = BeanPlusUtil.toBean(dto, SystemApi.class);
                SystemApi save = this.getApi(dto.getCode());
                if (save == null) {
                    systemApi.setIsOpen(false);
                    systemApi.setIsPersist(true);
                    super.save(systemApi);
                } else {
                    systemApi.setId(save.getId());
                    super.updateById(systemApi);
                }
            } catch (Exception e) {
                log.warn("api初始化失败", e);
            }
        });

        return true;
    }

    public SystemApi getApi(String code) {
        LbqWrapper<SystemApi> wrapper = Wraps.<SystemApi>lbQ().eq(SystemApi::getCode, code);
        return baseMapper.selectOne(wrapper);
    }
}
