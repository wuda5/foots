package com.cdqckj.gmis.readmeter.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.readmeter.ReadMeterPlanApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterPlanUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterPlan;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class ReadMeterPlanApiFallback implements ReadMeterPlanApi {

    @Override
    public R<ReadMeterPlan> save(ReadMeterPlanSaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R<ReadMeterPlan> update(ReadMeterPlanUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterPlan> updateBatch(List<ReadMeterPlanUpdateDTO> updateDTO) {
        return null;
    }

    @Override
    public R<Boolean> logicalDelete(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<Page<ReadMeterPlan>> page(PageParams<ReadMeterPlanPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterPlan>> queryList(List<Long> ids) {
        return R.timeout();
    }

    @Override
    public R<ReadMeterPlan> getById(Long id) {
        return R.timeout();
    }

    @Override
    public R<List<ReadMeterPlan>> query(ReadMeterPlan readMeterPlan) {
        return R.timeout();
    }

    @Override
    public R<Page<ReadMeterPlan>> planPageByReadmeterUser(PageParams<Long> params) {
        return R.timeout();
    }
}
