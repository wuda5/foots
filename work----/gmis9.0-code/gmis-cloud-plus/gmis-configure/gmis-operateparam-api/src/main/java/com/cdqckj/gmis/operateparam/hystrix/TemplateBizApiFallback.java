package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.TemplateBizApi;
import com.cdqckj.gmis.systemparam.dto.TemplatePageDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.Template;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class TemplateBizApiFallback implements TemplateBizApi {

    @Override
    public R<Template> saveTemplate(TemplateSaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R<Template> update(TemplateUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<Template>> page(PageParams<TemplatePageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<Template>> query(Template params) {
        return null;
    }

    @Override
    public R<Template> getById(Long id) {
        return null;
    }
}
