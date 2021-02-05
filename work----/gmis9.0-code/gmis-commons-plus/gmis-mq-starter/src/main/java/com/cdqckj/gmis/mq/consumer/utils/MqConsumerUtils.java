package com.cdqckj.gmis.mq.consumer.utils;

import com.cdqckj.gmis.mq.consumer.handler.QueueService;
import com.cdqckj.gmis.mq.producer.utils.MQSpringUtil;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 消息队列动态添加监听
 * @author: 秦川物联网科技
 * @date: 2020/11/06  14:08
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Component
@DependsOn("MQSpringUtil")
public class MqConsumerUtils {

    @Autowired
    private QueueService queueService;

    public String addNewListener(String queueName){
        SimpleMessageListenerContainer container = MQSpringUtil.getBean(SimpleMessageListenerContainer.class);
        List<String> queueNameList = queueService.getMQJSONArray();
        int count = 0;
        while(!queueNameList.contains(queueName)){
            queueNameList = queueService.getMQJSONArray();
            count++;
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(count >=5){
                return "动态添加监听失败";
            }
        }
        container.addQueueNames(queueName);
        long consumerCount = container.getActiveConsumerCount();
        return "修改成功:现有队列监听者["+consumerCount+"]个";
    }
}
