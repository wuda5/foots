package com.cdqckj.gmis.bizcenter.temp.counter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SettlementResult;
import com.cdqckj.gmis.bizcenter.temp.counter.dto.SettlementReturnParam;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;

/**
 * @author gmis
 */
public interface ChangeMeterService {

    /**
     * 分页查询换表记录列表
     *
     * @param pageDTO
     * @return
     */
    R<Page<ChangeMeterRecord>> pageChangeRecord(PageParams<ChangeMeterRecordPageDTO> pageDTO);

    /**
     * 通过ID查询详情
     *
     * @param id
     * @return
     */
    R<ChangeMeterRecord> getChangeRecord(Long id);

    /**
     * 新增换表记录数据包含新表信息
     *
     * @param saveDTO
     * @return
     */
    R<BusinessCheckResultDTO<ChangeMeterRecord>> add(ChangeMeterRecordSaveDTO saveDTO);

    /**
     * 新增前数据校验
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 用户编号
     * @return 检查结果
     */
    R<BusinessCheckResultDTO<ChangeMeterRecord>> check(String gasMeterCode, String customerCode);

    /**
     * 支付完成后更新数据状态
     *
     * @param id 记录id
     * @return
     */
    R<Boolean> updateAfterPaid(Long id);

    /**
     * 支付完成后更新数据状态
     *
     * @param chargeNo 缴费编号
     * @return
     */
    R<Boolean> updateAfterPaid(String chargeNo);

    /**
     * 支付回滚
     *
     * @param id 记录id
     * @return 回滚结果
     */
    R<Boolean> rollbackPaid(Long id);


    /**
     * 中心计费预付费结算
     *
     * @param record 换表记录
     * @return
     */
    SettlementResult costCenterRechargeMeter(ChangeMeterRecord record);

    /**
     * 中心计费预付费场景抄表数据预结算回退
     */
    void settlementReturn(SettlementReturnParam param);
}
