package com.cdqckj.gmis.userarchive.controller;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.utils.ExcelSelectortDTO;
import com.cdqckj.gmis.base.utils.ExcelUtils;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.userarchive.dto.*;
import com.cdqckj.gmis.userarchive.entity.ConcernsCustomer;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.enumeration.BlackStatusEnum;
import com.cdqckj.gmis.userarchive.enumeration.CustomerSexEnum;
import com.cdqckj.gmis.userarchive.enumeration.CustomerStatusEnum;
import com.cdqckj.gmis.userarchive.service.CustomerService;
import com.cdqckj.gmis.userarchive.vo.*;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 前端控制器
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-02
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/customer")
@Api(value = "Customer", tags = "客户档案接口")
//@PreAuth(replace = "customer:")
public class CustomerController extends SuperController<CustomerService, Long, Customer, CustomerPageDTO, CustomerSaveDTO, CustomerUpdateDTO> {
    /**
     * 新增
     *
     * @param customerSaveDTOs 保存参数
     * @return 实体
     */
    @Override
    public void query(PageParams<CustomerPageDTO> params, IPage<Customer> page, Long defSize) {
        CustomerPageDTO model = params.getModel();
        if (CollectionUtils.isEmpty(model.getCustomerStatusList())) {
            super.query(params, page, null);
        }
        Customer model2 = BeanUtil.toBean(params.getModel(), Customer.class);
        QueryWrap<Customer> wp = Wraps.q(model2);
        handlerWrapper(wp, params);
        wp.lambda().in(Customer::getCustomerStatus, model.getCustomerStatusList());
        baseService.page(page, wp);

    }

    @ApiOperation(value = "批量新增")
    @PostMapping(value = "/saveBatch")
    @SysLog(value = "批量新增", request = false)
    public R<List<Customer>> saveBatch(@RequestBody List<CustomerSaveDTO> customerSaveDTOs) {
        List<Customer> saveList=new ArrayList<>();
        for (CustomerSaveDTO customerSaveDTO : customerSaveDTOs) {
            customerSaveDTO.setCustomerCode(BizCodeNewUtil.getCustomCode());
            if(ObjectUtil.isNull(customerSaveDTO.getCustomerStatus())) {
                customerSaveDTO.setCustomerStatus(CustomerStatusEnum.EFFECTIVE.getCode());
            }
            customerSaveDTO.setBlackStatus(BlackStatusEnum.Remove_Blacklist.getCode());
            customerSaveDTO.setBalance(new BigDecimal(0.0000));
            customerSaveDTO.setPreStoreMoney(new BigDecimal(0.0000));
            customerSaveDTO.setPreStoreCount(0);
            Customer customer=new Customer();
            customerSaveDTO.setOrgId(BaseContextHandler.getOrgId());
            customerSaveDTO.setOrgName(BaseContextHandler.getOrgName());
            customerSaveDTO.setCompanyCode(BaseContextHandler.getTenantId());
            customerSaveDTO.setCompanyName(BaseContextHandler.getTenantName());
            Customer customer1 = BeanPlusUtil.toBean(customerSaveDTO,Customer.class);
            saveList.add(setCommonParams(customer1));
        }
        baseService.saveBatch(saveList);
        return R.success(saveList);
    }
    @Override
    public void exportExcel(HttpServletResponse response, List<ExcelSelectortDTO> selectors, List<Customer> dataList, Class clazz) {
        for (Customer customer : dataList) {
            if(CustomerSexEnum.MAN.getCode().equals(customer.getSex())){
                customer.setSex(CustomerSexEnum.MAN.getDesc());
            }else  if(CustomerSexEnum.WOMEN.getCode().equals(customer.getSex())){
                customer.setSex(CustomerSexEnum.WOMEN.getDesc());
            }
        }
        ExcelUtils.exportExcel(dataList, selectors,  "", "", clazz, "", response);
    }
    @ApiOperation(value = "查询客户")
    @PostMapping ("/findCustomer")
    public R<Customer> findCustomer(@RequestBody  String customerCode){
        return R.success(baseService.findCustomer(customerCode));
    }
    @ApiOperation(value = "编辑")
    @PostMapping ("/updateCustomerMeter")
    public R<Boolean> updateCustomerMeter(@RequestBody CustomGasMesterVO params){
        return R.success(baseService.updateCustomerMeter(params));
    }

    @ApiOperation(value = "获取客户表具信息带安检信息")
    @PostMapping("/findCustomerGmIf")
    public R<IPage<CustomerSecurityQuerypParametersDto>> findCustomerGmIf(@RequestBody CustomerSecurityQuerypParametersVo params){
        return R.success(baseService.findCustomerGmIf(params));
    }
    /*
    查询客户表具信息 不带安检（表具作为主表）
    * */

    @ApiOperation(value = "获取客户表具信息不带安检信息")
    @PostMapping("/findGasMeterCustomer")
    public R<IPage<GasMeterCustomerDto>> findGasMeterCustomer(@RequestBody GasMeterCustomerParme parmes){
        return R.success(baseService.findGasMeterCustomer(parmes));
    }
    @ApiOperation(value = "获取客户表具信息不带安检信息")
    @PostMapping("/findIdNumber")
    R<List<String>> findIdNumber(){
        return R.success(baseService.findIdNumber());
    }

