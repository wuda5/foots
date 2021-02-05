package com.cdqckj.gmis.invoice.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.invoice.ReceiptInvoiceAssociationBizApi;
import com.cdqckj.gmis.invoice.dto.ReceiptInvoiceAssociationPageDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptInvoiceAssociationSaveDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptInvoiceAssociationUpdateDTO;
import com.cdqckj.gmis.invoice.entity.ReceiptInvoiceAssociation;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class ReceiptInvoiceAssociationBizApiFallback implements ReceiptInvoiceAssociationBizApi {
    @Override
    public R<ReceiptInvoiceAssociation> save(@Valid ReceiptInvoiceAssociationSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<ReceiptInvoiceAssociation> update(ReceiptInvoiceAssociationUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<ReceiptInvoiceAssociation>> query(ReceiptInvoiceAssociation data) {
        return R.timeout();
    }

    @Override
    public R<IPage<ReceiptInvoiceAssociation>> page(PageParams<ReceiptInvoiceAssociationPageDTO> params) {
        return R.timeout();
    }
}
