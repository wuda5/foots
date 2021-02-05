package com.cdqckj.gmis.charges.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.charges.dto.CustomerAccountPageDTO;
import com.cdqckj.gmis.charges.dto.CustomerAccountSaveDTO;
import com.cdqckj.gmis.charges.dto.CustomerAccountUpdateDTO;
import com.cdqckj.gmis.charges.dto.UpdateAccountParamDTO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * 账户信息API
 * @author tp
 * @Date 2020-08-28
 */
@FeignClient(name = "${gmis.feign.gmis-charges-server:gmis-charges-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/customerAccount", qualifier = "CustomerAccountBizApi")
public interface CustomerAccountBizApi {
    /**
     * 保存账户信息
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<CustomerAccount> save(@RequestBody CustomerAccountSaveDTO saveDTO);


    /**
     * 保存账户信息
     * @param saveDTO
     * @return
     */
    @PostMapping(value = "/saveList")
    R<CustomerAccount> saveList(@RequestBody List<CustomerAccountSaveDTO> saveDTO);


    /**
     * 更新账户信息
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<CustomerAccount> update(@RequestBody CustomerAccountUpdateDTO updateDTO);

    @PutMapping(value = "/updateBatch")
    R<Boolean> updateBatchById(@RequestBody List<CustomerAccountUpdateDTO> list);

    /**
     * 删除账户信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/logicalDelete",method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> id);

    /**
     * 分页查询账户信息
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<CustomerAccount>> page( PageParams<CustomerAccountPageDTO> params);

    /**
     * 分页查询账户信息
     * @param params
     * @return
     */
    @PostMapping(value = "/query")
    R<List<CustomerAccount>> query( CustomerAccount params);

    /**
     * 条件查询账户-关联查询最新的用户名
     * @param param
     * @return
     */
    @PostMapping(value = "/queryByParam")
    R<CustomerAccount> queryByParam(@RequestBody CustomerAccount param);


    /**
     * ID查询账户信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    R<CustomerAccount> get(@PathVariable("id") Long id);

//    /**
//     * 根据客户编号查询账户，会自动加锁
//     * @param customerCode
//     * @return
//     */
//    @GetMapping("/queryByCustomerCodeLock")
//    R<CustomerAccount> queryByCustomerCodeLock(@RequestParam(value = "customerCode") String customerCode);
//    /**
//     * 根据客户编号查询账户
//     * @param customerCode
//     * @return
//     */
//    @GetMapping("/queryByCustomerCode")
//    public R<CustomerAccount> queryByCustomerCode(@RequestParam(value = "customerCode") String customerCode);

//    @PostMapping(value = "/check")
//    R<Boolean> check(@RequestBody CustomerAccountUpdateDTO customerAccountUpdateDTO);

    /**
     * 换表更新账户余额 并生成对应的流水
     *
     * @param updateDTO
     * @return
     */
    @PostMapping(value = "/updateAccountAfterChangeMeter")
    R<Boolean> updateAccountAfterChangeMeter(@RequestBody @Validated UpdateAccountParamDTO updateDTO);

    /**
     * 有效账户查询
     * @param customerCode 客户编号
     * @return 有效的账户
     */
    @GetMapping("/queryValidAccount")
    R<CustomerAccount> queryValidAccount(@RequestParam(value = "customerCode") String customerCode);

//    /**
//     * 客户编号列表获取账户列表
//     *
//     * @param customerCodeList 客户编号列表
//     * @return 账户列表
//     */
//    @PostMapping("/getByCustomerCodeList")
//    R<List<CustomerAccount>> getByCustomerCodeList(@RequestBody List<String> customerCodeList);

}
