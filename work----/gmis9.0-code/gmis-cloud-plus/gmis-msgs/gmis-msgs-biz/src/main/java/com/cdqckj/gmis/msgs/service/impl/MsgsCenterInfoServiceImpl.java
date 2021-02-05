package com.cdqckj.gmis.msgs.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.msgs.service.MsgsCenterInfoReceiveService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.msgs.dao.MsgsCenterInfoMapper;
import com.cdqckj.gmis.msgs.dto.MsgsCenterInfoPageResultDTO;
import com.cdqckj.gmis.msgs.dto.MsgsCenterInfoQueryDTO;
import com.cdqckj.gmis.msgs.dto.MsgsCenterInfoSaveDTO;
import com.cdqckj.gmis.msgs.entity.MsgsCenterInfo;
import com.cdqckj.gmis.msgs.entity.MsgsCenterInfoReceive;
import com.cdqckj.gmis.msgs.service.MsgsCenterInfoService;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.utils.StrHelper.getOrDef;

/**
 * <p>
 * 业务实现类
 * 消息中心
 * </p>
 *
 * @author gmis
 * @date 2019-08-01
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class MsgsCenterInfoServiceImpl extends SuperServiceImpl<MsgsCenterInfoMapper, MsgsCenterInfo> implements MsgsCenterInfoService {
    @Autowired
    private MsgsCenterInfoReceiveService msgsCenterInfoReceiveService;

    @Override
    public IPage<MsgsCenterInfoPageResultDTO> page(IPage<MsgsCenterInfoPageResultDTO> page, MsgsCenterInfoQueryDTO data) {
        return baseMapper.page(page, data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MsgsCenterInfo saveMsgs(MsgsCenterInfoSaveDTO data) {
        MsgsCenterInfo info = BeanPlusUtil.toBean(data.getMsgsCenterInfoDTO(), MsgsCenterInfo.class);
        info.setTitle(getOrDef(info.getTitle(), info.getContent()));
        info.setAuthor(getOrDef(info.getAuthor(), BaseContextHandler.getName()));
        super.save(info);

        //公式公告，不会指定接收人
        Set<Long> userIdList = data.getUserIdList();
        if (CollectionUtil.isNotEmpty(userIdList)) {
            List<MsgsCenterInfoReceive> receiveList = userIdList.stream().map((userId) -> MsgsCenterInfoReceive.builder()
                    .isRead(false)
                    .userId(userId)
                    .msgsCenterId(info.getId())
                    .build()).collect(Collectors.toList());
            msgsCenterInfoReceiveService.saveBatch(receiveList);
        }
        return info;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(List<Long> ids, Long userId) {
        boolean bool = msgsCenterInfoReceiveService.remove(Wraps.<MsgsCenterInfoReceive>lbQ()
                .eq(MsgsCenterInfoReceive::getUserId, userId)
                .in(MsgsCenterInfoReceive::getMsgsCenterId, ids));

        for (Long msgCenterId : ids) {
            int count = msgsCenterInfoReceiveService.count(Wraps.<MsgsCenterInfoReceive>lbQ()
                    .eq(MsgsCenterInfoReceive::getMsgsCenterId, msgCenterId));
            log.info("count={}", count);
            if (count <= 0) {
                super.remove(Wraps.<MsgsCenterInfo>lbQ().eq(MsgsCenterInfo::getId, msgCenterId));
            }
        }
        return bool;
    }

    /**
     * 修改状态
     * 公告的已读，新增记录
     * <p>
     * 其他的更新状态
     *
     * @param msgCenterIds 主表id
     * @param userId       用户id
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean mark(List<Long> msgCenterIds, Long userId) {
        if (CollectionUtil.isEmpty(msgCenterIds) || userId == null) {
            return true;
        }
        List<MsgsCenterInfo> list = super.listByIds(msgCenterIds);

        //其他类型的修改状态
        if (!list.isEmpty()) {
            List<Long> idList = list.stream().mapToLong(MsgsCenterInfo::getId).boxed().collect(Collectors.toList());
            return msgsCenterInfoReceiveService.update(Wraps.<MsgsCenterInfoReceive>lbU()
                    .eq(MsgsCenterInfoReceive::getUserId, userId)
                    .in(MsgsCenterInfoReceive::getMsgsCenterId, idList)
                    .set(MsgsCenterInfoReceive::getIsRead, true)
                    .set(MsgsCenterInfoReceive::getUpdateTime, LocalDateTime.now())
                    .set(MsgsCenterInfoReceive::getUpdateUser, userId)
            );
        }
        return true;
    }

}
