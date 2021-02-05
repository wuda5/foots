package com.cdqckj.gmis.bizcenter.interest.concerns.controller;

import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.userarchive.entity.ConcernsCustomer;
import com.cdqckj.gmis.userarchive.entity.Customer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author songyz
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/interestConcernsCustomer")
@Api(value = "interestConcernsCustomer", tags = "兴趣关注点-客户")
public class InterestConcernsCustomerController {
    @Resource
    private CustomerBizApi customerBizApi;

    @ApiOperation(value = "查询客户信息")
    @PostMapping("/query")
    R<ConcernsCustomer> query(@RequestBody Customer customer){

        return customerBizApi.getConcernsCustomer(customer);
    }
}
