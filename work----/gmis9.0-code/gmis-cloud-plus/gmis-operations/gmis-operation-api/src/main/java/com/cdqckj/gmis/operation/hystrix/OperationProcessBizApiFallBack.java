package com.cdqckj.gmis.operation.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operation.dto.OperationProcessPageDTO;
import com.cdqckj.gmis.operation.dto.OperationProcessSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationProcessUpdateDTO;
import com.cdqckj.gmis.operation.entity.OperationProcess;
import com.cdqckj.gmis.operation.OperationProcessBizApi;
import org.springframework.stereotype.Component;

@Component
public class OperationProcessBizApiFallBack implements OperationProcessBizApi {
    @Override
    public R<OperationProcess> save(OperationProcessSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<OperationProcess> update(OperationProcessUpdateDTO updateDTO) {
        return R.timeout();
    }

    @Override
    public R<Page<OperationProcess>> page(PageParams<OperationProcessPageDTO> params) {
        return R.timeout();
    }
}
