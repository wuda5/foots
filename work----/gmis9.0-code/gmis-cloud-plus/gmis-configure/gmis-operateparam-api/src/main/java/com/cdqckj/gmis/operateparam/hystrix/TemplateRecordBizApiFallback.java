package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.TemplateRecordBizApi;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordPageDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TemplateRecordUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.TemplateRecord;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class TemplateRecordBizApiFallback implements TemplateRecordBizApi {


    @Override
    public R<TemplateRecord> save(TemplateRecordSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<TemplateRecord> update(TemplateRecordUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<Page<TemplateRecord>> page(PageParams<TemplateRecordPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<TemplateRecord>> query(TemplateRecord params) {
        return R.timeout();
    }
}
