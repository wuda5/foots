package com.cdqckj.gmis.archive.hystrix;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.archive.BatchGasBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.devicearchive.dto.BatchGasPageDTO;
import com.cdqckj.gmis.devicearchive.dto.BatchGasSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.BatchGasUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.BatchGas;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BatchGasBizApifallback implements BatchGasBizApi {
    @Override
    public R<BatchGas> saveBatchGas(BatchGasSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<BatchGas> updateBatchGas(BatchGasUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Boolean> deleteBatchGas(List<Long> id) {
        return R.timeout();
    }

    @Override
    public R<IPage<BatchGas>> page(PageParams<BatchGasPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<BatchGas> saveList(List<BatchGasSaveDTO> list) {
        return R.timeout();
    }
}
