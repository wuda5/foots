package com.cdqckj.gmis.readmeter.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.utils.ExecuteStatus;
import com.cdqckj.gmis.readmeter.ReadMeterPlanScopeApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopePageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopeSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanScopeUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlanScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class ReadMeterPlanScopeApiFallback implements ReadMeterPlanScopeApi {

    @Override
    public R<ReadMeterPlanScope> save(ReadMeterPlanScopeSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterPlanScope> saveList(List<ReadMeterPlanScopeSaveDTO> saveDTOList) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterPlanScope> update(ReadMeterPlanScopeUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterPlanScope> updateBatch(List<ReadMeterPlanScopeUpdateDTO> updateDTO) {
        return null;
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<ReadMeterPlanScope>> page(PageParams<ReadMeterPlanScopePageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterPlanScope>> queryList(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterPlanScope>> queryByBookIdAndPlan(Long id, List<Long> planIds) {
        return null;
    }

    @Override
    public R<List<ReadMeterPlanScope>> queryByBookId(List<Long> ids) {
        return null;
    }

    @Override
    public R<List<ReadMeterPlanScope>> query(ReadMeterPlanScope readMeterPlanScope) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterPlanScope>> updateByPlanId(Long planId, ExecuteStatus status) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterPlanScope>> queryByWrap(List<Long> list, Long bookId) {
        return null;
    }

    @Override
    public R<Boolean> deleteReadMeterPlanScope(List<Long> ids) {
        return null;
    }

    /*@Override
    public R<List<ReadMeterPlanScope>> queryByWrap(QueryWrapper<ReadMeterPlanScope> wrapper) {
        return R.timeout();
    }*/
}
