package com.cdqckj.gmis.charges.api;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.ChargeInsuranceDetailPageDTO;
import com.cdqckj.gmis.charges.dto.ChargeInsuranceDetailSaveDTO;
import com.cdqckj.gmis.charges.dto.ChargeInsuranceDetailUpdateDTO;
import com.cdqckj.gmis.charges.entity.ChargeInsuranceDetail;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 缴费记录API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/chargeInsuranceDetail", qualifier = "ChargeInsuranceDetailBizApi")
public interface ChargeInsuranceDetailBizApi {
    /**
     * 保存缴费记录
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ChargeInsuranceDetail> save(@RequestBody ChargeInsuranceDetailSaveDTO saveDTO);

    /**
     * 更新缴费记录
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ChargeInsuranceDetail> update(@RequestBody ChargeInsuranceDetailUpdateDTO updateDTO);

    /**
     * 删除缴费记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询缴费记录
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<ChargeInsuranceDetail>> page(@RequestBody PageParams<ChargeInsuranceDetailPageDTO> params);

    /**
     * ID查询缴费记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<ChargeInsuranceDetail> get(@PathVariable("id") Long id);

    /**
     * 查询缴费记录
     * @param queryInfo
     * @return
     */
    @PostMapping(value = "/query")
    R<List<ChargeInsuranceDetail>> query(@RequestBody ChargeInsuranceDetail queryInfo);

    /**
     * @auth lijianguo
     * @date 2020/11/7 15:09
     * @remark 这个客户的素有的有效的保单的个数
     */
    @GetMapping("/customerInsureSum")
    R<Integer> customerInsureSum(@RequestParam("customerCode") String customerCode);
}
