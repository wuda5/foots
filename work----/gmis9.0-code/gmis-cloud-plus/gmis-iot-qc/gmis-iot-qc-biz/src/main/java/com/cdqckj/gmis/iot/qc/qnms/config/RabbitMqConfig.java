package com.cdqckj.gmis.iot.qc.qnms.config;

import com.cdqckj.gmis.iot.qc.qnms.mq.MessageConsumerHandler;
import com.cdqckj.gmis.mq.consumer.handler.QueueService;
import com.cdqckj.gmis.mq.properties.MqProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.RetryListener;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.interceptor.RetryInterceptorBuilder;
import org.springframework.retry.interceptor.RetryOperationsInterceptor;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.io.IOException;
import java.util.List;

@Configuration
@Slf4j
@ConditionalOnProperty(prefix = MqProperties.PREFIX, name = "enabled", havingValue = "true")
public class RabbitMqConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    @Autowired
    private MessageConsumerHandler handler;

    @Autowired
    private QueueService queueService;

    @Bean
    @Order(value = 2)
    public SimpleMessageListenerContainer mqMessageContainer() throws AmqpException, IOException {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        List<String> list = queueService.getMQJSONArray();
        container.setQueueNames(list.toArray(new String[list.size()]));
        container.setExposeListenerChannel(true);
        //是否重回队列
        container.setDefaultRequeueRejected(false);
        //设置每个消费者获取的最大的消息数量
        container.setPrefetchCount(1);
        //消费者个数
        container.setConcurrentConsumers(10);
        //设置确认模式为手工确认
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //监听处理类
        container.setMessageListener(handler);
        //消息队列重试
        container.setAdviceChain(retryOperationsInterceptor());
        return container;
    }

    @Bean
    RetryOperationsInterceptor retryOperationsInterceptor() {
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        //重试次数
        retryPolicy.setMaxAttempts(3);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        //重试间隔时间
        backOffPolicy.setInitialInterval(6000);
        //最大重试间隔时间
        backOffPolicy.setMaxInterval(36000);
        retryTemplate.setRetryPolicy(retryPolicy);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        retryTemplate.registerListener(new RetryListener() {
            @Override
            public <T, E extends Throwable> boolean open(RetryContext context, RetryCallback<T, E> callback) {
                return true;
            }

            @Override
            public <T, E extends Throwable> void close(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                if (throwable != null) {
                    log.error("Failed: Retry count " + context.getRetryCount(), throwable);
                }
            }

            @Override
            public <T, E extends Throwable> void onError(RetryContext context, RetryCallback<T, E> callback, Throwable throwable) {
                log.error("Retry count " + context.getRetryCount(), throwable);
            }
        });
        RetryOperationsInterceptor interceptor = RetryInterceptorBuilder.stateless().retryOperations(retryTemplate).build();
        return interceptor;
    }

    @Bean
    public void start() {
        try {
            mqMessageContainer().start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
