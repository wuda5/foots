package com.cdqckj.gmis.biztemporary.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.biztemporary.dto.BusinessCheckResultDTO;
import com.cdqckj.gmis.biztemporary.entity.RemoveMeterRecord;
import com.cdqckj.gmis.biztemporary.vo.ChangeMeterRecordVO;
import com.cdqckj.gmis.biztemporary.vo.RemoveMeterRecordVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 气表拆表记录
 * </p>
 *
 * @author gmis
 * @date 2020-11-11
 */
public interface RemoveMeterRecordService extends SuperService<RemoveMeterRecord> {

    /**
     * 拆表前检查：是否已有记录
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 客户编号
     * @return 检查结果
     */
    BusinessCheckResultDTO<RemoveMeterRecord> check(String gasMeterCode, String customerCode);

    /**
     * 业务关注点数据查询
     *
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return 关注数据
     */
    List<RemoveMeterRecordVO> queryFocusInfo(String customerCode, String gasMeterCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 16:39
    * @remark 统计拆表的信息
    */
    Long stsRemoveMeterNum(StsSearchParam stsSearchParam);
}
