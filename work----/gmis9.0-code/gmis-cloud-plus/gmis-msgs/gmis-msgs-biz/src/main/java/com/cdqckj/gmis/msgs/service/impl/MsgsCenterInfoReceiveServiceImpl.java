package com.cdqckj.gmis.msgs.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.msgs.service.MsgsCenterInfoReceiveService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.msgs.dao.MsgsCenterInfoReceiveMapper;
import com.cdqckj.gmis.msgs.entity.MsgsCenterInfoReceive;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 消息中心 接收表
 * 全量数据
 * </p>
 *
 * @author gmis
 * @date 2019-08-01
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class MsgsCenterInfoReceiveServiceImpl extends SuperServiceImpl<MsgsCenterInfoReceiveMapper, MsgsCenterInfoReceive> implements MsgsCenterInfoReceiveService {

}
