package com.cdqckj.gmis.tenant.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.lang.L18nEnum;
import com.cdqckj.gmis.lang.L18nMenuContainer;
import com.cdqckj.gmis.tenant.entity.GlobalUser;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.tenant.dao.GlobalUserMapper;
import com.cdqckj.gmis.tenant.dto.GlobalUserSaveDTO;
import com.cdqckj.gmis.tenant.dto.GlobalUserUpdateDTO;
import com.cdqckj.gmis.tenant.service.GlobalUserService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import com.cdqckj.gmis.utils.StrHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.cdqckj.gmis.utils.BizAssert.isFalse;

/**
 * <p>
 * 业务实现类
 * 全局账号
 * </p>
 *
 * @author gmis
 * @date 2019-10-25
 */
@Slf4j
@Service
@DS("master")
public class GlobalUserServiceImpl extends SuperServiceImpl<GlobalUserMapper, GlobalUser> implements GlobalUserService {

    @Autowired
    private RedisService redisService;

    @Override
    public Boolean check(String account) {
        return super.count(Wraps.<GlobalUser>lbQ()
                .eq(GlobalUser::getAccount, account)) > 0;
    }

    /**
     * @param data
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GlobalUser save(GlobalUserSaveDTO data) {

        BizAssert.equals(data.getPassword(), data.getConfirmPassword(),
                redisService.getLangMessage(MessageConstants.USER_VERIFY_UPDATE_PASSWORD));
        isFalse(check(data.getAccount()),
                redisService.getLangMessage(MessageConstants.USER_VERIFY_NAME_EXIST));

        String md5Password = SecureUtil.md5(data.getPassword());

        GlobalUser globalAccount = BeanPlusUtil.toBean(data, GlobalUser.class);
        // 全局表就不存用户数据了
        globalAccount.setPassword(md5Password);
        globalAccount.setName(StrHelper.getOrDef(data.getName(), data.getAccount()));
        globalAccount.setReadonly(false);

        save(globalAccount);
        return globalAccount;
    }

    /**
     * @param data
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public GlobalUser update(GlobalUserUpdateDTO data) {
        if (StrUtil.isNotBlank(data.getPassword()) || StrUtil.isNotBlank(data.getPassword())) {
            BizAssert.equals(data.getPassword(), data.getConfirmPassword(),
                    redisService.getLangMessage(MessageConstants.USER_VERIFY_UPDATE_PASSWORD));
        }

        GlobalUser globalUser = BeanPlusUtil.toBean(data, GlobalUser.class);
        if (StrUtil.isNotBlank(data.getPassword())) {
            String md5Password = SecureUtil.md5(data.getPassword());
            globalUser.setPassword(md5Password);

        }
        updateById(globalUser);
        return globalUser;
    }
}
