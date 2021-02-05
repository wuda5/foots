package com.cdqckj.gmis.charges.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.dao.ChargeRefundMapper;
import com.cdqckj.gmis.charges.entity.ChargeRefund;
import com.cdqckj.gmis.charges.enums.RefundStatusEnum;
import com.cdqckj.gmis.charges.service.ChargeRefundService;
import com.cdqckj.gmis.context.BaseContextHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 退费记录
 * </p>
 *
 * @author tp
 * @date 2020-09-24
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ChargeRefundServiceImpl extends SuperServiceImpl<ChargeRefundMapper, ChargeRefund> implements ChargeRefundService {


    @Override
    public BigDecimal sumChargeRefundByTime(LocalDateTime startTime, LocalDateTime endTime) {
        long chargeUserId = BaseContextHandler.getUserId();
        BigDecimal sum = baseMapper.sumChargeRefundByTime(startTime, endTime, RefundStatusEnum.REFUNDED.getCode(), chargeUserId);
        return sum == null ? BigDecimal.ZERO : sum;
    }

    @Override
    public Boolean isSummaryBill(long chargeUserId, LocalDateTime startTime, LocalDateTime endTime) {
        List<String> refundStatusList = new ArrayList<>();
        refundStatusList.add(RefundStatusEnum.REFUNDED.getCode());
        refundStatusList.add(RefundStatusEnum.CANCEL.getCode());
        refundStatusList.add(RefundStatusEnum.NONREFUND.getCode());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        QueryWrapper<ChargeRefund> wrapper =  new QueryWrapper<>();
        wrapper.lambda().notIn(ChargeRefund::getRefundStatus, refundStatusList).eq(ChargeRefund::getChargeUserId, chargeUserId);
        if(ObjectUtil.isNull(startTime) && ObjectUtil.isNotNull(endTime)){
            wrapper.lambda().and(dateSql -> dateSql.apply("unix_timestamp(create_time) <= unix_timestamp({0})", endTime.format(dateTimeFormatter)));
        }
        if(ObjectUtil.isNotNull(startTime) && ObjectUtil.isNull(endTime)){
            wrapper.lambda().and(dateSql -> dateSql.apply("unix_timestamp(create_time) >= unix_timestamp({0})", startTime.format(dateTimeFormatter)));
        }
        if(ObjectUtil.isNotNull(startTime) && ObjectUtil.isNotNull(endTime)){
            wrapper.lambda().and(dateSql -> dateSql.apply("unix_timestamp(create_time) >= unix_timestamp({0})", startTime.format(dateTimeFormatter))
                    .apply("unix_timestamp(create_time) <= unix_timestamp({0})", endTime.format(dateTimeFormatter)));
        }
        return super.count(wrapper) > 0;
    }

}
