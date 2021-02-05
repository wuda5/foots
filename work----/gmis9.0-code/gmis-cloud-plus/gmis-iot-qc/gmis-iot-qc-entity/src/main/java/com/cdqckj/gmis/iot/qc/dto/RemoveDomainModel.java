package com.cdqckj.gmis.iot.qc.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 物联网对接接收3.0移除设备数据模型
 * @author: 秦川物联网科技
 * @date: 2020/10/27  13:53
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
public class RemoveDomainModel implements Serializable {
    /**
     * 唯一标识
     */
    private String domainId;
    /**
     * 档案id
     */
    private String deviceType;
    /**
     * 设备id数组
     */
    private List<RemoveDomainBus> data;
}
