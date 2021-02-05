package com.cdqckj.gmis.operation.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operation.dto.OperationRecordPageDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OperationRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OperationRecord;
import com.cdqckj.gmis.operation.OperationRecordBizApi;
import org.springframework.stereotype.Component;

@Component
public class OperationRecordBizApiFallBack implements OperationRecordBizApi {
    @Override
    public R<Page<OperationRecord>> page(PageParams<OperationRecordPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<OperationRecord> save(OperationRecordSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<OperationRecord> update(OperationRecordUpdateDTO updateDTO) {
        return R.timeout();
    }
}
