package com.cdqckj.gmis.archive;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.hystrix.PurchaseLimitCustomerBizApiFallback;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.dto.PurchaseLimitCustomerSaveDTO;
import com.cdqckj.gmis.userarchive.entity.PurchaseLimitCustomer;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceLimitVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户气表信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallback = PurchaseLimitCustomerBizApiFallback.class
        , path = "/purchaseLimitCustomer", qualifier = "PurchaseLimitCustomerBizApi")
public interface PurchaseLimitCustomerBizApi {

    @PostMapping(value = "/saveList")
    R<PurchaseLimitCustomer> saveList(@RequestBody List<PurchaseLimitCustomerSaveDTO> list);

    /**
     * 批量删除限购客户
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 客户气表限购关联列表
     * @return
     */
    @PostMapping("/limitList")
    R<Page<CustomerDeviceDTO>> findLimitCustomerPage(@RequestBody CustomerDeviceLimitVO params);

    @PostMapping("/selectByIds")
    List<PurchaseLimitCustomer> selectByIds(@RequestBody List<Long> limitIds);

    @PostMapping("/query")
    R<List<PurchaseLimitCustomer>> query(@RequestBody PurchaseLimitCustomer gasMeter);
}
