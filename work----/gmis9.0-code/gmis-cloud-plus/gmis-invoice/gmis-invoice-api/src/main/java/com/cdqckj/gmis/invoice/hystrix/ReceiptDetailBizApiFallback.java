package com.cdqckj.gmis.invoice.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.invoice.ReceiptDetailBizApi;
import com.cdqckj.gmis.invoice.dto.ReceiptDetailPageDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptDetailSaveDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptDetailUpdateDTO;
import com.cdqckj.gmis.invoice.entity.ReceiptDetail;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class ReceiptDetailBizApiFallback implements ReceiptDetailBizApi {
    @Override
    public R<ReceiptDetail> save(@Valid ReceiptDetailSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<ReceiptDetail> update(ReceiptDetailUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<ReceiptDetail>> query(ReceiptDetail data) {
        return R.timeout();
    }

    @Override
    public R<IPage<ReceiptDetail>> page(PageParams<ReceiptDetailPageDTO> params) {
        return R.timeout();
    }
}
