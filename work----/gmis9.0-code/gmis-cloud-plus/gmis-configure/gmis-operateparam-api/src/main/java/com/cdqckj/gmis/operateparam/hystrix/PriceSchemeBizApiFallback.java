package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.PriceSchemeBizApi;
import com.cdqckj.gmis.operateparam.dto.PriceSchemePageDTO;
import com.cdqckj.gmis.operateparam.dto.PriceSchemeSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PriceSchemeUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

/**
 * @author 65427
 */
@Component
public abstract class PriceSchemeBizApiFallback implements PriceSchemeBizApi {

    @Override
    public R<PriceScheme> save(PriceSchemeSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<PriceScheme> update(PriceSchemeUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<PriceScheme> queryById(Long id) {
        return R.timeout();
    }

    @Override
    public PriceScheme queryRecentRecord(@RequestParam("useGasTypeId") Long useGasTypeId) {
        return null;
    }

    @Override
    public PriceScheme queryRecentRecordByTime(Long useGasTypeId, LocalDate activateDate) {
        return null;
    }

    @Override
    public PriceScheme queryRecentHeatingRecord(Long useGasTypeId) {
        return null;
    }

    @Override
    public int updatePriceStatus() {
        return 0;
    }

    @Override
    public R<Page<PriceScheme>> page(PageParams<PriceSchemePageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<PriceScheme>> queryAllPriceScheme() {
        return R.timeout();
    }

    @Override
    public R<Integer> updateByPriceId(PriceScheme priceScheme) {
        return R.timeout();
    }

    @Override
    public List<PriceScheme> queryRecord(Long useGasTypeId) {
        return null;
    }

    @Override
    public R<PriceScheme> queryOne(PriceScheme data) {
        return R.timeout();
    }

    @Override
    public PriceScheme queryPriceByTime(Long useGasTypeId, LocalDate activateDate) {
        return null;
    }

    @Override
    public PriceScheme queryPriceHeatingByTime(Long useGasTypeId, LocalDate activateDate) {
        return null;
    }

    @Override
    public R<List<PriceScheme>> query(PriceScheme data) {
        return R.timeout();
    }
}
