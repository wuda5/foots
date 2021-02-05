package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.ExceptionRuleBizApi;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfPageDTO;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfSaveDTO;
import com.cdqckj.gmis.operateparam.dto.ExceptionRuleConfUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.ExceptionRuleConf;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class ExceptionRuleConfBizApiFallback implements ExceptionRuleBizApi {

    @Override
    public R<ExceptionRuleConf> save(ExceptionRuleConfSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<ExceptionRuleConf> update(ExceptionRuleConfUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBatchById(List<ExceptionRuleConfUpdateDTO> updateBatch) {
        return R.timeout();
    }

    @Override
    public R<Boolean> delete(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<Page<ExceptionRuleConf>> page(PageParams<ExceptionRuleConfPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<ExceptionRuleConf>> query(ExceptionRuleConf queryDTO) {
        return R.timeout();
    }

    @Override
    public R<List<ExceptionRuleConf>> queryByGasTypeId(List<Long> typeIds) {
        return R.timeout();
    }
}
