package com.cdqckj.gmis;

import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.authority.entity.core.Org;
import com.cdqckj.gmis.common.constant.InjectionFieldConstants;
import com.cdqckj.gmis.injection.annonation.InjectionField;
import com.cdqckj.gmis.model.RemoteData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@ToString
/**
 * 测试DTO
 *
 * @author gmis
 * @date 2019/07/25
 */
public class TestModel {
    private LocalDateTime date;
    private Date d2;

    @ApiModelProperty(value = "组织ID")
    @TableField("org_id")
//    @InjectionField(feign = OrgApi.class, method = ORG_ID_METHOD)
    private RemoteData<Long, Org> org;


    //    @InjectionField(api = "orgApi", method = "findOrgNameByIds")
    private RemoteData<Long, String> org2;


    @InjectionField(feign = Object.class, method = "findOrgNameByIds")
    private RemoteData<Long, String> error;

    @InjectionField(api = "stationServiceImpl", method = InjectionFieldConstants.STATION_ID_METHOD)
    private RemoteData<Long, Org> station;

    // 去数据字典表 根据code 查询 name
    @InjectionField(api = "dictionaryItemServiceImpl", method = "findDictionaryItem")
    private RemoteData<String, String> education;

    @InjectionField(api = "dictionaryItemServiceImpl", method = "findDictionaryItem")
    private String education2;

}
