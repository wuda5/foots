package com.cdqckj.gmis.operation.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operation.dto.OperationAcceptancePageDTO;
import com.cdqckj.gmis.operation.dto.OperationAcceptanceSaveDTO;
import com.cdqckj.gmis.operation.entity.OperationAcceptance;
import com.cdqckj.gmis.operation.OperationAcceptanceBizApi;
import org.springframework.stereotype.Component;

@Component
public class OperationAcceptanceBizApiFallBack implements OperationAcceptanceBizApi {
    @Override
    public R<Page<OperationAcceptance>> page(PageParams<OperationAcceptancePageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<OperationAcceptance> save(OperationAcceptanceSaveDTO saveDTO) {
        return R.timeout();
    }
}
