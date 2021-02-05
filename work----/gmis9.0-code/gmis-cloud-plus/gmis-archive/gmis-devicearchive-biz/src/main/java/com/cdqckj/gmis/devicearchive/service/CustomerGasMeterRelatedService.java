package com.cdqckj.gmis.devicearchive.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.devicearchive.dto.CustomerGasMeterRelatedUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.vo.BatchUserDeviceVO;
import com.cdqckj.gmis.devicearchive.vo.MeterRelatedVO;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceVO;

import java.util.List;

/**
 * <p>
 * 业务接口
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-03
 */
public interface CustomerGasMeterRelatedService extends SuperService<CustomerGasMeterRelated> {
    /**
     * 查询客户设备列表
     *
     * @param params
     * @return
     */
    Page<CustomerDeviceDTO> findCustomerDevicePage(CustomerDeviceVO params);

    /**
     * 根据气表id批量更新
     *
     * @param userGasList
     * @return
     */
    int updateBatchUserDevice(List<BatchUserDeviceVO> userGasList);

    CustomerGasMeterRelated findOneByCode(String code);

    List<CustomerGasMeterRelated> findCustomerAndGasMeterList(com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated customerGasMeterRelated);

    List<CustomerGasMeterRelated> findPreOpenAccount();

    List<CustomerGasMeterRelated> findNormalOpenAccount();

    Boolean check(CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO);

    Boolean checkChargeNo(CustomerGasMeterRelatedUpdateDTO customerGasMeterRelatedUpdateDTO);

    /*
     * 根据客户 ID差寻出表具信息
     *
     * */


    /**
     * 通过表具code和客户code逻辑删除有效的关联管理
     *
     * @param updateDTO
     * @return
     */
    CustomerGasMeterRelated deleteByCustomerAndMeter(CustomerGasMeterRelatedUpdateDTO updateDTO);

    /**
     * 通过客户编号查询所有有效的关联关系和表具状态
     *
     * @param customerCode 客户编号
     * @return 关联关系列表
     */
    List<MeterRelatedVO> queryAllRelation(String customerCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 13:46
    * @remark 统计这个类型的数据
    */
    List<StsInfoBaseVo<String, Long>> accountNowTypeFrom(StsSearchParam stsSearchParam);
}
