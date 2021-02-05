package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.BusinessHallQuotaRecordPageDTO;
import com.cdqckj.gmis.operateparam.dto.BusinessHallQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.entity.BusinessHallQuotaRecord;
import com.cdqckj.gmis.operateparam.hystrix.BusinessHallQuotaRecordBizApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = BusinessHallQuotaRecordBizApiFallback.class
        , path = "/businessHallQuotaRecord", qualifier = "businessHallQuotaRecordBizApi")
public interface BusinessHallQuotaRecordBizApi {

    /**
     * 保存营业厅配额记录信息
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<BusinessHallQuotaRecord> save(@RequestBody BusinessHallQuotaRecordSaveDTO saveDTO);

    /**
     * 分页查询营业厅配额记录信息
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<BusinessHallQuotaRecord>> page(@RequestBody PageParams<BusinessHallQuotaRecordPageDTO> params);
}
