package com.cdqckj.gmis.bizcenter.temp.counter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.RemoveMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.entity.RemoveMeterRecord;

/**
 * @author gmis
 */
public interface RemoveMeterService {

    /**
     * 分页查询拆表列表
     *
     * @param pageDTO 分页参数
     * @return 拆表分页列表数据
     */
    R<Page<RemoveMeterRecord>> pageRemoveRecord(PageParams<RemoveMeterRecordPageDTO> pageDTO);

    /**
     * 通过ID查询详情
     *
     * @param id 主键id
     * @return 拆表记录数据
     */
    R<RemoveMeterRecord> getRemoveRecord(Long id);

    /**
     * 新增拆表记录
     *
     * @param saveDTO 拆表记录数据
     * @return 保存数据
     */
    R<BusinessCheckResultDTO<RemoveMeterRecord>> add(RemoveMeterRecordSaveDTO saveDTO);

    /**
     * 拆表前检查：是否已有记录、是否欠费
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 用户编号
     * @return 检查结果
     */
    R<BusinessCheckResultDTO<RemoveMeterRecord>> check(String gasMeterCode, String customerCode);

    /**
     * 支付完成后更新数据状态
     *
     * @param id 记录id
     * @return 更新结果
     */
    R<Boolean> updateAfterPaid(Long id);


    /**
     * 支付完成后更新数据状态
     *
     * @param chargeNo 缴费编号
     * @return 更新结果
     */
    R<Boolean> updateAfterPaid(String chargeNo);

    /**
     * 支付回滚
     *
     * @param id 记录id
     * @return 回滚结果
     */
    R<Boolean> rollbackPaid(Long id);
}
