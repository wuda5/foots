package com.cdqckj.gmis.iot.qc.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import com.cdqckj.gmis.iot.qc.entity.MessageType;
import io.lettuce.core.models.command.CommandDetail;
import io.swagger.annotations.ApiModel;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 物联网对接消息队列消息类
 * @author: 秦川物联网科技
 * @date: 2020/10/13  21:20
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
public class MessageDTO implements Serializable {
    private String message;
    private MessageType messageType;
    private String tenant;

    @Builder
    public MessageDTO(String message,MessageType messageType,String tenant){
        this.message = message;
        this.messageType = messageType;
        this.tenant = tenant;
    }
}
