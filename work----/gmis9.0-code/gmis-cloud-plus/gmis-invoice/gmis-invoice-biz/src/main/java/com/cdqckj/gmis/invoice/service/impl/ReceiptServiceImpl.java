package com.cdqckj.gmis.invoice.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.invoice.dao.ReceiptMapper;
import com.cdqckj.gmis.invoice.dto.ReceiptPageDTO;
import com.cdqckj.gmis.invoice.entity.Receipt;
import com.cdqckj.gmis.invoice.service.ReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author songyz
 * @date 2020-09-04
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class ReceiptServiceImpl extends SuperServiceImpl<ReceiptMapper, Receipt> implements ReceiptService {
    @Override
    public IPage<Receipt> findPage(PageParams<ReceiptPageDTO> pageParams, IPage<Receipt> page) {
        ReceiptPageDTO pageDTO = pageParams.getModel();
        LbqWrapper<Receipt> wrapper = Wraps.lbQ();

        wrapper.eq(Receipt::getReceiptState, pageDTO.getReceiptState())
                .and(!StringUtils.isEmpty(pageDTO.getQueryString()),
                        wrapperSql -> wrapperSql.like(Receipt::getCustomerNo, pageDTO.getQueryString())
                                .or().like(Receipt::getCustomerName, pageDTO.getQueryString())
                                .or().like(Receipt::getCustomerPhone, pageDTO.getQueryString()))
                .orderByDesc(Receipt::getCreateTime, Receipt::getBillingDate);

        return baseMapper.findPage(page, wrapper, new DataScope());
    }
}
