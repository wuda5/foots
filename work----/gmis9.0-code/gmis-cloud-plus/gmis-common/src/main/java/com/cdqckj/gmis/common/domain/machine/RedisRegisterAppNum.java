package com.cdqckj.gmis.common.domain.machine;

import com.cdqckj.gmis.common.key.RedisCommonKey;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: lijianguo
 * @time: 2020/11/25 9:20
 * @remark: 同步分布式缓存
 */
@Log4j2
@Component
public class RedisRegisterAppNum implements RegisterAppNum{

    /**  key过期时间24小时 */
    private static final int KEY_EXPIRE_MILLISECONDS = 1000 * 60 * 60 * 24;

    /**  编号注册时间20纷争 */
    private static final int MAX_SURVIVE = 1000 * 60 * 20;

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * @auth lijianguo
     * @date 2020/11/25 13:09
     * @remark 注册一个应用并且绑定一个编号
     */
    @Override
    public synchronized RegisterInfo registerAppInfo(String projectName, EnvProperty envProperty) {

        String projectKey = createProjectKey(projectName);
        String machineKey = createMachineKey(envProperty);

        RegisterInfo registerInfo = (RegisterInfo) redisTemplate.opsForHash().get(projectKey, machineKey);
        if (registerInfo == null) {
            List<RegisterInfo> allInfo = redisTemplate.opsForHash().values(projectKey);
            // 找到一个编号
            Integer rightNum = getExpireMachineNum(projectKey, allInfo);
            registerInfo = new RegisterInfo();
            registerInfo.setIP(envProperty.getIpAddress());
            registerInfo.setPort(envProperty.getPort());
            registerInfo.setNum(rightNum);
        }
        registerInfo.setTime(System.currentTimeMillis());
        redisTemplate.opsForHash().put(projectKey, machineKey, registerInfo);
        redisTemplate.expire(projectKey, KEY_EXPIRE_MILLISECONDS, TimeUnit.MILLISECONDS);
        return registerInfo;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/26 11:39
     * @remark 项目的key
     */
    private String createMachineKey(EnvProperty envProperty){
        StringBuilder sbKey = new StringBuilder();
        sbKey.append(envProperty.getIpAddress()).append(":").append(envProperty.getPort());
//        sbKey.append(":").append(envProperty.getEnvNameSuffix());
        return sbKey.toString();
    }

    /**
     * @auth lijianguo
     * @date 2020/11/26 11:43
     * @remark has的key
     */
    private String createProjectKey(String projectName){
        return CacheKeyUtil.createNoTenantKey(RedisCommonKey.REGISTER_MACHINE_KEY, projectName);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/25 13:38
     * @remark 获取一个过期的机器的编号 从1开始编号
     */
    private Integer getExpireMachineNum(String projectName, List<RegisterInfo> registerInfoList){

        List<Integer> nums = new ArrayList<>(registerInfoList.size());
        for (int i = 0; i < registerInfoList.size(); i++){
            // -1 表示没有使用
            nums.add(i, -1);
        }
        Long now = System.currentTimeMillis();
        for (RegisterInfo registerInfo: registerInfoList){

            if (now - registerInfo.getTime() > MAX_SURVIVE){
                redisTemplate.opsForHash().delete(projectName, registerInfo.getIP());
                return registerInfo.getNum();
            }else {
                if (registerInfo.getNum() > nums.size()){
                    log.info("超出限制 {} {}", nums.size(), registerInfo.getNum());
                }
                nums.set(registerInfo.getNum() - 1, 1);
            }
        }
        for (int i = 0; i < nums.size(); i++){
            Integer num = nums.get(i);
            if (num == -1){
                return i + 1;
            }
        }
        return nums.size() + 1;
    }

}
