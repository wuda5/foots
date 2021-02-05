package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.CustomerAccountSerialPageDTO;
import com.cdqckj.gmis.charges.dto.CustomerAccountSerialSaveDTO;
import com.cdqckj.gmis.charges.entity.CustomerAccountSerial;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
/**
 * 账户流水信息API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/customerAccountSerial", qualifier = "CustomerAccountSerialBizApi")
public interface CustomerAccountSerialBizApi {
    /**
     * 保存账户流水信息
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<CustomerAccountSerial> save(@RequestBody CustomerAccountSerialSaveDTO saveDTO);


    /**
     * 批量保存账户流水信息
     * @param saveDTOs
     * @return
     */
    @RequestMapping(method = RequestMethod.POST,value = "/saveList")
    R<CustomerAccountSerial> saveList(@RequestBody List<CustomerAccountSerialSaveDTO> saveDTOs);



    /**
     * 分页查询账户流水信息
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<CustomerAccountSerial>> page(@RequestBody PageParams<CustomerAccountSerialPageDTO> params);

}
