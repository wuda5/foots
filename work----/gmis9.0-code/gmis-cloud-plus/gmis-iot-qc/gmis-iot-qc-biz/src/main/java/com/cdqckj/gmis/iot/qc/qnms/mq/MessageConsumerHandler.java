package com.cdqckj.gmis.iot.qc.qnms.mq;

import com.cdqckj.gmis.iot.qc.dto.MessageDTO;
import com.cdqckj.gmis.iot.qc.qnms.constant.GMISConstant;
import com.cdqckj.gmis.iot.qc.qnms.utils.MQUtils;
import com.cdqckj.gmis.iot.qc.service.MessageService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 消息队列监听
 * @author: 秦川物联网科技
 * @date: 2020/11/06  10:54
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
@Slf4j
public class MessageConsumerHandler implements ChannelAwareMessageListener {

    @Autowired
    private MessageService messageService;

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        try {
            log.info(message.getMessageProperties().getConsumerQueue());
            if(!message.getMessageProperties().getReceivedRoutingKey().contains(GMISConstant.GMIS_MQ_NAME_PRE)){return;}
            MessageDTO messageDTO = (MessageDTO) MQUtils.getObjectFromBytes(message.getBody());
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
                case ParamsSetting: log.info("物联网iot 参数设置........");
                    messageService.setDeviceParamsEvent(messageDTO.getMessage(),messageDTO.getTenant());
                    break;
//            case Recharge:
//                messageService.rechargeEvent(messageDTO.getMessage(),messageDTO.getTenant());
//                break;
                case ChangePrice:
                    messageService.changePriceEvent(messageDTO.getMessage(),messageDTO.getTenant());
                    break;
                case ChangeBatPrice:
                    messageService.changeBatPriceEvent(messageDTO.getMessage(),messageDTO.getTenant());
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
                case ReadMeterData:
                    messageService.readIotMeterEvent(messageDTO.getMessage(),messageDTO.getTenant());
                    break;
                default:
                    log.error("未找到相应的操作");
                    break;
            }
        }catch (Throwable r) {
            // 失败抛出异常重试
            throw r;
        }
    }
}
