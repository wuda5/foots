package com.cdqckj.gmis.statistics.statistics.config;

import cn.hutool.core.thread.ThreadUtil;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.cdqckj.gmis.authority.api.TenantBizApi;
import com.cdqckj.gmis.common.domain.machine.EnvProperty;
import com.cdqckj.gmis.statistics.domain.log.*;
import com.cdqckj.gmis.statistics.domain.table.ListenTable;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/22 13:30
 * @remark: 处理数据库日志
 */
@Log4j2
//@Component
public class CanalClientRun {

    @Autowired
    SysServiceBean sysServiceBean;

    @Autowired
    DatabaseProperty databaseProperty;

    @Autowired
    ConfigurableApplicationContext application;

    @Autowired
    TenantBizApi tenantBizApi;

    @Autowired
    EnvProperty envProperty;

    TableLogEntityProduce tableLogFactory = new TableLogEntityProduceDefault();

    @PostConstruct
    public void canalClientConfigInitOtherThread(){

        Thread syncMysqlLog = new Thread(new SyncMysqlLog());
        // syncMysqlLog.run();
        // 只在开发环境同步数据，后期优化--要从mq取数据
//        if (envProperty.devEnv()) {
//            syncMysqlLog.start();
//        }

    }

    /**
     * @auth lijianguo
     * @date 2020/10/22 15:29
     * @remark 同步日志的线程
     */
    public class SyncMysqlLog implements Runnable{

        @Override
        public void run() {
            log.info("日志同步启动");
            //InetSocketAddress address = new InetSocketAddress("127.0.0.1",11111);
            InetSocketAddress address = new InetSocketAddress(databaseProperty.getIp(),11111);
            CanalConnector connector = CanalConnectors.newSingleConnector(address, "example", "", "");

            int batchSize = 5;
            try {
                connector.connect();
//                connector.subscribe("gmis_base_0000.gt_charge_record,gmis_base_0000.gt_invoice");
                connector.subscribe(ListenTable.getCanalAllListenName(Arrays.asList("gmis_base_0000")));
                connector.rollback();
                while (true) {
                    Message message = connector.getWithoutAck(batchSize);
                    long batchId = message.getId();
                    int size = message.getEntries().size();
                    if (batchId == -1 || size == 0) {
                        ThreadUtil.safeSleep(10000);
                    } else {
                        CoverBinlogData cbd = printEntry(message.getEntries());
                        if (cbd != null) {
                            try {
                                FormatRowData formatRowData = cbd.processData(tableLogFactory);
                            } catch (HystrixRuntimeException e){
                                log.error(e);
                                log.error("===========================================================");
                                log.info("上游服务器未启动......【{}】【{}】 【{}】【{}】", batchId, cbd.getSchemaName(), cbd.getTableName(), cbd.getOpType());
                                ThreadUtil.safeSleep(200 * 1000);
                            } catch (Exception e) {
                                log.error(e);
                                log.error("===========================================================");
                                log.info("处理数据失败sleep......【{}】【{}】 【{}】【{}】", batchId, cbd.getSchemaName(), cbd.getTableName(), cbd.getOpType());
                                ThreadUtil.safeSleep(20 * 1000);
                            }
                        }
                    }
                    //log.info("获取消息 数量为:【{}】 时间是:【{}】", size, DateUtil.now());
                    connector.ack(batchId);
                }
            }catch (Exception e){
                log.error("数据库日志同步异常,停止数据同步");
                log.error(e);
            }finally {
                connector.disconnect();
            }
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/10/22 15:45
     * @remark 打印消息
     */
    private CoverBinlogData printEntry(List<CanalEntry.Entry> entryList) {
        for (CanalEntry.Entry entry : entryList) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                //开启/关闭事务的实体类型，跳过
                continue;
            }
            String schemaName = entry.getHeader().getSchemaName();
            String tableName = entry.getHeader().getTableName();
            String opType = entry.getHeader().getEventType().name();
            log.info("日志详情数据库【{}】 表【{}】 类型【{}】", schemaName, tableName, opType.toLowerCase());
            CanalEntry.RowChange rowChange;
            try {
                rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(), e);
            }
            //判断是否是DDL语句
            if (rowChange.getIsDdl()) {
                log.info("isDdl: true,sql: 【{}】",rowChange.getSql());
            }
            for (CanalEntry.RowData rowData : rowChange.getRowDatasList()) {
                CoverBinlogData binlogData = new CoverBinlogData(rowData, schemaName, tableName, sysServiceBean);
                //如果是删除语句
                if (rowChange.getEventType() == CanalEntry.EventType.DELETE) {
                    binlogData.setOpType("delete");
                //如果是新增语句
                } else if (rowChange.getEventType() == CanalEntry.EventType.INSERT) {
                    binlogData.setOpType("insert");
                //如果是更新的语句
                } else if (rowChange.getEventType() == CanalEntry.EventType.UPDATE){
                    binlogData.setOpType("update");
                }else {
                    log.info("这个操作没有记录 {}",rowChange.getEventType());
                }
                return binlogData;
            }
        }
        return null;
    }

}
