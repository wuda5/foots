package com.cdqckj.gmis.common.utils;

import com.cdqckj.gmis.common.domain.code.DataBaseUtil;
import com.cdqckj.gmis.common.key.RedisAuthKey;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author: lijianguo
 * @time: 2021/01/26 14:54
 * @remark: 关于组织的工具类
 */
@Log4j2
@Component
public class UserOrgIdUtil {

    /** 缓存 **/
    private static RedisTemplate redisTemplate;

    @Autowired
    RedisTemplate redisTemplateInit;

    @PostConstruct
    public void InitOrgCacheUtil() {
        UserOrgIdUtil.redisTemplate = redisTemplateInit;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/26 14:56
    * @remark 获取这个用户的组织ID
    */
    public static Long getUserOrgId(){
        Long userId = getUserId();
        String key = getUserOrgKey(userId);
        Object orgIdCache = redisTemplate.opsForValue().get(key);
        if (orgIdCache == null){
            orgIdCache = getUserOrgIdFromDataBase(userId);
            redisTemplate.opsForValue().set(key, orgIdCache, 1, TimeUnit.MINUTES);
        }
        return Long.valueOf((String) orgIdCache);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/26 15:57
    * @remark 清空这个用户的 org id
    */
    public static void clearUserOrgIdCache(){
        Long userId = getUserId();
        String key = getUserOrgKey(userId);
        redisTemplate.delete(key);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/26 15:32
    * @remark 获取用户的orgId
    */
    private static String getUserOrgIdFromDataBase(Long userId){
        String sql = "SELECT org_id FROM c_auth_user WHERE id = " + userId;
        List<String> idList =  DataBaseUtil.getOneColData(sql,"org_id");
        if (idList.size() != 1){
            throw BizException.wrap("请先设置用户组织");
        }
        return idList.get(0);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/26 15:45
    * @remark 获取用户的Key
    */
    private static String getUserOrgKey(Long userId){
        String key = CacheKeyUtil.createTenantKey(RedisAuthKey.AUTH_USER_ORG_KEY, String.valueOf(userId));
        return key;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/26 16:30
    * @remark 获取用户的Id
    */
    private static Long getUserId(){
        Long userId = BaseContextHandler.getUserId();
        if (userId == null){
            throw BizException.wrap("用户未登陆");
        }
        return userId;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/26 16:19
    * @remark 获取用户的数据权限
    */
    public static List<Long> getUserDataScopeList(){

        Long userId = getUserId();
        StringBuilder sql = new StringBuilder();
        sql.append("SELECT caro.org_id").append(" ");
        sql.append("FROM").append(" ");
        sql.append("c_auth_user cau").append(" ");
        sql.append("INNER JOIN c_auth_user_role caur ON caur.user_id = cau.id").append(" ");
        sql.append("INNER JOIN c_auth_role_org caro ON caur.role_id = caro.role_id").append(" ");
        sql.append("WHERE cau.id = ").append(userId).append(" ");
        sql.append("GROUP BY caro.org_id");
        List<String> idList =  DataBaseUtil.getOneColData(sql.toString(),"org_id");
        List<Long> resultList = new ArrayList<>();
        for (String id: idList){
            resultList.add(Long.valueOf(id));
        }
        if (resultList.size() == 0){
            resultList.add(-1L);
        }
        return resultList;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/28 9:12
    * @remark 获取租户的数据权限的string
    */
    public static String getUserDataScopeStr(){

        List<Long> orgIdList = getUserDataScopeList();
        StringBuilder sb = new StringBuilder();
        sb.append(" (");
        for (int i = 0; i < orgIdList.size(); i++){
            Long id = orgIdList.get(i);
            sb.append(id);
            if (i != orgIdList.size() - 1){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
