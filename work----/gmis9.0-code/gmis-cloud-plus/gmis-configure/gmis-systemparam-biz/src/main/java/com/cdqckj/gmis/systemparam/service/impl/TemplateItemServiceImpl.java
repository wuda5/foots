package com.cdqckj.gmis.systemparam.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.systemparam.dao.TemplateItemMapper;
import com.cdqckj.gmis.systemparam.dto.TemplateItemPageDTO;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import com.cdqckj.gmis.systemparam.enumeration.AuditEnum;
import com.cdqckj.gmis.systemparam.service.TemplateItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 模板项目表（Item）
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class TemplateItemServiceImpl extends SuperServiceImpl<TemplateItemMapper, TemplateItem> implements TemplateItemService {

    @DS("master")
    @Override
    public R<TemplateItem> share(TemplateItem model) {
        //save(model);
        saveOrUpdate(model);
        return R.success(model);
    }

    @DS("master")
    @Override
    public R<Boolean> adminExamine(List<TemplateItem> list) {
        Boolean bool = updateBatchById(list);
        return bool?R.success():R.fail("");
    }

    @DS("#thread.tenant")
    @Override
    public R<Boolean> publish(List<TemplateItem> list) {
        return R.success(saveOrUpdateBatch(list));
    }

    @DS("master")
    @Override
    public R<IPage<TemplateItem>> pageAdminTemplate(PageParams<TemplateItemPageDTO> params) {
        TemplateItem model = BeanUtil.toBean(params.getModel(), TemplateItem.class);
        /*QueryWrap<TemplateItem> wrapper = Wraps.q(model);
        wrapper.eq("data_status", AuditEnum.APPROVED.getStatus());*/
        LbqWrapper wrapper = Wraps.<TemplateItem>lbQ().eq(TemplateItem::getDataStatus, AuditEnum.APPROVED.getStatus())
                .eq(TemplateItem::getTemplateCode,model.getTemplateCode());
        return R.success(page(params.getPage(),wrapper));
    }
}
