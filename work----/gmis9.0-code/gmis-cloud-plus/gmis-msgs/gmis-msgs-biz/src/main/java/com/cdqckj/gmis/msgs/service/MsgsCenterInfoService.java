package com.cdqckj.gmis.msgs.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.msgs.dto.MsgsCenterInfoPageResultDTO;
import com.cdqckj.gmis.msgs.dto.MsgsCenterInfoQueryDTO;
import com.cdqckj.gmis.msgs.dto.MsgsCenterInfoSaveDTO;
import com.cdqckj.gmis.msgs.entity.MsgsCenterInfo;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 消息中心
 * </p>
 *
 * @author gmis
 * @date 2019-08-01
 */
public interface MsgsCenterInfoService extends SuperService<MsgsCenterInfo> {

    /**
     * 保存消息
     *
     * @param data
     * @return
     */
    MsgsCenterInfo saveMsgs(MsgsCenterInfoSaveDTO data);

    /**
     * 删除指定用户 指定消息的数据
     *
     * @param ids
     * @param userId
     * @return
     */
    boolean delete(List<Long> ids, Long userId);

    /**
     * 标记状态
     *
     * @param msgCenterIds 主表id
     * @param userId       用户id
     * @return
     */
    boolean mark(List<Long> msgCenterIds, Long userId);

    /**
     * 分页查询
     *
     * @param page
     * @param data
     * @return
     */
    IPage<MsgsCenterInfoPageResultDTO> page(IPage<MsgsCenterInfoPageResultDTO> page, MsgsCenterInfoQueryDTO data);
}
