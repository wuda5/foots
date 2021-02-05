package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.ChargeRecordPayPageDTO;
import com.cdqckj.gmis.charges.dto.ChargeRecordPaySaveDTO;
import com.cdqckj.gmis.charges.dto.ChargeRecordPayUpdateDTO;
import com.cdqckj.gmis.charges.entity.ChargeRecordPay;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 三方支付记录API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/chargeRecordPay", qualifier = "ChargeRecordPayBizApi")
public interface ChargeRecordPayBizApi  {
    /**
     * 保存第三方支付记录
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<ChargeRecordPay> save(@RequestBody ChargeRecordPaySaveDTO saveDTO);

    /**
     * 更新第三方支付记录
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<ChargeRecordPay> update(@RequestBody ChargeRecordPayUpdateDTO updateDTO);

    /**
     * 删除第三方支付记录
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询第三方支付记录
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<ChargeRecordPay>> page(@RequestBody PageParams<ChargeRecordPayPageDTO> params);

    /**
     * ID查询第三方支付记录
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<ChargeRecordPay> get(@PathVariable("id") Long id);
}
