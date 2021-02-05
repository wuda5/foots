package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.PenaltyBizApi;
import com.cdqckj.gmis.operateparam.dto.PenaltyPageDTO;
import com.cdqckj.gmis.operateparam.dto.PenaltySaveDTO;
import com.cdqckj.gmis.operateparam.dto.PenaltyUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Penalty;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class PenaltyBizApiFallback implements PenaltyBizApi {

    @Override
    public R<Penalty> save(PenaltySaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Penalty> update(PenaltyUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Penalty> queryById(Long id) {
        return R.timeout();
    }

    @Override
    public Penalty queryRecentRecord() {
        return null;
    }

    @Override
    public R<Page<Penalty>> page(PageParams<PenaltyPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<Penalty>> query(Penalty data) {
        return R.timeout();
    }

    @Override
    public R<String> check(PenaltyUpdateDTO penaltyUpdateDTO) {
        return R.timeout();
    }


    @Override
    public R<Penalty> getById(Long id) {
        return R.timeout();
    }

    @Override
    public R<Penalty> queryByUseGasId(Long useGasId) {
        return R.timeout();
    }


}
