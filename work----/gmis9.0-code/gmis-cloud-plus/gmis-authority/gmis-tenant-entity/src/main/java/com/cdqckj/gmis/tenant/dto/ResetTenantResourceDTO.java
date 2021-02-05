package com.cdqckj.gmis.tenant.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableField;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.common.domain.Separate.SeparateKey;
import com.cdqckj.gmis.tenant.entity.Tenant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.baomidou.mybatisplus.annotation.SqlCondition.LIKE;

/**
 * @author: lijianguo
 * @time: 2020/10/9 11:15
 * @remark: 请输入类说明
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "ResetTenantResourceDTO", description = "资源重新分配给租户")
public class ResetTenantResourceDTO implements Serializable {

    @ApiModelProperty(value = "这个资源的Id")
    @NotNull(message = "资源id不能为空", groups = SuperEntity.Update.class)
    private Long resourceId;

    @ApiModelProperty(value = "租户列表")
    private List<TenantInfo> tenantInfoList = new ArrayList<>();


    /**
     * @auth lijianguo
     * @date 2020/10/9 11:38
     * @remark 请输入备注
     */
    @Data
    public static class TenantInfo implements SeparateKey {

        @ApiModelProperty(value = "企业编码")
        private String code;

        @ApiModelProperty(value = "企业名称")
        private String name;

        @Override
        public String indexKey() {
            return code;
        }
    }
}
