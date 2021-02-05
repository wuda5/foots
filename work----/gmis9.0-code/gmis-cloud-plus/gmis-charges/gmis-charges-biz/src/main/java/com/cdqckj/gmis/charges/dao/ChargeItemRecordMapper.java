package com.cdqckj.gmis.charges.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordPageDTO;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordResDTO;
import com.cdqckj.gmis.charges.vo.ChargeItemVO;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;

import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBasePlusVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 缴费项记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Repository
public interface ChargeItemRecordMapper extends SuperMapper<ChargeItemRecord> {

    /**
     * @auth lijianguo
     * @date 2020/10/27 14:26
     * @remark 获取收费项目
     */
    List<ChargeItemRecord> getChangeItemByChargeNo(@Param("chargeNo") String chargeNo);

    /**
     * 查询表具燃气费最后一次缴费时间和缴费次数
     *
     * @param gasMeterCode 表具编号
     * @return 查询结果
     */
    ChargeItemVO getLastUpdateTimeAndCount(@Param("gasMeterCode") String gasMeterCode);

    List<ChargeItemRecordResDTO> pageListByMeterCustomerCode(@Param("record") ChargeItemRecordPageDTO record, @Param("offset")int offset, @Param("pageSize")int pageSize);
    int pageListByMeterCustomerCodeCount(@Param("record") ChargeItemRecordPageDTO record);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/20 10:25
    * @remark 统计用气类型和气量
    */
    List<StsInfoBasePlusVo<String, Long>> stsGasFeeAndType(@Param("stsSearchParam") StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 16:06
    * @remark 卡表的售气
    */
    BigDecimal stsCardGasMeter(@Param("stsSearchParam") StsSearchParam stsSearchParam);
}
