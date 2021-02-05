package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.InvoiceParamBizApi;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamPageDTO;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamSaveDTO;
import com.cdqckj.gmis.systemparam.dto.InvoiceParamUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.InvoiceParam;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class InvoiceParamBizApiFallback implements InvoiceParamBizApi {

    @Override
    public R<InvoiceParam> save(InvoiceParamSaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R<InvoiceParam> update(InvoiceParamUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<InvoiceParam>> page(PageParams<InvoiceParamPageDTO> params) {
        return R.timeout();
    }
}
