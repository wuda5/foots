package com.cdqckj.gmis.iot.qc.qnms.mq;

import com.cdqckj.gmis.iot.qc.dto.MessageDTO;
import com.cdqckj.gmis.iot.qc.qnms.constant.GMISConstant;
import com.cdqckj.gmis.iot.qc.qnms.utils.MQUtils;
import com.cdqckj.gmis.iot.qc.service.MessageService;
import com.cdqckj.gmis.mq.properties.MqProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: rabbitmq消息监听器
 * @author: 秦川物联网科技
 * @date: 2020/10/15 13:51
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
//@Component
@Slf4j
//@ConditionalOnProperty(prefix = MqProperties.PREFIX, name = "enabled", havingValue = "true")
public class IotRabbitListener {

    @Autowired
    private MessageService messageService;

    @RabbitHandler
    @RabbitListener(queues = GMISConstant.GMIS_QUEUE_IOT_QC)
    public void immediateProcess(byte[] bytes) throws Exception {
        MessageDTO messageDTO = (MessageDTO) MQUtils.getObjectFromBytes(bytes);
        log.info("Receive:" + messageDTO.getMessage());
        switch (messageDTO.getMessageType()) {
            case DeviceData:
                messageService.deviceDataEvent(messageDTO.getMessage(),messageDTO.getTenant());
                break;
            case BusinessData:
                messageService.businessDataEvent(messageDTO.getMessage(),messageDTO.getTenant());
                break;
            case DeviceAdd:
                messageService.deviceAddEvent(messageDTO.getMessage(),messageDTO.getTenant());
                break;
            case DeviceRegistry:
                messageService.registerDeviceEvent(messageDTO.getMessage(),messageDTO.getTenant());
                break;
            case OpenAccount:
                messageService.openAccountEvent(messageDTO.getMessage(),messageDTO.getTenant());
                break;
            case ValveControl:
                messageService.valveControlEvent(messageDTO.getMessage(),messageDTO.getTenant());
                break;
//            case Recharge:
//                messageService.rechargeEvent(messageDTO.getMessage(),messageDTO.getTenant());
//                break;
            case ChangePrice:
                messageService.changePriceEvent(messageDTO.getMessage(),messageDTO.getTenant());
                break;
            case DeviceRemove:
                messageService.removeDeviceEvent(messageDTO.getMessage(),messageDTO.getTenant());
                break;
            case UpdateBalance:
                messageService.updateBalanceEvent(messageDTO.getMessage(),messageDTO.getTenant());
                break;
            case DeviceModify:
                messageService.updateDeviceEvent(messageDTO.getMessage(),messageDTO.getTenant());
                break;
            default:
                log.error("未找到相应的操作");
                break;
        }
    }
}
