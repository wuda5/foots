package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.charges.dto.CustomerAccountPageDTO;
import com.cdqckj.gmis.charges.dto.CustomerAccountSaveDTO;
import com.cdqckj.gmis.charges.dto.CustomerAccountUpdateDTO;
import com.cdqckj.gmis.charges.dto.UpdateAccountParamDTO;
import com.cdqckj.gmis.charges.entity.CustomerAccount;
import com.cdqckj.gmis.charges.service.CustomerAccountService;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 账户表
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customerAccount")
@Api(value = "CustomerAccount", tags = "账户表")
//@PreAuth(replace = "customerAccount:")
public class CustomerAccountController extends SuperController<CustomerAccountService, Long, CustomerAccount, CustomerAccountPageDTO, CustomerAccountSaveDTO, CustomerAccountUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<CustomerAccount> customerAccountList = list.stream().map((map) -> {
            CustomerAccount customerAccount = CustomerAccount.builder().build();
            //TODO 请在这里完成转换
            return customerAccount;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(customerAccountList));
    }
    /**
     * 根据客户编号查询账户
     * 带行锁
     * @param customerCode 客户编号
     * @return 查询结果
     */
    @ApiOperation(value = "账户事务查询", notes = "账户事务查询")
    @GetMapping("/queryByCustomerCodeLock")
    public R<CustomerAccount> queryByCustomerCodeLock(@RequestParam(value = "customerCode") String customerCode) {
        return R.success(baseService.queryAccountByCharge(customerCode));
    }
    /**
     * 根据客户编号查询账户
     * 不带行锁
     * @param customerCode 客户编号
     * @return 查询结果
     */
    @ApiOperation(value = "账户事务查询", notes = "账户事务查询")
    @GetMapping("/queryByCustomerCode")
    public R<CustomerAccount> queryByCustomerCode(@RequestParam(value = "customerCode") String customerCode) {
        return R.success(baseService.getOne(new LbqWrapper<CustomerAccount>().eq(CustomerAccount::getCustomerCode,customerCode)));
    }

//    @PostMapping(value = "/check")
//    R<Boolean> check(@RequestBody CustomerAccountUpdateDTO customerAccountUpdateDTO){
//        return R.success(baseService.check(customerAccountUpdateDTO));
//    }


    /**
     * 换表更新账户余额 并生成对应的流水
     */
    @ApiOperation(value = "换表更新账户余额", notes = "更新账户余额 并生成对应的流水")
    @PostMapping(value = "/updateAccountAfterChangeMeter")
    @GlobalTransactional
    R<Boolean> updateAccountAfterChangeMeter(@RequestBody @Validated UpdateAccountParamDTO updateDTO) {

        return R.success(baseService.updateAccountAfterChangeMeter(updateDTO));
    }

    @ApiOperation(value = "有效账户查询", notes = "有效账户查询")
    @GetMapping("/queryValidAccount")
    public R<CustomerAccount> queryValidAccount(@RequestParam(value = "customerCode") String customerCode) {
        LbqWrapper<CustomerAccount> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(CustomerAccount::getCustomerCode, customerCode)
                .eq(CustomerAccount::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());
        return R.success(baseService.getOne(lbqWrapper));
    }

    /**
     * 客户编号列表获取账户列表
     *
     * @param customerCodeList 客户编号列表
     * @return 账户列表
     */
    @ApiOperation(value = "客户编号列表获取账户列表")
    @PostMapping("/getByCustomerCodeList")
    public R<List<CustomerAccount>> getByCustomerCodeList(@RequestBody List<String> customerCodeList) {
        if (CollectionUtils.isEmpty(customerCodeList)) {
            return R.success(Collections.EMPTY_LIST);
        }
        LbqWrapper<CustomerAccount> lbqWrapper = Wraps.lbQ();
        lbqWrapper.in(CustomerAccount::getCustomerCode, customerCodeList)
                .eq(CustomerAccount::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());

        return R.success(baseService.list(lbqWrapper));
    }

    /**
     * 条件查询账户-关联查询最新的用户名
     * @param param
     * @return
     */
    @ApiOperation(value = "客户编号列表获取账户列表")
    @PostMapping("/queryByParam")
    public R<CustomerAccount> queryByParam(@RequestBody CustomerAccount param){
        return R.success(baseService.queryAccountByParam(param));
    }

}
