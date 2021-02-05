package com.cdqckj.gmis.charges.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.charges.vo.StsCounterStaffVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderDetailFeeVo;
import com.cdqckj.gmis.charges.vo.StsGasLadderFeeVo;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 缴费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
@Repository
public interface ChargeRecordMapper extends SuperMapper<ChargeRecord> {
    User getUserById(Long id);
    void rebackRefundStatus(Long id);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/29 10:34
    * @remark 统计柜员的信息
    */
    List<StsCounterStaffVo> counterStaff(@Param("resultPage") Page<StsCounterStaffVo> resultPage, @Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/13 10:16
    * @remark 统计费用
    */
    List<StsGasLadderFeeVo> stsGasLadderFee(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("idList") List<Long> idList, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/13 16:14
    * @remark 统计阶梯
    */
    List<StsGasLadderDetailFeeVo> stsGasLadderDetailFee(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("idList") List<Long> idList, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 15:28
    * @remark 统计
    */
    List<StsInfoBaseVo<String, BigDecimal>> stsChangeFeeByChargeMethod(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/20 11:40
    * @remark 收费的类型
    */
    List<StsInfoBaseVo<String, BigDecimal>> stsChargeFeeItemType(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/22 14:31
    * @remark 退费
    */
    List<StsInfoBaseVo<String, BigDecimal>> stsRefundByChargeMethod(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/22 15:08
    * @remark 预付费表
    */
    BigDecimal stsBeforeGasMeter(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("gasMeterType") String gasMeterType, @Param("dataScope") String dataScope);
}
