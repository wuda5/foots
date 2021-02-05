package com.cdqckj.gmis.devicearchive.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.vo.BatchUserDeviceVO;
import com.cdqckj.gmis.devicearchive.vo.MeterRelatedVO;
import com.cdqckj.gmis.userarchive.dto.CustomerDeviceDTO;
import com.cdqckj.gmis.userarchive.vo.CustomerDeviceVO;
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
 * @date 2020-07-03
 */
@Repository
public interface CustomerGasMeterRelatedMapper extends SuperMapper<CustomerGasMeterRelated> {
    /**
     * 查询客户设备列表
     * @param page
     * @param params
     * @return
     */
    Page<CustomerDeviceDTO> findCustomerDevicePage(Page<CustomerDeviceDTO> page, @Param("params")CustomerDeviceVO params);

    /**
     * 根据气表Id批量更新
     * @param userGasVo
     * @return
     */
    int updateBatchUserDevice(@Param("userGasVo")List<BatchUserDeviceVO> userGasVo);

    /**
     * 通过客户编号查询所有有效的关联关系和表具状态
     *
     * @param customerCode 客户编号
     * @return 关联关系列表
     */
    List<MeterRelatedVO> queryAllRelation(String customerCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 13:48
    * @remark 开户的统计信息
    */
    List<StsInfoBaseVo<String, Long>> accountNowTypeFrom(@Param("stsSearchParam") StsSearchParam stsSearchParam);
}
