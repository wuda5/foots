package com.cdqckj.gmis.iot.qc.qnms.config;

import com.cdqckj.gmis.mq.properties.MqProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 物联网模块消息队列配置
 * @author: 秦川物联网科技
 * @date: 2020/10/13 16:56
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
@Configuration
@AllArgsConstructor
@Slf4j
@ConditionalOnProperty(prefix = MqProperties.PREFIX, name = "enabled", havingValue = "true")
public class IotRabbitmqConfig {
    /**
     * 创建一个消费队列
     * @return
     */
    @Bean
    public Queue gmisIotQueue() {
        // 第一个参数是创建的queue的名字，第二个参数是是否支持持久化
        return new Queue("gmis_queue_iot_qc", true);
    }

    @Bean
    public DirectExchange gmisIotExchange() {
        // 一共有三种构造方法，可以只传exchange的名字， 第二种，可以传exchange名字，是否支持持久化，是否可以自动删除，
        //第三种在第二种参数上可以增加Map，Map中可以存放自定义exchange中的参数
        return new DirectExchange("gmis_exchange_iot_qc", true, false);
    }

    /**
     * 把消费的队列和消费的exchange绑定在一起
      * @return
     */
    @Bean
    public Binding gmisIotBinding() {
        return BindingBuilder.bind(gmisIotQueue()).to(gmisIotExchange()).with("gmis_routing_key_iot_qc");
    }

}
