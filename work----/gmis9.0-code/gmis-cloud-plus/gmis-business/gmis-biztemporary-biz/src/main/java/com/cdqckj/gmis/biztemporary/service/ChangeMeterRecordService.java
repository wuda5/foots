package com.cdqckj.gmis.biztemporary.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.dto.ChangeMeterRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.entity.ChangeMeterRecord;
import com.cdqckj.gmis.biztemporary.vo.ChangeMeterRecordVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 气表换表记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-11
 */
public interface ChangeMeterRecordService extends SuperService<ChangeMeterRecord> {

    /**
     * 新增换表记录
     *
     * @param saveDTO
     * @return
     */
    ChangeMeterRecord addWithMeterInfo(ChangeMeterRecordSaveDTO saveDTO);

    /**
     * 换表前校验数据
     *
     * @param oldMeterCode 表具编号
     * @param customerCode 客户编号
     * @return 校验结果
     */
    BusinessCheckResultDTO<ChangeMeterRecord> check(String oldMeterCode, String customerCode);

    /**
     * 校验新表数据是否可用
     *
     * @param oldMeterCode
     * @param newMeterCode
     * @return
     */
    Boolean validateNewMeter(String oldMeterCode, String newMeterCode);

    /**
     * 支付成功后更新数据状态
     *
     * @param id 数据id
     * @return 更新的数据
     */
    ChangeMeterRecord updateAfterPaid(Long id);

    /**
     * 业务关注点数据查询
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 关注数据
     */
    List<ChangeMeterRecordVO> queryFocusInfo(String customerCode, String gasMeterCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 16:51
    * @remark 换表
    */
    Long stsChangeMeterNum(StsSearchParam stsSearchParam);
}
