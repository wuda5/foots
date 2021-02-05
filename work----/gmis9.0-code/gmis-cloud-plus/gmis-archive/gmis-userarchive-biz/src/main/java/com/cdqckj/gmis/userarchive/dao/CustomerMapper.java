package com.cdqckj.gmis.userarchive.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import com.cdqckj.gmis.userarchive.dto.CustomerGasInfoDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerPageDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerSecurityQuerypParametersDto;
import com.cdqckj.gmis.userarchive.entity.ConcernsCustomer;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.vo.CustomerPageVo;
import com.cdqckj.gmis.userarchive.vo.CustomerSecurityQuerypParametersVo;
import com.cdqckj.gmis.userarchive.vo.GasMeterCustomerDto;
import com.cdqckj.gmis.userarchive.vo.GasMeterCustomerParme;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-02
 */
@Repository
public interface CustomerMapper extends SuperMapper<Customer> {
    /**
     * 查询客户设备列表
     * @param page
     * @param params
     * @return
     */
/*    IPage<CustomerDesDTO> findCustomerBlacklistPage(IPage<CustomerDesDTO> page, @Param("params") CustomerDesVo params);*/
/*
    IPage<CustomerDesDTO> findCustomerPage(IPage<CustomerDesDTO> page, @Param("params") CustomdesVoT params);*/

    IPage<CustomerSecurityQuerypParametersDto> findCustomerGmIf(IPage<CustomerSecurityQuerypParametersDto> page, @Param("params") CustomerSecurityQuerypParametersVo params);

    IPage<GasMeterCustomerDto> findGasMeterCustomer(IPage<GasMeterCustomerDto> page,  @Param("parmes") GasMeterCustomerParme parmes);

    List<String> findIdNumber();

    Page<Customer> findCustomerPage(Page<CustomerPageDTO> page, @Param("params") CustomerPageVo customerPageVo, DataScope dataScope);

    Page<CustomerGasDto> findCustomerGasMeterPage(Page<CustomerGasDto> page, @Param("params") CustomerPageVo customerPageVo, DataScope dataScope);

    /** 根据缴费编号 批量获取缴费信息 **/
    List<CustomerGasInfoDTO> findGasByChargeNos(@Param("customerChargeNos") List<String> customerChargeNos);

    /**
     * 获取兴趣关注点客户信息
     * @param params
     * @return
     */
    ConcernsCustomer getConcernsCustomer(@Param("params") Customer params);


    Page<CustomerGasDto> findCustomerGasMeterPageTwo(Page<CustomerGasDto> page, @Param("params") CustomerPageVo customerPageVo, DataScope dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 14:57
    * @remark 统计客户数据
    */
    List<StsInfoBaseVo<Integer, Long>> stsByCustomerStatus(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/19 13:43
    * @remark 统计分类
    */
    List<StsInfoBaseVo<String, Long>> stsCustomType(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/19 16:58
    * @remark 统计数量
    */
    Long stsCustomBlackNum(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);
}
