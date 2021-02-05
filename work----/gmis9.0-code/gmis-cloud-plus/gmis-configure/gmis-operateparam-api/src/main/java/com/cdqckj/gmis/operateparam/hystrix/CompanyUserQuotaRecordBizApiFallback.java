package com.cdqckj.gmis.operateparam.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.CompanyUserQuotaRecordBizApi;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordPageDTO;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.entity.CompanyUserQuotaRecord;
import com.cdqckj.gmis.operateparam.vo.BusinessHallVO;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

/**
 * @author 65427
 */
@Component
public class CompanyUserQuotaRecordBizApiFallback implements CompanyUserQuotaRecordBizApi {

    @Override
    public R<CompanyUserQuotaRecord> save(CompanyUserQuotaRecordSaveDTO saveDTO) {
        return R.timeout();
    }

    @Override
    public R<Page<CompanyUserQuotaRecord>> page(PageParams<CompanyUserQuotaRecordPageDTO> params) {
        return R.timeout();
    }

    @Override
    public R<Map<String, List<BusinessHallVO>>> getUserBusinessHall() {
        return R.timeout();
    }

    @Override
    public CompanyUserQuotaRecord queryRecentRecord() {
        return null;
    }

    @Override
    public R<List<CompanyUserQuotaRecord>> query(@RequestBody CompanyUserQuotaRecord queryInfo) {
        return R.timeout();
    }
}
