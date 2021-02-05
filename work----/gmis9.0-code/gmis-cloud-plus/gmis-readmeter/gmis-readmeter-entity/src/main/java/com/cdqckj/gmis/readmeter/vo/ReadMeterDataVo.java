package com.cdqckj.gmis.readmeter.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.cdqckj.gmis.common.domain.excell.RowFailDescribe;
import com.cdqckj.gmis.common.domain.excell.FailNumInfo;
import com.cdqckj.gmis.log.annotation.ExcelSelf;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Joiner;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 实体类
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @since 2020-07-13
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "ReadMeterDataVo", description = "抄表数据")
@AllArgsConstructor
public class ReadMeterDataVo implements RowFailDescribe {

    /**
     * 客户编号
     */
    @ApiModelProperty(value = "客户缴费编号")
    @Excel(name = "客户缴费编号")
    @ExcelSelf(name = "客户缴费编号,customer ChargeNo")
    private String customerChargeNo;

    /**
     * 客户名称
     */
    @ApiModelProperty(value = "客户名称")
    @Excel(name = "客户名称")
    @ExcelSelf(name = "客户名称,customer name")
    private String customerName;

    /**
     * 气表code
     */
    @ApiModelProperty(value = "气表code")
    @Excel(name = "气表code")
    @ExcelSelf(name = "气表code,Gas meter code")
    private String gasMeterCode;

    /**
     * 表身号
     */
    @ApiModelProperty(value = "表身号")
    @Excel(name = "表身号")
    @ExcelSelf(name = "表身号,Body number")
    private String gasMeterNumber;

    /**
     * 安装地址
     */
    @ApiModelProperty(value = "安装地址")
    @Excel(name = "安装地址")
    @ExcelSelf(name = "安装地址,Installation address")
    private String moreGasMeterAddress;

    /**
     * 抄表员名称
     */
    @ApiModelProperty(value = "抄表员名称")
    @Excel(name = "抄表员名称")
    @ExcelSelf(name = "抄表员名称,Name of meter reader")
    private String readMeterUserName;

    /**
     * 本期止数
     */
    @ApiModelProperty(value = "本期止数")
    @Excel(name = "本期止数")
    @ExcelSelf(name = "本期止数,End of current period")
    private BigDecimal currentTotalGas;

    /**
     * 抄表时间
     */
    @ApiModelProperty(value = "抄表时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "抄表时间")
    private LocalDate recordTime;

    private String failValue;

    @Override
    public void setMsg(List<FailNumInfo> infoList) {
        failValue = Joiner.on(",").join(infoList.stream().map(FailNumInfo::getFailDescribe).collect(Collectors.toList()));
    }
}
