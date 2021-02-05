package com.cdqckj.gmis.statistics.statistics.config;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author: lijianguo
 * @time: 2020/11/26 13:10
 * @remark: 数据库的地址
 */
@Data
@Log4j2
@Component
@ConfigurationProperties(prefix="gmis.mysql")
public class DatabaseProperty {

    /** 数据库的Ip **/
    private String ip;

    /** 数据库的端口 **/
    private String port;

    @PostConstruct
    public void initLog(){
        log.info("开始启动 数据库:【{}:{}】",ip, port);
    }

}
