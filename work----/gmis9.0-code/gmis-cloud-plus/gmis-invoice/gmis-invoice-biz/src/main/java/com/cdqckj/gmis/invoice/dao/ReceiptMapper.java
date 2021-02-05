package com.cdqckj.gmis.invoice.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.database.mybatis.auth.DataScope;
import com.cdqckj.gmis.invoice.entity.Receipt;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 *
 * </p>
 *
 * @author songyz
 * @date 2020-09-04
 */
@Repository
public interface ReceiptMapper extends SuperMapper<Receipt> {

    /**
     * 分页查询发票信息
     *
     * @param page
     * @param queryWrapper
     * @param dataScope
     * @return
     */
    IPage<Receipt> findPage(IPage page, @Param(Constants.WRAPPER) Wrapper<Receipt> queryWrapper, DataScope dataScope);

}
