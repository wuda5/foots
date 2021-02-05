package com.cdqckj.gmis.userarchive.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.userarchive.dto.*;
import com.cdqckj.gmis.userarchive.entity.ConcernsCustomer;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.vo.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-02
 */
public interface CustomerService extends SuperService<Customer> {

    IPage<CustomerSecurityQuerypParametersDto> findCustomerGmIf(@RequestBody CustomerSecurityQuerypParametersVo params);


    Customer findCustomer(String customerCode);

    Boolean updateCustomerMeter(CustomGasMesterVO params);

    IPage<GasMeterCustomerDto>  findGasMeterCustomer(@RequestBody GasMeterCustomerParme parmes);

    List<String> findIdNumber();

    Page<Customer> findCustomerPage(CustomerPageVo customerPageVo);


    Page<CustomerGasDto> findCustomerGasMeterPage(CustomerPageVo customerPageVo);

    Page<CustomerGasDto> findCustomerGasMeterPageTwo(CustomerPageVo customerPageVo);

    Boolean check(CustomerUpdateDTO customerUpdateDTO);
    Boolean checkByCustomerCode(CustomerUpdateDTO customerUpdateDTO);

    Boolean checkAdd(CustomerSaveDTO customerSaveDTO);

    /**
     * 根据缴费编号获取表具列表
     * @param customerChargeNos
     * @return
     */
    List<CustomerGasInfoDTO> findGasByChargeNos(List<String> customerChargeNos);
    /**
     * 获取兴趣关注点客户信息
     * @param params
     * @return
     */
    ConcernsCustomer getConcernsCustomer(Customer params);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 14:57
    * @remark 统计客户数据
    */
    List<StsInfoBaseVo<Integer, Long>> stsByCustomerStatus(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/19 13:42
    * @remark 分类的统计
    */
    List<StsInfoBaseVo<String, Long>> stsCustomType(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/19 16:58
    * @remark 统计拉黑的数量
    */
    Long stsCustomBlackNum(StsSearchParam stsSearchParam);
}
