package com.cdqckj.gmis.tenant.strategy.impl;

/**
 * @author: lijianguo
 * @time: 2020/12/09 13:20
 * @remark: 请添加类说明
 */

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.common.constant.BizMqQueue;
import com.cdqckj.gmis.exception.BizException;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @Author: lijiangguo
 * @Date: 2020/12/9 11:20
 * @remark 初始化数据的线程--内部类
 */
@Log4j2
public class InitDataBaseThread implements Runnable{

    /** 操作 **/
    private DatasourceInitSystemStrategy strategy;
    /** 编码 **/
    private String tenant;
    /** mq **/
    RabbitTemplate rabbitTemplate;

    public InitDataBaseThread(DatasourceInitSystemStrategy strategy, String tenant, RabbitTemplate rabbitTemplate) {
        this.strategy = strategy;
        this.tenant = tenant;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run() {
        JSONObject param = new JSONObject();
        param.put("tenant", tenant);
        log.info("新建租户初始化脚本");
        try {
            // 初始数据库
            //strategy.initDatabases(tenant);
            // 初始化表
            strategy.initTables(tenant);
            // 初始化数据
            strategy.initData(tenant);
            // 切换为默认数据源
            strategy.resetDatabase();
            param.put("initStatus", 2);
            param.put("initFailMsg", "初始化成功");
        }catch (BizException e){
            log.error("BizException {}",e.getMessage());
            log.error(e);
            param.put("initStatus", 3);
            param.put("initFailMsg", e.getMessage());
        }catch (Exception e){
            log.error("Exception {}",e.getMessage());
            log.error(e);
            param.put("initStatus", 3);
            param.put("initFailMsg", e.getMessage());
        }
        // 广播信息，各个项目从新同步数据
        strategy.getInitDsService().initDataSource(tenant);
        // 休息120s
        ThreadUtil.safeSleep(120 * 1000);
        rabbitTemplate.convertAndSend(BizMqQueue.NEW_TENANT_FANOUT_EXCHANGE, null, param.toString());
    }
}