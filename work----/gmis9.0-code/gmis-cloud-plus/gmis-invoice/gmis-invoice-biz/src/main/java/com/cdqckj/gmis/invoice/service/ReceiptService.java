package com.cdqckj.gmis.invoice.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.invoice.dto.ReceiptPageDTO;
import com.cdqckj.gmis.invoice.entity.Receipt;

/**
 * <p>
 * 业务接口
 *
 * </p>
 *
 * @author songyz
 * @date 2020-09-04
 */
public interface ReceiptService extends SuperService<Receipt> {

    /**
     * 模糊查询票据数据
     * 匹配字段：客户编号、客户姓名
     *
     * @param pageParams
     * @param page
     * @return
     */
    public IPage<Receipt> findPage(PageParams<ReceiptPageDTO> pageParams, IPage<Receipt> page);

}
