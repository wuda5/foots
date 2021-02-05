package com.cdqckj.gmis.archive;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.userarchive.dto.*;
import com.cdqckj.gmis.userarchive.entity.ConcernsCustomer;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.vo.*;
import feign.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.util.List;

/**
 * 客户信息
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.userarchive-server:gmis-userarchive-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/customer", qualifier = "customerBizApi")
public interface CustomerBizApi {

    /**
     * @param saveDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    R<Customer> save(@RequestBody CustomerSaveDTO saveDTO);

    /**
     * @param updateDTO
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT)
    R<Customer> update(@RequestBody CustomerUpdateDTO updateDTO);

    @PutMapping(value = "/updateBatch")
     R<Boolean> updateBatchById(@RequestBody List<CustomerUpdateDTO> list);
    @PostMapping(value = "/saveBatch")
    R<List<Customer>> saveBatch(@RequestBody  List<CustomerSaveDTO> list) ;

    /**
     * @param ids
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE)
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    /**
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<Customer>> page(@RequestBody PageParams<CustomerPageDTO> params);
    /*删除客户信息
    * */
    @DeleteMapping(value = "/logicalDelete")
    R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids);

    @PostMapping(value = "/importCustomer", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    R<List<Customer>> importCustomerData(@RequestPart(value = "file") MultipartFile file) throws Exception;

    @PostMapping ("/findCustomerGmIf")
    R<IPage<CustomerSecurityQuerypParametersDto>> findCustomerGmIf(@RequestBody CustomerSecurityQuerypParametersVo params);
    @PostMapping ("/findCustomer")
    R<Customer> findCustomerByCode(@RequestBody String customerCode);
    @PostMapping ("/updateCustomerMeter")
    R<Boolean> updateCustomerAndGasMeter(@RequestBody CustomGasMesterVO params);

    @PostMapping("/findGasMeterCustomer")
     R<Page<GasMeterCustomerDto>> findGasMeterCustomer(@RequestBody GasMeterCustomerParme parmes);
    @PostMapping("/query")
     R<List<Customer>> query(@RequestBody Customer customer);
    @PostMapping("/findIdNumber")
     R<List<String>> findIdNumber();
    @PostMapping(value = "/exportCombobox")
    Response exportCombobox(@RequestBody @Validated PageParams<CustomerPageDTO> params);
    @PostMapping(value = "/findCustomerPage")
    R<Page<Customer>> findCustomerPage(@RequestBody CustomerPageVo customerPageVo);

    @PostMapping(value = "/findGasCusPage")
    R<Page<CustomerGasDto>> findCustomerGasMeterPage(@RequestBody CustomerPageVo customerPageVo);


    @PostMapping(value = "/findGasCusPageTwo")
    R<Page<CustomerGasDto>> findCustomerGasMeterPageTwo(@RequestBody CustomerPageVo customerPageVo);

    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody CustomerUpdateDTO customerUpdateDTO);
    @ApiOperation(value = "根据客户编号是否存在")
    @PostMapping(value = "/check")
    R<Boolean> checkByCustomerCode(@RequestBody CustomerUpdateDTO customerUpdateDTO);

    @PostMapping(value = "/checkAdd")
    R<Boolean> checkAdd(@RequestBody CustomerSaveDTO customerSaveDTO);

    @GetMapping(value = "/findGasChargeNos")
    R<List<CustomerGasInfoDTO>> findGasByChargeNos(@RequestParam("customerChargeNos") List<String> customerChargeNos);
    /**
     * 获取兴趣关注点客户信息
     * @param params
     * @return
     */
    @PostMapping("/getConcernsCustomer")
    R<ConcernsCustomer> getConcernsCustomer(@RequestBody Customer params);
    /**
     * 客户编号列表获取档案列表
     *
     * @param customerCodeList 客户编号列表
     * @return 客户档案列表
     */
    @PostMapping("/getByCustomerCodeList")
    R<List<Customer>> getByCustomerCodeList(@RequestBody List<String> customerCodeList);
    /**
     * 根据客户编号更新客户信息
     * @param customer
     * @return
     */
    @ApiOperation(value = "根据客户编号更新客户信息")
    @PostMapping ("/updateCustomerByCode")
    public R<Boolean> updateCustomerByCode( @RequestBody Customer customer);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 14:40
    * @remark 统计客户的信息
    */
    @PostMapping ("/sts/stsByCustomerStatus")
    R<List<StsInfoBaseVo<Integer, Long>>> stsByCustomerStatus(@RequestBody StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/19 13:38
    * @remark 客户的分类的统计
    */
    @PostMapping ("/sts/stsCustomType")
    R<List<StsInfoBaseVo<String, Long>>> stsCustomType(@RequestBody StsSearchParam stsSearchParam);

    /**
     * @Author: lijiangguo
     * @Date: 2021/1/19 13:38
     * @remark 黑名单的数量统计
     */
    @PostMapping ("/sts/stsCustomBlackNum")
    R<Long> stsCustomBlackNum(@RequestBody StsSearchParam stsSearchParam);
}
