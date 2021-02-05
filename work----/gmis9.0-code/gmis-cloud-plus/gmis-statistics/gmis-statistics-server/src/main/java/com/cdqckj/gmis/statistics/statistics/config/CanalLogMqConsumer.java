package com.cdqckj.gmis.statistics.statistics.config;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.statistics.domain.log.CoverMqBinlogData;
import com.cdqckj.gmis.statistics.domain.log.FormatRowData;
import com.cdqckj.gmis.statistics.domain.log.SysServiceBean;
import com.cdqckj.gmis.statistics.domain.table.ListenTable;
import com.rabbitmq.client.Channel;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author: lijianguo
 * @time: 2020/11/30 14:02
 * @remark: 请添加类说明
 */
@Log4j2
@Component
public class CanalLogMqConsumer {

    @Autowired
    ListenTable listenTable;

    @Autowired
    SysServiceBean sysServiceBean;

    @Autowired
    RedisTemplate redisTemplate;

    @RabbitHandler
    @RabbitListener(queues = "canal_queue", ackMode = "MANUAL")
    public void process(@Payload String message, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, Channel channel) throws IOException {
        // 告诉服务器收到这条消息 已经被我消费了 可以在队列删掉 这样以后就不会再发了 否则消息服务器以为这条消息没处理掉 后续还会在发
        JSONObject jsonData = JSONObject.parseObject(message);
        String schemaName = jsonData.getString("database");
        String table = jsonData.getString("table");
        String type = jsonData.getString("type");
        try {
            if (listenTable.userDataBaseInclude(schemaName) && listenTable.userTableInclude(table)){
                if (redisTemplate.hasKey("f_log")){
                    log.info("暂停消费 【{}】 表 【{}】",schemaName, table);
                    ThreadUtil.safeSleep(10 * 1000);
                    channel.basicReject(deliveryTag, true);
                    return;
                }
                log.info("数据库 【{}】 表 【{}】",schemaName, table);
                log.info("==============================================================================");
                Long startTime = System.currentTimeMillis();
                log.info("开始处理数据......【{}】 【{}】 【{}】 【{}】", LocalDateTime.now(), schemaName, table, type);
                CoverMqBinlogData coverBinlogData = new CoverMqBinlogData(type, schemaName, table, sysServiceBean);
                coverBinlogData.realProcessData(jsonData);
                log.info("处理数据成功......【{}】 【{}】 【{}】 【{}】", System.currentTimeMillis() - startTime, schemaName, table, type);
            }
            channel.basicAck(deliveryTag,false);
        } catch (Exception e) {
            log.info("处理数据失败......【{}】 【{}】 【{}】 【{}】", schemaName, table, type, deliveryTag);
            log.error(e);
            // 将消息放回队列重新消费
            ThreadUtil.safeSleep(10 * 1000);
            channel.basicReject(deliveryTag, true);
        }
    }
}