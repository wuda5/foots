package com.cdqckj.gmis.common.domain.search;

import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.common.utils.ReflectionUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;

/**
 * @author: lijianguo
 * @time: 2020/11/17 9:41
 * @remark: 生成搜索的sql参数--一般把公共的提取出来
 */
@Data
@Log4j2
@ApiModel("生成搜索的sql参数")
public class StsSearchParam implements Serializable, StsAutoSql {

    @ApiModelProperty(value = "租户CODE")
    @StsField(value = "t_code", op = "=")
    private String tCode;

    @ApiModelProperty(value = "公司CODE")
    @StsField(value = "company_code", op = "=")
    private String companyCode;

    @ApiModelProperty(value = "组织ID")
    @StsField(value = "org_id", op = "=")
    private Long orgId;

    @ApiModelProperty(value = "营业厅ID")
    @StsField(value = "business_hall_id", op = "=")
    private Long businessHallId;

    @ApiModelProperty(value = "收费员ID")
    @StsField(value = "create_user_id", op = "=")
    private Long createUserId;

    @ApiModelProperty(value = "开始时间")
    @StsField(value = "sts_day", op = ">=")
    private LocalDate startDay;

    @ApiModelProperty(value = "统计的是哪一天的数据")
    @StsField(value = "sts_day", op = "=")
    private LocalDate stsDay;

    @ApiModelProperty(value = "结束时间")
    @StsField(value = "sts_day", op = "<")
    private LocalDate endDay;

    @ApiModelProperty(value = "第几页")
    private Long pageNo;

    @ApiModelProperty(value = "每页条数")
    private Long pageSize;

    @ApiModelProperty(value = "用来保存数据的map")
    private Map dataMap;

    @Override
    public String createSearchSql() {

        StringBuilder sb = new StringBuilder();
        Field[] fieldList = this.getClass().getDeclaredFields();
        for (Field field: fieldList) {
            String filedName = field.getName();
            Object value = ReflectUtil.getFieldValue(this, filedName);
            StsField stsField = field.getAnnotation(StsField.class);
            if (stsField == null || value == null){
                continue;
            }
            // 空的字符串不计算
            if (value instanceof String){
                String str = (String)value;
                if (StringUtils.isBlank(str)){
                    continue;
                }
            }
            sb.append(" AND ");
            sb.append(stsField.value()).append(" ");
            sb.append(stsField.op()).append(" ");
            sb.append("#{stsSearchParam.").append(filedName).append("}");
        }
        return sb.toString();
    }

    public StsSearchParam(StsSearchParam param) {
        this.tCode = param.tCode;
        this.companyCode = param.companyCode;
        this.orgId = param.orgId;
        this.businessHallId = param.businessHallId;
        this.createUserId = param.createUserId;
        this.startDay = param.startDay;
        this.stsDay = param.stsDay;
        this.endDay = param.endDay;
    }

    public StsSearchParam() {
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 15:55
    * @remark 添加查询的字段
    */
    public void addSearchKey(String key, String value){
        if (this.dataMap == null){
            this.dataMap = new HashMap();
        }
        this.dataMap.put(key, value);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 15:59
    * @remark 获取查询的key
    */
    public String getSearchKeyValue(String key){
        if (this.dataMap == null){
            return null;
        }
        Object o = this.dataMap.get(key);
        if (o == null){
            return null;
        }else {
            return String.valueOf(o);
        }
    }
}
