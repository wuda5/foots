package com.cdqckj.gmis.device.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.GasMeterModelBizApi;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterModelUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterModel;
import org.springframework.stereotype.Component;

import java.util.List;

/**User
 * @author 65427
 */
@Component
public class GasMeterModelBizApiFallback implements GasMeterModelBizApi {


    @Override
    public R<GasMeterModel> save(GasMeterModelSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<GasMeterModel> update(GasMeterModelUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBatchById(List<GasMeterModelUpdateDTO> updateBatch) { return R.timeout(); }

    @Override
    public R<Page<GasMeterModel>> page(PageParams<GasMeterModelPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<GasMeterModel>> query(GasMeterModel queryDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> queryGasMeter(GasMeterModel queryGasMeter) {
        return R.timeout();
    }

    @Override
    public R<GasMeterModel> get(Long id) {
        return R.timeout();
    }

    @Override
    public R<GasMeterModel> queryModel(GasMeterModel gasMeterModel) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateModelStatusByFactoryIds(List<Long> factoryIds, int setVersionState) {
        return  R.timeout();
    }
}
