package com.cdqckj.gmis.common.domain.machine;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author: lijianguo
 * @time: 2020/11/25 8:55
 * @remark: 获取运行的环境
 */
@Log4j2
@Data
@Configuration
@Component
public class EnvProperty {

    public static final String ACTIVE_PREFIX = "spring.profiles";

    private static final String ENV_DEV = "dev";

    private static final String TEST_DEV = "test";

    private static final String PRO_DEV = "pro";

    /** 当前的环境 **/
    @Value("${spring.profiles.active}")
    private String active;

    /** 项目的名字 **/
    @Value("${info.name}")
    private String projectName;

    /** 项目的名字 **/
    @Value("${spring.application.name}")
    private String artifactName;

    /** 端口 **/
    @Value("${gmis.nacos.port}")
    private String port;

    /**
     * @auth lijianguo
     * @date 2020/11/25 9:08
     * @remark 查看当前的环境
     */
    public String getEnvName(){
        return active;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/25 9:12
     * @remark 是不是开发环境
     */
    public Boolean devEnv(){

        if(ENV_DEV.equals(getEnvName())){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/11/25 9:15
     * @remark 测试环境
     */
    public Boolean testEnv(){

        if(TEST_DEV.equals(getEnvName())){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/11/25 9:17
     * @remark 生产环境
     */
    public Boolean proEnv(){

        if(PRO_DEV.equals(getEnvName())){
            return true;
        }else {
            return false;
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/11/25 10:10
     * @remark 获取id
     */
    public String getProjectName(){
        return projectName;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/25 13:13
     * @remark 获取Ip地址
     */
    public String getIpAddress(){
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            log.error(e.getMessage(),e);
        }
        return localHost.getHostAddress();
    }

    /**
     * @auth lijianguo
     * @date 2020/11/25 16:14
     * @remark 获取端口
     */
    public String getPort(){
       return port;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/26 10:22
     * @remark 获取环境的名称后缀
     */
    public String getEnvNameSuffix(){
        if (proEnv()){
            return "p";
        }else if(testEnv()){
            return "t";
        }else {
            return "d";
        }
    }


}
