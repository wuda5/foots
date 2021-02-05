package com.cdqckj.gmis.systemparam.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.systemparam.dao.TollItemMapper;
import com.cdqckj.gmis.systemparam.dto.TollItemSaveDTO;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import com.cdqckj.gmis.systemparam.service.TollItemService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * <p>
 * 业务实现类
 * 收费项配置表
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class TollItemServiceImpl extends SuperServiceImpl<TollItemMapper, TollItem> implements TollItemService {
    @Override
    public Boolean check(TollItemSaveDTO tollItemSaveDTO) {
        return super.count(Wraps.<TollItem>lbQ().eq(TollItem::getSceneCode,tollItemSaveDTO.getSceneCode()))>0;
    }

    @Override
    public Boolean existTollItem(String sceneCode) {
        LocalDateTime localDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
        LbqWrapper<TollItem> wrapper = Wraps.<TollItem>lbQ()
                .eq(TollItem::getSceneCode, sceneCode)
                .eq(TollItem::getDataStatus, DataStatusEnum.NORMAL.getValue())
                .eq(TollItem::getDeleteStatus, DeleteStatusEnum.NORMAL.getValue())
                .le(TollItem::getStartTime, localDate);

        return super.count(wrapper)>0;
    }
}