    @ApiOperation(value = "导入客户信息")
    @PostMapping(value = "/importCustomer")
    public R<List<Customer>> importCustomerData(@RequestParam(value = "file") MultipartFile simpleFile, HttpServletRequest request,
                                                      HttpServletResponse response) throws Exception {
        List<Map<String, Object>> list = getDataList(simpleFile, request);
        List<Customer> customers = getEntityList(list);
        for (Customer customer : customers) {
            if(customer.getTelphone()!=null){
                customer.setTelphone(new BigDecimal(customer.getTelphone()).toPlainString());
            }
            if(customer.getIdNumber()!=null){
                customer.setIdNumber(new BigDecimal(customer.getIdNumber()).toPlainString());
            }
        }
        return R.success(customers);
    }
    @ApiOperation(value = "获取客户列表信息")
    @PostMapping(value = "/findCustomerPage")
    R<Page<Customer>> findCustomerPage(@RequestBody CustomerPageVo customerPageVo){
        log.info(BaseContextHandler.getTenant());
        return R.success(baseService.findCustomerPage(customerPageVo));
    }

    @ApiOperation(value = "获取表具客户列表信息,日常综合")
    @PostMapping(value = "/findGasCusPage")
    R<Page<CustomerGasDto>> findCustomerGasMeterPage(@RequestBody CustomerPageVo customerPageVo){
        return R.success(baseService.findCustomerGasMeterPage(customerPageVo));
    }

    @ApiOperation(value = "获取表具客户列表信息,临时综合")
    @PostMapping(value = "/findGasCusPageTwo")
    R<Page<CustomerGasDto>> findCustomerGasMeterPageTwo(@RequestBody CustomerPageVo customerPageVo){
        return R.success(baseService.findCustomerGasMeterPageTwo(customerPageVo));
    }

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody CustomerUpdateDTO customerUpdateDTO){
        return R.success(baseService.check(customerUpdateDTO));
    }

    @ApiOperation(value = "根据客户编号是否存在")
    @PostMapping(value = "/checkByCustomerCode")
    public R<Boolean> checkByCustomerCode(@RequestBody CustomerUpdateDTO customerUpdateDTO){
        return R.success(baseService.checkByCustomerCode(customerUpdateDTO));
    }

    @PostMapping(value = "/checkAdd")
    R<Boolean> checkAdd(@RequestBody CustomerSaveDTO customerSaveDTO){
        return R.success(baseService.checkAdd(customerSaveDTO));
    }

    /**
     * 根据燃气缴费编号获取表具列表
     * @author hc
     * @param meterChargeNos 燃气缴费集
     * @return
     */
    @GetMapping(value = "/findGasChargeNos")
    R<List<CustomerGasInfoDTO>> findGasByChargeNos(@RequestParam("customerChargeNos") List<String> customerChargeNos){


        return R.success(baseService.findGasByChargeNos(customerChargeNos));
    }
    /**
     * 获取兴趣关注点客户信息
     * @param params
     * @return
     */
    @ApiOperation(value = "获取兴趣关注点客户信息")
    @PostMapping("/getConcernsCustomer")
    R<ConcernsCustomer>  getConcernsCustomer(@RequestBody Customer params){
        return R.success(baseService.getConcernsCustomer(params));
    }

    /**
     * 客户编号列表获取档案列表
     *
     * @param customerCodeList 客户编号列表
     * @return 客户档案列表
     */
    @ApiOperation(value = "客户编号列表获取档案列表")
    @PostMapping("/getByCustomerCodeList")
    public R<List<Customer>> getByCustomerCodeList(@RequestBody List<String> customerCodeList) {
        if (CollectionUtils.isEmpty(customerCodeList)) {
            return R.success(Collections.EMPTY_LIST);
        }
        LbqWrapper<Customer> lbqWrapper = Wraps.lbQ();
        lbqWrapper.in(Customer::getCustomerCode, customerCodeList)
                .eq(Customer::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue());

        return R.success(baseService.list(lbqWrapper));
    }

    /**
     * 根据客户编号更新客户信息
     * @param customer
     * @return
     */
    @ApiOperation(value = "根据客户编号更新客户信息")
    @PostMapping ("/updateCustomerByCode")
    public R<Boolean> updateCustomerByCode( @RequestBody Customer customer){
        LbqWrapper<Customer> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(Customer::getCustomerCode, customer.getCustomerCode());
        return R.success(baseService.update(customer, lbqWrapper));
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/16 14:40
     * @remark 统计客户的信息
     */
    @ApiOperation(value = "统计客户的信息")
    @PostMapping ("/sts/stsByCustomerStatus")
    R<List<StsInfoBaseVo<Integer, Long>>> stsByCustomerStatus(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<Integer, Long>> infoBaseVos = this.baseService.stsByCustomerStatus(stsSearchParam);
        return R.success(infoBaseVos);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/19 13:38
     * @remark 客户的分类的统计
     */
    @ApiOperation(value = "客户的分类的统计")
    @PostMapping ("/sts/stsCustomType")
    R<List<StsInfoBaseVo<String, Long>>> stsCustomType(@RequestBody StsSearchParam stsSearchParam){

        List<StsInfoBaseVo<String, Long>> infoBaseVos = this.baseService.stsCustomType(stsSearchParam);
        return R.success(infoBaseVos);
    }

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/19 13:38
     * @remark 黑名单的数量统计
     */
    @PostMapping ("/sts/stsCustomBlackNum")
    R<Long> stsCustomBlackNum(@RequestBody StsSearchParam stsSearchParam){
        Long num = this.baseService.stsCustomBlackNum(stsSearchParam);
        if (num == null){
            num = 0L;
        }
        return R.success(num);
    }

}
