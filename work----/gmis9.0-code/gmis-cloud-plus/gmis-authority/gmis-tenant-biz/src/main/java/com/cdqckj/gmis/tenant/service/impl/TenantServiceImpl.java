package com.cdqckj.gmis.tenant.service.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.tenant.dao.TenantMapper;
import com.cdqckj.gmis.tenant.dto.TenantSaveDTO;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.enumeration.TenantStatusEnum;
import com.cdqckj.gmis.tenant.enumeration.TenantTypeEnum;
import com.cdqckj.gmis.tenant.service.TenantService;
import com.cdqckj.gmis.tenant.strategy.InitSystemContext;
import com.cdqckj.gmis.base.service.SuperCacheServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.tenant.strategy.impl.DatasourceInitSystemStrategy;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.cdqckj.gmis.common.constant.CacheKey.TENANT;
import static com.cdqckj.gmis.utils.BizAssert.isFalse;

/**
 * <p>
 * 业务实现类
 * 企业
 * </p>
 *
 * @author gmis
 * @date 2019-10-24
 */
@Slf4j
@Service
@DS("master")
public class TenantServiceImpl extends SuperCacheServiceImpl<TenantMapper, Tenant> implements TenantService {

    @Autowired
    private InitSystemContext initSystemContext;

    @Autowired
    private RedisService redisService;

    @Autowired
    DatasourceInitSystemStrategy DATASOURCE;

    @Override
    protected String getRegion() {
        return TENANT;
    }

    @Override
    public Tenant getByUrl(String tenant) {
        return super.getOne(Wraps.<Tenant>lbQ().eq(Tenant::getUrl, tenant).eq(Tenant::getStatus,"NORMAL"));
    }

    @Override
    public Tenant getByCode(String tenant) {
        return super.getOne(Wraps.<Tenant>lbQ().eq(Tenant::getCode, tenant));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Tenant save(TenantSaveDTO data) {
        // defaults 库
        isFalse(check(data.getCode()),getLangMessage(MessageConstants.CODE_REPETITION));
        // 1， 保存租户 (默认库)
        if(super.count(Wraps.<Tenant>lbQ().eq(Tenant::getUrl, data.getUrl())) > 0){
            R.fail(getLangMessage(MessageConstants.CODE_REPETITION));
        }
        Tenant tenant = BeanPlusUtil.toBean(data, Tenant.class);
        tenant.setStatus(TenantStatusEnum.NORMAL);
        tenant.setType(TenantTypeEnum.CREATE);
        // defaults 库
        save(tenant);

        // 3, 初始化库，表, 数据  考虑异步完成 // 租户库
        initSystemContext.init(tenant.getCode());

        DATASOURCE.initDataSource();
        return tenant;
    }

    @Override
    public boolean check(String tenantCode) {
        return super.count(Wraps.<Tenant>lbQ().eq(Tenant::getCode, tenantCode)) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(List<Long> ids) {
        List<String> tenantCodeList = listObjs(Wraps.<Tenant>lbQ().select(Tenant::getCode).in(Tenant::getId, ids), Convert::toStr);
        if (tenantCodeList.isEmpty()) {
            return true;
        }
        removeByIds(ids);

        return initSystemContext.delete(tenantCodeList);
    }

    @Override
    public List<Tenant> getAllTenant() {
        return baseMapper.selectList(null);
    }

}
