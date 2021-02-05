package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.BatcgDetailedAddressBizApi;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressPageDTO;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressSaveDTO;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.BatchDetailedAddress;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchDetailedAddressBizApiFallback implements BatcgDetailedAddressBizApi {
    @Override
    public R<BatchDetailedAddress> saveList(List<BatchDetailedAddressSaveDTO> list) {
        return R.timeout();
    }

    @Override
    public R<List<BatchDetailedAddress>> query(BatchDetailedAddress data) {
        return R.timeout();
    }

    @Override
    public R<BatchDetailedAddress> save(BatchDetailedAddressSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<BatchDetailedAddress> update(BatchDetailedAddressUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Page<BatchDetailedAddress>> page(PageParams<BatchDetailedAddressPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<Boolean> check(BatchDetailedAddressUpdateDTO detailedAddressUpdateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }
}
