package com.cdqckj.gmis.invoice.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.invoice.InvoiceDetailsBizApi;
import com.cdqckj.gmis.invoice.dto.InvoiceDetailsPageDTO;
import com.cdqckj.gmis.invoice.dto.InvoiceDetailsSaveDTO;
import com.cdqckj.gmis.invoice.dto.InvoiceDetailsUpdateDTO;
import com.cdqckj.gmis.invoice.entity.InvoiceDetails;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class InvoiceDetailsBizApiFallback implements InvoiceDetailsBizApi {
    @Override
    public R<InvoiceDetails> save(@Valid InvoiceDetailsSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<InvoiceDetails> update(InvoiceDetailsUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<InvoiceDetails>> query(InvoiceDetails data) {
        return R.timeout();
    }

    @Override
    public R<IPage<InvoiceDetails>> page(PageParams<InvoiceDetailsPageDTO> params) {
        return R.timeout();
    }
}
