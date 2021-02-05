package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.GiveReductionConfBizApi;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfPageDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfSaveDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionQueryDTO;
import com.cdqckj.gmis.operateparam.entity.GiveReductionConf;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class GiveReductionConfBizApiFallback implements GiveReductionConfBizApi {


    @Override
    public R<GiveReductionConf> save(GiveReductionConfSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<GiveReductionConf> update(GiveReductionConfUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBatchById(List<GiveReductionConfUpdateDTO> updateBatch) {
        return R.timeout();
    }

    @Override
    public R<Page<GiveReductionConf>> page(PageParams<GiveReductionConfPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<GiveReductionConf>> query(GiveReductionConf queryDTO) {
        return R.timeout();
    }

    @Override
    public R<List<GiveReductionConf>> queryEffectiveGiveReduction(GiveReductionQueryDTO queryEffectiveActive) {
        return R.timeout();
    }
}
