package com.cdqckj.gmis.appmanager.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.appmanager.AppmanagerApi;
import com.cdqckj.gmis.appmanager.dto.AppSettingDTO;
import com.cdqckj.gmis.appmanager.dto.AppmanagerPageDTO;
import com.cdqckj.gmis.appmanager.dto.AppmanagerSaveDTO;
import com.cdqckj.gmis.appmanager.dto.AppmanagerUpdateDTO;
import com.cdqckj.gmis.appmanager.entity.Appmanager;
import com.cdqckj.gmis.appmanager.service.IAppManageService;
import com.cdqckj.gmis.authority.api.TenantApi;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.constants.AppConfConstants;
import com.cdqckj.gmis.jwt.utils.JwtUtil;
import com.cdqckj.gmis.tenant.entity.Tenant;
import com.cdqckj.gmis.tenant.enumeration.TenantStatusEnum;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import com.cdqckj.gmis.utils.I18nUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;

@Service
public class AppManageService implements IAppManageService {

    @Autowired
    private AppmanagerApi appmanagerApi;

    @Autowired
    private TenantApi tenantApi;

    @Autowired
    private I18nUtil i18nUtil;

    @Override
    public R<Appmanager> addApplication(AppmanagerSaveDTO appmanagerSaveDTO) {
        //租户有效性校验 有tenantCode
        if(appmanagerSaveDTO.getTenantId()!=null
                ||StringUtils.isNotBlank(appmanagerSaveDTO.getTenantCode())){
            String tenantCode = JwtUtil.base64Decoder(appmanagerSaveDTO.getTenantCode());

            Tenant queryData = new Tenant();
            if(appmanagerSaveDTO.getTenantId()!=null) {
                queryData.setId(appmanagerSaveDTO.getTenantId());
            }
            if(StringUtils.isNotBlank(appmanagerSaveDTO.getTenantCode())) {
                queryData.setCode(tenantCode);
            }
            queryData.setStatus(TenantStatusEnum.NORMAL);
            R<List<Tenant>> listR = tenantApi.query(queryData);
            if(listR.getIsSuccess()){
                if(CollectionUtils.isEmpty(listR.getData())){
                   return R.fail("当前租户不存在或以禁用");
                }

                Tenant tenant = listR.getData().get(0);
                //租户时效性校验
                if (tenant.getExpirationTime() != null) {
                    BizAssert.gt(LocalDateTime.now(), tenant.getExpirationTime(),
                            i18nUtil.getMessage(MessageConstants.SYS_VALID_TENANT_TIME,"租户已过时效"));
                }

                appmanagerSaveDTO.setTenantId(tenant.getId());
                appmanagerSaveDTO.setTenantCode(tenant.getCode());
                appmanagerSaveDTO.setTenantName(tenant.getName());

            }else{
               return R.fail(listR.getDebugMsg());
            }

        }else{ //无法精准定位租户的情况 设为default
            appmanagerSaveDTO.setTenantId(0L);
            appmanagerSaveDTO.setTenantName("秦川物联网科技股份有限公司");
            appmanagerSaveDTO.setTenantCode("default");
        }

        //拷贝对象
        Appmanager appmanager = BeanPlusUtil.copyProperties(appmanagerSaveDTO, Appmanager.class);
        //AppID用UUID生成
        appmanager.setAppId(UUID.randomUUID().toString());

        return appmanagerApi.save(appmanager);
    }

    @Override
    public R<Page<Appmanager>> queryList(PageParams<AppmanagerPageDTO> params) {
        return appmanagerApi.page(params);
    }

    @Override
    public R<Appmanager> update(AppSettingDTO settingDTO) {
        //获取 未认证的应用不能编辑
        R<Appmanager> appmanagerR = appmanagerApi.get(settingDTO.getId());
        if(appmanagerR.getIsSuccess() && null!=appmanagerR.getData()){
            Appmanager rData = appmanagerR.getData();
            if(StringUtils.isEmpty(rData.getAppSecret())){
               return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_EFFECTIVE,"应用未生效"));
            }
        }else {
            return R.fail(appmanagerR.getDebugMsg());
        }

        AppmanagerUpdateDTO updateDTO = BeanPlusUtil.copyProperties(settingDTO,AppmanagerUpdateDTO.class);
        return appmanagerApi.update(updateDTO);
    }

    @Override
    public R<Appmanager> buildScrect(Long id) {

         R<Appmanager> appmanager = appmanagerApi.get(id);
        if(appmanager.getIsSuccess()){
            Appmanager data = appmanager.getData();
            if(null!=data) {
                String secretStr = "";
                if (StringUtils.isNotBlank(data.getAppSecret())) {
                    secretStr = data.getAppSecret();
                } else {
                    secretStr = buildScrect(data.getTenantCode(), data.getAppType().getCode());
                }
                AppmanagerUpdateDTO dto = new AppmanagerUpdateDTO();
                dto.setId(data.getId());
                dto.setAppSecret(secretStr);
                //设置生效时间为当前
                dto.setValidTimeStart(LocalDateTime.now());
                //设置每分钟访问次数
                dto.setVisitsTimes(AppConfConstants.VISIT_TIMES);
                //设置访问时间间隔
                dto.setVisitsInterval(AppConfConstants.VISTST_TERVAL);
                return appmanagerApi.update(dto);
            }else{
                return R.fail(i18nUtil.getMessage(MessageConstants.SYS_VALID_APP_NOT,"当前应用不存在"));
            }
        }else{
            return R.fail(appmanager.getDebugMsg());
        }
    }

    @Override
    public R<Appmanager> detail(Long id) {
        return appmanagerApi.get(id);
    }

    @Override
    public R<Appmanager> changeStatus(Long id, Boolean item) {
        AppmanagerUpdateDTO appmanager = new AppmanagerUpdateDTO();
        appmanager.setId(id);
        appmanager.setStatus(item);

        return appmanagerApi.update(appmanager);
    }

    /**
     * 生成应用密匙：MD5(租户Code+应用类型+时间搓)
     * @param tenantCode
     * @param appType
     * @return
     */
    private String buildScrect(String tenantCode,String appType){
        Long second = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
        String key = appType+tenantCode+second.toString();
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }
}
