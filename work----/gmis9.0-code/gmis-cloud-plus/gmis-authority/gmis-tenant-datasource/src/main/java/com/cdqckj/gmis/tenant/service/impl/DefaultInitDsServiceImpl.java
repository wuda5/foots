package com.cdqckj.gmis.tenant.service.impl;

import com.cdqckj.gmis.tenant.api.FileDsApi;
import com.cdqckj.gmis.tenant.api.OauthDsApi;
import com.cdqckj.gmis.tenant.service.DataSourceService;
import com.cdqckj.gmis.tenant.service.InitDsService;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.mq.properties.MqProperties;
import com.cdqckj.gmis.tenant.api.MsgsDsApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 没有开启消息队列就只能轮训了
 *
 * @author gmis
 * @date 2020年04月05日16:27:03
 */
@Service
@Slf4j
@ConditionalOnProperty(prefix = MqProperties.PREFIX, name = "enabled", havingValue = "false", matchIfMissing = true)
public class DefaultInitDsServiceImpl implements InitDsService {

    @Autowired
    private DataSourceService dataSourceService;

    @Autowired
    private OauthDsApi oauthDsApi;
    @Autowired
    private FileDsApi fileDsApi;
    @Autowired
    private MsgsDsApi msgsDsApi;

    @Override
    public boolean initDataSource(String tenant) {
        // 权限服务
        dataSourceService.initDataSource(tenant);

        // SpringBoot项目就不需要调用以下方法了
        // oauth
        R<Boolean> oauth = oauthDsApi.init(tenant);
        // file
        R<Boolean> file = fileDsApi.init(tenant);
        // msgs
        R<Boolean> msgs = msgsDsApi.init(tenant);
        log.info("oauth={}, file={}, msgs={}", oauth, file, msgs);
        return true;
    }

    @Override
    public boolean removeDataSource(String tenant) {
        // 权限服务
        dataSourceService.remove(tenant);

        // SpringBoot项目就不需要调用以下方法了
        // oauth
        R<Object> oauth = oauthDsApi.remove(tenant);
        // file
        R<Object> file = fileDsApi.remove(tenant);
        // msgs
        R<Object> msgs = msgsDsApi.remove(tenant);
        log.info("oauth={}, file={}, msgs={}", oauth, file, msgs);
        return true;
    }
}
