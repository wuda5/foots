package com.cdqckj.gmis.common.domain.machine;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author: lijianguo
 * @time: 2020/11/25 9:22
 * @remark: 配置queue的名字
 */
@Log4j2
@Component
public class MqQueueName {

    /**  5分钟后开始同步 **/
    private static final int START_SCHEDULE = 1000 * 60 * 5;

    /**  5分钟同步的间隔 **/
    private static final int SCHEDULE_FREQUENCY = 1000 * 60 * 5;

    @Autowired
    RegisterAppNum registerAppNum;

    @Autowired
    EnvProperty envProperty;

    /**
     * @auth lijianguo
     * @date 2020/11/25 9:24
     * @remark 获取queue的名字
     */
    public String getQueueName(String oriName){

        RegisterInfo registerInfo = registerAppNum.registerAppInfo(envProperty.getProjectName(), envProperty);
        StringBuilder queueName = new StringBuilder();
        queueName.append(oriName).append("_").append(registerInfo.getNum());
//        queueName.append("_").append(envProperty.getEnvNameSuffix());
        return queueName.toString();
    }

    /**
     * @auth lijianguo
     * @date 2020/11/25 15:04
     * @remark 同步一下应用的编号
     */
    @PostConstruct
    public void syncNum() {
        Timer timer = new Timer();
        ThreadLocal<Long> count = new ThreadLocal<>();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                try {
                    RegisterInfo registerInfo = registerAppNum.registerAppInfo(envProperty.getProjectName(), envProperty);
                    Long value = count.get();
                    if (value == null){
                        value = 0L;
                    }
                    value ++;
                    count.set(value);
                    if (value % 50 <= 1) {
                        log.info("-------同步机器编号-------- 【{}】 【{}】 【{}】 【{}】", registerInfo.getIP(), registerInfo.getPort(),
                                registerInfo.getNum(), System.currentTimeMillis());
                    }
                }catch (Exception e){
                    log.error(e);
                }
            }
        }, START_SCHEDULE, SCHEDULE_FREQUENCY);
    }

}
