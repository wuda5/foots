package com.cdqckj.gmis.invoice.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.invoice.BuyerTaxpayerInfoBizApi;
import com.cdqckj.gmis.invoice.dto.BuyerTaxpayerInfoPageDTO;
import com.cdqckj.gmis.invoice.dto.BuyerTaxpayerInfoSaveDTO;
import com.cdqckj.gmis.invoice.dto.BuyerTaxpayerInfoUpdateDTO;
import com.cdqckj.gmis.invoice.entity.BuyerTaxpayerInfo;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class BuyerTaxpayerInfoBizApiFallback implements BuyerTaxpayerInfoBizApi {
    @Override
    public R<BuyerTaxpayerInfo> save(@Valid BuyerTaxpayerInfoSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<BuyerTaxpayerInfo> update(BuyerTaxpayerInfoUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<BuyerTaxpayerInfo>> query(BuyerTaxpayerInfo data) {
        return R.timeout();
    }

    @Override
    public R<Page<BuyerTaxpayerInfo>> page(PageParams<BuyerTaxpayerInfoPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<BuyerTaxpayerInfo> queryBuyerTaxpayerInfo(BuyerTaxpayerInfo data) {
        return R.timeout();
    }
}
