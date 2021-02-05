package com.cdqckj.gmis.invoice.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.invoice.SellerTaxpayerInfoBizApi;
import com.cdqckj.gmis.invoice.dto.SellerTaxpayerInfoPageDTO;
import com.cdqckj.gmis.invoice.dto.SellerTaxpayerInfoSaveDTO;
import com.cdqckj.gmis.invoice.dto.SellerTaxpayerInfoUpdateDTO;
import com.cdqckj.gmis.invoice.entity.SellerTaxpayerInfo;
import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class SellerTaxpayerInfoBizApiFallback implements SellerTaxpayerInfoBizApi {
    @Override
    public R<SellerTaxpayerInfo> save(@Valid SellerTaxpayerInfoSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<SellerTaxpayerInfo> update(SellerTaxpayerInfoUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<List<SellerTaxpayerInfo>> query(SellerTaxpayerInfo data) {
        return R.timeout();
    }

    @Override
    public R<IPage<SellerTaxpayerInfo>> page(PageParams<SellerTaxpayerInfoPageDTO> params) {
        return R.timeout();
    }
}
