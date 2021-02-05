package com.cdqckj.gmis.charges.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordPageDTO;
import com.cdqckj.gmis.charges.dto.ChargeItemRecordResDTO;
import com.cdqckj.gmis.charges.vo.ChargeItemVO;
import com.cdqckj.gmis.charges.entity.ChargeItemRecord;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBasePlusVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 缴费项记录
 * </p>
 *
 * @author tp
 * @date 2020-09-07
 */
public interface ChargeItemRecordService extends SuperService<ChargeItemRecord> {

    /**
     * @auth lijianguo
     * @date 2020/10/27 14:22
     * @remark 根据支付获取支付的item
     */
    List<ChargeItemRecord> getChangeItemByChargeNo(String chargeNo);

    /**
     * 查询表具燃气费最后一次缴费时间和缴费次数
     *
     * @param gasMeterCode 表具编号
     * @return 查询结果
     */
    ChargeItemVO getLastUpdateTimeAndCount(String gasMeterCode);


    Page<ChargeItemRecordResDTO> pageListByMeterCustomerCode(PageParams<ChargeItemRecordPageDTO> params);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/20 10:24
    * @remark 统计用气的金额和气量
    */
    List<StsInfoBasePlusVo<String, Long>> stsGasFeeAndType(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/21 16:05
    * @remark 统计卡表的售气量
     * @return
    */
    BigDecimal stsCardGasMeter(StsSearchParam stsSearchParam);
}
