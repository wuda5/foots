package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.TemplateItemBizApi;
import com.cdqckj.gmis.systemparam.dto.TemplateItemPageDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateItemSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateItemUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class TemplateItemBizApiFallback implements TemplateItemBizApi {

    @Override
    public R<TemplateItem> save(TemplateItemSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<TemplateItem> share(TemplateItem saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> adminExamine(List<TemplateItem> list) {
        return R.timeout();
    }

    @Override
    public R<Boolean> publish(List<TemplateItem> list) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateByCode(List<TemplateItem> list, String code) {
        return R.timeout();
    }

    @Override
    public R<TemplateItem> update(TemplateItemUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBatch(List<TemplateItemUpdateDTO> list) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<Page<TemplateItem>> page(PageParams<TemplateItemPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<Page<TemplateItem>> pageAdminTemplate(PageParams<TemplateItemPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<TemplateItem>> query(TemplateItem params) {
        return R.timeout();
    }

    @Override
    public R<List<TemplateItem>> queryList(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<TemplateItem> getById(Long id) {
        return R.timeout();
    }

    @Override
    public R<String> test(String path) {
        return R.timeout();
    }
}
