package com.cdqckj.gmis.archive.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.MeterStockBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.devicearchive.dto.MeterStockPageDTO;
import com.cdqckj.gmis.devicearchive.dto.MeterStockSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.MeterStockUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.MeterStock;
import com.cdqckj.gmis.userarchive.dto.CustomerPageDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerSaveDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerUpdateDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class MeterStockBizApiFallback implements MeterStockBizApi {

    @Override
    public R<MeterStock> save(MeterStockSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<MeterStock> update(MeterStockUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<Page<MeterStock>> page(PageParams<MeterStockPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<MeterStock> saveList(List<MeterStockSaveDTO> list) {
        return R.timeout();
    }
}
