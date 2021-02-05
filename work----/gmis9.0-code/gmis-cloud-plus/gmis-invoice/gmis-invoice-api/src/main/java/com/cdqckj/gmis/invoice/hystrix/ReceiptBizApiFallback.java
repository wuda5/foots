package com.cdqckj.gmis.invoice.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.invoice.ReceiptBizApi;
import com.cdqckj.gmis.invoice.dto.ReceiptPageDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptSaveDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptUpdateDTO;
import com.cdqckj.gmis.invoice.entity.Receipt;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ReceiptBizApiFallback implements ReceiptBizApi {
    @Override
    public R<Receipt> save(@Valid ReceiptSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Receipt> update(ReceiptUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Receipt> get(Long id) {
        return R.timeout();
    }

    @Override
    public R<List<Receipt>> query(Receipt data) {
        return R.timeout();
    }

    @Override
    public R<Page<Receipt>> page(PageParams<ReceiptPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<Receipt>> queryReceipt(LocalDateTime startTime, LocalDateTime endTime) {
        return R.timeout();
    }
}
