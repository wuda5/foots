package com.cdqckj.gmis.iot.qc.dto;


import lombok.Data;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 物联网对接接收3.0阀门控制数据模型
 * @author: 秦川物联网科技
 * @date: 2020/10/21  13:07
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class ValveControlModel extends BaseCommandModel implements Serializable {
    /**
     * 阀门控制
     */
    private ValveControlBus businessData;
}
