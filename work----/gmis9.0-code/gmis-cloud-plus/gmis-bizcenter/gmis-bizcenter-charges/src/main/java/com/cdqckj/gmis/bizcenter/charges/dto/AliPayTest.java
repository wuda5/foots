package com.cdqckj.gmis.bizcenter.charges.dto;

import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@ApiModel(value = "AliPayTest", description = "阿里生活支付-测试实体")
public class AliPayTest {
    /**
     * 清算单位
     */
    private String acctOrgNo;
    /**
     * 查询条件的类别：0－按用户编号查询，1－按手机号码查询，2－按身份证号码查询，3－按原户号查询；（默认值：0）
     */
    private String queryType;
    /**
     * 对应查询类别的查询条件。
     */
    private String queryValue;
    /**
     * 开始年月（可以缺省，目前暂无用）
     */
    private String bgnYm;
    /**
     * 结束年月（可以缺省，目前暂无用）
     */
    private String endYm;
    /**
     * 费用类型，默认值：11,表示水电燃，热力等主营费用
     */
    private String busiType;
    /**
     * 预留
     */
    private String extend;
}
