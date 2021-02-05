package com.cdqckj.gmis.devicearchive.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.cdqckj.gmis.common.domain.excell.FailNumInfo;
import com.cdqckj.gmis.common.domain.excell.RowFailDescribe;
import com.google.common.base.Joiner;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)

@ApiModel(value = "GasmeterPoi", description = "")
public class GasmeterPoi implements RowFailDescribe {
    @ApiModelProperty(value = "厂家名称")
    @Excel(name = "厂家名称(必填)")
    private String gasMeterFactoryName;


    @ApiModelProperty(value = "版号名称")
    @Excel(name = "版号名称(必填)")
    private String gasMeterVersionName;

    @ApiModelProperty(value = "型号名称")
    @Excel(name = "型号名称(必填)")
    private String gasMeterModelName;

    /**
     * 通气方向
     */
    @ApiModelProperty(value = "通气方向")
    @Excel(name = "通气方向(必填)")
    private String direction;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Excel(name = "表身号(卡表，普表(有就必填，没有不填)，物联网必填)")
    private String gasMeterNumber;

    /**
     * 检定人
     */
    @ApiModelProperty(value = "检定人")
    @Excel(name = "检定人")
    private String checkUser;

    /**
     * 检定时间
     */
    @ApiModelProperty(value = "检定时间")
    @Excel(name = "检定时间",isImportField = "true",exportFormat = "yyyy-MM-dd", importFormat =  "yyyy-MM-dd" ,databaseFormat ="yyyy-MM-dd" ,type = 1)
    private String checkTime;

    @ApiModelProperty(value = "购买时间")
    @Excel(name = "购买时间", isImportField = "true",exportFormat = "yyyy-MM-dd", importFormat =  "yyyy-MM-dd" ,databaseFormat ="yyyy-MM-dd",type = 1)
    private String buyTime;

    @ApiModelProperty(value = "防盗扣编号")
    @Excel(name = "防盗扣编号")
    private String safeCode;

    /**
     * 气表底数
     */
    @ApiModelProperty(value = "气表底数")
    @Excel(name = "气表底数")
    private BigDecimal gasMeterBase;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    @Excel(name = "备注")
    private String remark;

    private String failValue;

    @Override
    public void setMsg(List<FailNumInfo> infoList) {
        failValue = Joiner.on(",").join(infoList.stream().map(FailNumInfo::getFailDescribe).collect(Collectors.toList()));
    }
}
