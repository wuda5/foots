package com.cdqckj.gmis.statistics.domain.log;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.db.Entity;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.cdqckj.gmis.common.utils.ReflectionUtil;
import com.cdqckj.gmis.statistics.domain.log.util.EntityFieldUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.util.EntityUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author: lijianguo
 * @time: 2020/10/23 17:14
 * @remark: 请输入类说明
 */
@Data
@Log4j2
public class CoverMqBinlogData implements BinlogData {

    @ApiModelProperty(value = "获取的数据的值")
    JSONObject objectData;

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

    public CoverMqBinlogData(String opType, String schemaName, String tableName, SysServiceBean sysServiceBean) {
        this.opType = opType;
        this.schemaName = schemaName;
        this.tableName = tableName;
        this.sysServiceBean = sysServiceBean;
    }

    @Override
    public FormatRowData realProcessData(Serializable serializable) {

        objectData = (JSONObject) serializable;
        TableLogEntityProduce logEntityProduce = new TableLogEntityProduceDefault();
        logEntityProduce.switchDatabase(schemaName);
        TableBaseLog processTableLog = logEntityProduce.entityFactory(tableName, sysServiceBean);

        FormatRowData formatRowData = new FormatRowData();
        if (processTableLog != null){
            Class cl = processTableLog.getEntityClass();
            if (cl == null){
                throw new RuntimeException("请确保该实体存在");
            }
            if (opType.equalsIgnoreCase("insert") && processTableLog instanceof TableInsertLog){
                Object afterObject = ReflectionUtil.createClassObject(cl);
                JSONArray afterArray = objectData.getJSONArray("data");
                setBeanFiledValue(afterObject, afterArray, cl, logEntityProduce);
                formatRowData.setAfterRowValue(afterObject);

                Object beforeObject = BeanUtil.copyProperties(afterObject, afterObject.getClass());
                formatRowData.setBeforeRowValue(beforeObject);

                List<FormatColData> dataList = changCol(afterArray);
                formatRowData.setColFieldList(dataList);

                if (processTableLog.logNeedProcess(formatRowData, ProcessTypeEnum.PROCESS_INSERT)){
                    ((TableInsertLog)processTableLog).insertLog(formatRowData);
                }
            }else if (opType.equalsIgnoreCase("update") && processTableLog instanceof TableUpdateLog) {
                Object afterObject = ReflectionUtil.createClassObject(cl);
                JSONArray afterArray = objectData.getJSONArray("data");
                setBeanFiledValue(afterObject, afterArray, cl, logEntityProduce);
                formatRowData.setAfterRowValue(afterObject);

                Object beforeObject = BeanUtil.copyProperties(afterObject, afterObject.getClass());
                JSONArray beforeArray = objectData.getJSONArray("old");
                setBeanFiledValue(beforeObject, beforeArray, cl, logEntityProduce);
                formatRowData.setBeforeRowValue(beforeObject);

                List<FormatColData> dataList = changCol(beforeArray);
                formatRowData.setColFieldList(dataList);

                if (processTableLog.logNeedProcess(formatRowData, ProcessTypeEnum.PROCESS_UPDATE)) {
                    ((TableUpdateLog) processTableLog).updateLog(formatRowData);
                }
            }else {
                log.info("未处理数据......【{}】 【{}】 【{}】", schemaName, tableName, opType);
            }
        }

        return formatRowData;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/25 10:06
     * @remark 设置bean的值
     */
    private void setBeanFiledValue(Object object, JSONArray jsonArray, Class cl, TableLogEntityProduce logEntityProduce) {

        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            for (Map.Entry entry : jsonObject.entrySet()) {
                String key = entry.getKey().toString();
                Object value = entry.getValue();
                String fieldName = EntityFieldUtil.fieldToCamelCase(key);
                Class objectClass = EntityFieldUtil.getFiledClass(tableName, fieldName, cl);
                if (objectClass != null) {
                    try {
                        if (EntityFieldUtil.classFieldExist(objectClass, fieldName)){
                            ReflectionUtil.setFieldValueByFieldName(object, objectClass, fieldName, value);
                        }
                    } catch (Exception e) {
                        log.error("实体转换的赋值错误 {} {}", fieldName, object.getClass());
                    }
                }
            }
            logEntityProduce.entityGeneric(object);
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/10/25 15:17
     * @remark 生成修改的字段
     */
    private List<FormatColData> changCol(JSONArray jsonArray){

        List<FormatColData> dataList = new ArrayList<>(jsonArray.size());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            for (Map.Entry entry : jsonObject.entrySet()) {
                String key = entry.getKey().toString();
                String entityFieldName = EntityFieldUtil.fieldToCamelCase(key);
                FormatColData colChange = new FormatColData();
                colChange.setFieldName(entityFieldName);
                colChange.setChange(true);
                dataList.add(colChange);
            }
        }
        return dataList;
    }

}
