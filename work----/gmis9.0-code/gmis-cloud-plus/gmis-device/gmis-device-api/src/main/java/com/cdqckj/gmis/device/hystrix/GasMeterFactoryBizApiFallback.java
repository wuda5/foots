package com.cdqckj.gmis.device.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.device.GasMeterFactoryBizApi;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactorySaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterFactoryUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 65427
 */
@Component
public class GasMeterFactoryBizApiFallback implements GasMeterFactoryBizApi {

    @Override
    public R<GasMeterFactory> save(GasMeterFactorySaveDTO saveDTO) {
        return R.timeout();
    }


    @Override
    public R<GasMeterFactory> update(GasMeterFactoryUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> updateBatchById(List<GasMeterFactoryUpdateDTO> updateBatch) {
        return R.timeout();
    }

    @Override
    public R<Page<GasMeterFactory>> page(PageParams<GasMeterFactoryPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<List<GasMeterFactory>> query(GasMeterFactory data) {
        return R.timeout();
    }

    @Override
    public R<Boolean> queryGasMeter(GasMeterFactory queryGasMeter) {
        return R.timeout();
    }

    @Override
    public R<GasMeterFactory> get(Long id) {
        return R.timeout();
    }

    @Override
    public R<GasMeterFactory> queryFactoryByName(GasMeterFactory gasMeterFactory) {
        return R.timeout();
    }

}
