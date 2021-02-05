package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.BusinessHallQuotaRecordBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.operateparam.dto.*;
import com.cdqckj.gmis.operateparam.entity.BusinessHallQuotaRecord;
import com.cdqckj.gmis.operateparam.entity.UseGasType;
import org.springframework.stereotype.Component;

/**
 * @author 65427
 */
@Component
public class BusinessHallQuotaRecordBizApiFallback implements BusinessHallQuotaRecordBizApi {

    @Override
    public R<BusinessHallQuotaRecord> save(BusinessHallQuotaRecordSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Page<BusinessHallQuotaRecord>> page(PageParams<BusinessHallQuotaRecordPageDTO> params) {
        return R.timeout();
    }
}
