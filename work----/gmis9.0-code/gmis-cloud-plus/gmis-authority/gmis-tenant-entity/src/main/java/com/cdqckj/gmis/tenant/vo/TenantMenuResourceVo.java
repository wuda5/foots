package com.cdqckj.gmis.tenant.vo;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.common.domain.Separate.SeparateKey;
import com.cdqckj.gmis.common.domain.tenant.SetTenantInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

import java.io.Serializable;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * @author: lijianguo
 * @time: 2020/10/15 13:47
 * @remark: 请输入类说明
 */
@Data
public class TenantMenuResourceVo implements Serializable, SeparateKey {

    @ApiModelProperty(value = "1拥有 0没有")
    private Boolean holdAuthority;

    @ApiModelProperty(value = "资源的Id")
    private Long id;

    @ApiModelProperty(value = "权限的名称")
    private String name;

    @ApiModelProperty(value = "菜单ID")
    private Long menuId;

    @Override
    public String indexKey() {
        return String.valueOf(id);
    }
}
