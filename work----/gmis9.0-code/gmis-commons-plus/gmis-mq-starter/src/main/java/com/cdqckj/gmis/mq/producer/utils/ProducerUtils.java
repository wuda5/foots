package com.cdqckj.gmis.mq.producer.utils;

import com.cdqckj.gmis.mq.consumer.utils.MqConsumerUtils;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Properties;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 消息队列动态创建队列和消息
 * @author: 秦川物联网科技
 * @date: 2020/11/06  14:08
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@AllArgsConstructor
@Component
public class ProducerUtils {

    private RabbitTemplate rabbit;

    private RabbitAdmin rabbitAdmin;

    @Autowired
    private MqConsumerUtils mqConsumerUtils;

    public MqR<Boolean> produce(String queueName , byte[] message){
        createMQIfNotExist(queueName,queueName);
        rabbit.convertAndSend(queueName,message);
        return MqR.success();
    }

    private void createMQIfNotExist(String queueName ,String exchangeName) {
        //判断队列是否存在
        Properties properties = rabbitAdmin.getQueueProperties(queueName);
        if(properties == null){
            Queue queue = new Queue(queueName, true, false, false, null);
            FanoutExchange fanoutExchange = new FanoutExchange(exchangeName);
            rabbitAdmin.declareQueue(queue);
            rabbitAdmin.declareExchange(fanoutExchange);
            rabbitAdmin.declareBinding(BindingBuilder.bind(queue).to(fanoutExchange));
            //新启动一个线程，通知消费者新增listener
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String res = callAddNewListener(queueName);
                    if(!StringUtils.isEmpty(res)){
                        System.out.println("-->>调用创建新的 listener feign 失败");
                    }
                }

            }).start();

        }

    }

    private String callAddNewListener(String queueName){
        try {
            mqConsumerUtils.addNewListener(queueName);
        }catch (Exception e){
            e.printStackTrace();
            return "调用添加listener feign失败";
        }
        return null;

    }

}
