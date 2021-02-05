package com.cdqckj.gmis.operateparam;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordPageDTO;
import com.cdqckj.gmis.operateparam.dto.CompanyUserQuotaRecordSaveDTO;
import com.cdqckj.gmis.operateparam.entity.CompanyUserQuotaRecord;
import com.cdqckj.gmis.operateparam.hystrix.CompanyUserQuotaRecordBizApiFallback;
import com.cdqckj.gmis.operateparam.vo.BusinessHallVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.operateparam-server:gmis-operateparam-server}", fallback = CompanyUserQuotaRecordBizApiFallback.class
        , path = "/companyUserQuotaRecord", qualifier = "companyUserQuotaRecordBizApi")
public interface CompanyUserQuotaRecordBizApi {
    /**
     * 保存用户配额记录信息
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<CompanyUserQuotaRecord> save(@RequestBody CompanyUserQuotaRecordSaveDTO saveDTO);

    /**
     * 分页查询用户配额记录信息
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<CompanyUserQuotaRecord>> page(@RequestBody PageParams<CompanyUserQuotaRecordPageDTO> params);

    /**
     * 查询营业厅用户
      * @return
     */
    @GetMapping(value = "/findBusinessHallUser")
    R<Map<String, List<BusinessHallVO>>> getUserBusinessHall();

    /**
     * 查询最近一条营业员配额记录
      * @return
     */
    @GetMapping(value = "/queryRecentRecord")
    CompanyUserQuotaRecord queryRecentRecord();

    /**
     * 查询
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<CompanyUserQuotaRecord>> query(@RequestBody CompanyUserQuotaRecord queryInfo);
}
