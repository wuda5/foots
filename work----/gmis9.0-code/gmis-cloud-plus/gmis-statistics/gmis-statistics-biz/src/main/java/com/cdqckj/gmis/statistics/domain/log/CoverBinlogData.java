package com.cdqckj.gmis.statistics.domain.log;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.cdqckj.gmis.charges.entity.ChargeRecord;
import com.cdqckj.gmis.common.utils.ReflectionUtil;
import com.cdqckj.gmis.statistics.domain.log.util.EntityFieldUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/23 17:14
 * @remark: 请输入类说明
 */
@Data
@Log4j2
public class CoverBinlogData{

    @ApiModelProperty(value = "获取的数据的值")
    CanalEntry.RowData rowData;

    @ApiModelProperty(value = "1:insert 2:update 3:delete 4:create")
    String opType;

    @ApiModelProperty(value = "数据库的名字")
    String schemaName;

    @ApiModelProperty(value = "表的名字")
    String tableName;

    @ApiModelProperty(value = "所有的bean")
    SysServiceBean sysServiceBean;

    public CoverBinlogData(CanalEntry.RowData rowData ,String dbName, String tableName, SysServiceBean sysServiceBean) {
        this(rowData, null, dbName, tableName, sysServiceBean);
    }

    public CoverBinlogData(CanalEntry.RowData rowData, String opType, String schemaName, String tableName,SysServiceBean sysServiceBean) {
        this.rowData = rowData;
        this.opType = opType;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.sysServiceBean = sysServiceBean;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/25 9:04
     * @remark 开始处理数据
     */
    public FormatRowData processData(TableLogEntityProduce logEntityProduce){
        Long startTime = System.currentTimeMillis();
        log.info("开始处理数据......【{}】 【{}】 【{}】",schemaName, tableName, opType);
        logEntityProduce.switchDatabase(schemaName);
        TableBaseLog processTableLog = logEntityProduce.entityFactory(tableName, sysServiceBean);

        FormatRowData formatRowData = new FormatRowData();
        if (processTableLog != null){
            Class cl = processTableLog.getEntityClass();
            if (cl == null){
                throw new RuntimeException("请确保该实体存在");
            }
            if (opType.equals("insert") && processTableLog instanceof TableInsertLog){

                Object beforeObject = ReflectionUtil.createClassObject(cl);
                setBeanFiledValue(beforeObject, rowData.getBeforeColumnsList(), cl, logEntityProduce);
                formatRowData.setBeforeRowValue(beforeObject);

                Object afterObject = ReflectionUtil.createClassObject(cl);
                setBeanFiledValue(afterObject, rowData.getAfterColumnsList(), cl, logEntityProduce);
                formatRowData.setAfterRowValue(afterObject);

                List<FormatColData> dataList = changCol(rowData.getAfterColumnsList());
                formatRowData.setColFieldList(dataList);

                if (processTableLog.logNeedProcess(formatRowData, ProcessTypeEnum.PROCESS_INSERT)){
                    ((TableInsertLog)processTableLog).insertLog(formatRowData);
                }
            }else if (opType.equals("update") && processTableLog instanceof TableUpdateLog) {

                Object beforeObject = ReflectionUtil.createClassObject(cl);
                setBeanFiledValue(beforeObject, rowData.getBeforeColumnsList(), cl, logEntityProduce);
                formatRowData.setBeforeRowValue(beforeObject);

                Object afterObject = ReflectionUtil.createClassObject(cl);
                setBeanFiledValue(afterObject, rowData.getAfterColumnsList(), cl, logEntityProduce);
                formatRowData.setAfterRowValue(afterObject);

                List<FormatColData> dataList = changCol(rowData.getAfterColumnsList());
                formatRowData.setColFieldList(dataList);

                if (processTableLog.logNeedProcess(formatRowData, ProcessTypeEnum.PROCESS_UPDATE)) {
                    ((TableUpdateLog) processTableLog).updateLog(formatRowData);
                }
            }
        }
        log.info("处理数据成功......【{}】 【{}】【{}】 用时间:【{}】",schemaName, tableName, opType ,System.currentTimeMillis() - startTime);
        return formatRowData;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/25 10:06
     * @remark 设置bean的值
     */
    private void setBeanFiledValue(Object object, List<CanalEntry.Column> columns, Class cl, TableLogEntityProduce logEntityProduce){

        for (CanalEntry.Column column : columns) {

            String entityFieldName = EntityFieldUtil.fieldToCamelCase(column.getName());
            Class objectClass = EntityFieldUtil.getFiledClass(tableName, entityFieldName, cl);
            String value = column.getValue();
            if (objectClass != null && StringUtils.isNotBlank(value)){

                try {
                    Field field = object.getClass().getField(entityFieldName);
                    if (field == null){
                        log.info("实体字段不存在 {} {}",entityFieldName, object.getClass().getName());
                    }
                    value = value.trim();
                    ReflectUtil.setFieldValue(object, entityFieldName, value);
//                    ReflectionUtil.setFieldValueByFieldName(object, objectClass, entityFieldName, value);
                } catch (Exception e) {
                    log.error("实体转换的赋值错误 {} {}", entityFieldName, object.getClass());
                }
            }
        }
        logEntityProduce.entityGeneric(object);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/25 15:17
     * @remark 生成修改的字段
     */
    private List<FormatColData> changCol(List<CanalEntry.Column> columns){

        List<FormatColData> dataList = new ArrayList<>(columns.size());
        for(CanalEntry.Column column: columns){

            FormatColData colChange = new FormatColData();
            String filedName = EntityFieldUtil.fieldToCamelCase(column.getName());
            colChange.setFieldName(filedName);
            if (column.getUpdated()){
                colChange.setChange(true);
            }else {
                colChange.setChange(false);
            }
            dataList.add(colChange);
        }
        return dataList;
    }

}
