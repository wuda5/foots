package com.cdqckj.gmis.common.domain.code;

import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.common.domain.code.clear.ClearNoneException;
import com.cdqckj.gmis.common.domain.code.clear.ClearNowException;
import com.cdqckj.gmis.common.domain.code.next.NextCode;
import com.cdqckj.gmis.common.domain.code.next.NextCodeFactoryUtil;
import com.cdqckj.gmis.common.key.RedisCommonKey;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.lock.DistributedLock;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author: lijianguo
 * @time: 2020/12/05 09:38
 * @remark: 整个系统的code的生成的基本类
 */
@Log4j2
@Component
public class BizCodeBaseUtil {

    @Autowired
    private RedisTemplate redisTemplateInit;

    @Autowired(required = false)
    private DistributedLock distributedLockInit;

    /** 缓存 **/
    private static RedisTemplate redisTemplate;

    /** 缓存 **/
    private static DistributedLock redisDistributedLock;

    /** 保存这个开始的code **/
    public static final String CODE_CREATE_BEGIN_ID = "codeBeginId";

    public BizCodeBaseUtil() {

    }

    @PostConstruct
    public void initStaticData() {
        BizCodeBaseUtil.redisTemplate = redisTemplateInit;
        BizCodeBaseUtil.redisDistributedLock = distributedLockInit;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/10 11:08
    * @remark 生成code 传入 CodeInfo 如果你要让code不丢失，一定要在controller加 @CodeNotLost 注解
    */
    public static String createCode(CodeInfo codeInfo, String codePrefix, Integer codeLength){

        CodeProduce codeProduce = createProduce(codeInfo);
        NextCode nextCode = NextCodeFactoryUtil.createNextCode(codeInfo, codePrefix, codeLength);
        String code = codeProduce.getCode(codeInfo, nextCode);
        tempSaveCode(codeInfo, code, true);
        return code;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/10 14:02
    * @remark 检查这个code是否已经存在 -- 如果code不存在，就要记住code
    */
    public static Boolean checkSaveCode(CodeInfo codeInfo, String newCode){
        CodeProduce codeProduce = createProduce(codeInfo);
        Boolean exist = codeProduce.saveCode(codeInfo, newCode);
        if (exist == true){
            tempSaveCode(codeInfo, newCode, false);
        }else {
            tempSaveCode(codeInfo, newCode, true);
        }
        return exist;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/10 14:11
    * @remark 生成类型
    */
    private static CodeProduce createProduce(CodeInfo codeInfo){

        if (codeInfo.getCodeTypeEnum() == null){
            throw new BizException("编码类型不能为空");
        }
        if (codeInfo.getTableName() == null){
            throw new BizException("表名不能为空");
        }
        if (codeInfo.getColName() == null){
            throw new BizException("列名不能为空");
        }
        return new CodeProduceDefault(redisTemplate, redisDistributedLock);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/7 18:07
    * @remark 生成编码
    */
    public static String createAutoIncCode(String tableName, String columnName, String codePrefix, Integer length){
        CodeInfo codeInfo = new CodeInfo(CodeTypeEnum.INC_TYPE_ONE, tableName, columnName);
        return createAutoIncCode(codeInfo, codePrefix, length);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/7 18:07
    * @remark 自增加编码重载
    */
    public static String createAutoIncCode(CodeInfo codeInfo, String codePrefix, Integer codeLength){
        CodeProduce codeProduce = new CodeProduceDefault(redisTemplate, redisDistributedLock);
        NextCode nextCode = NextCodeFactoryUtil.createNextCode(codeInfo, codePrefix, codeLength);
        String code = codeProduce.getCode(codeInfo, nextCode);
        tempSaveCode(codeInfo, code, true);
        return code;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/7 18:59
     * @remark 生成当前请求的code-并且把code保存在 header中，让其可以在各个应用中传递
     */
    public static String initNotLossCodeBeginId(){
        String beginId = BaseContextHandler.getLocalMap().get(CODE_CREATE_BEGIN_ID);
        if (StringUtils.isBlank(beginId)) {
            beginId = StrUtil.uuid();
            BaseContextHandler.getLocalMap().put(CODE_CREATE_BEGIN_ID, beginId);
        }
        return beginId;
    }

    /**
     * @Author: lijiangguo
     * @Date: 2020/12/7 19:09
     * @remark 获取这个beginId--这个beginId
     */
    public static String getNotLossCodeBeginId(){
        String beginId = BaseContextHandler.getLocalMap().get(CODE_CREATE_BEGIN_ID);
        return beginId;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/7 20:04
    * @remark 清空这个beginId
    */
    public static void clearBeginId(){
        BaseContextHandler.getLocalMap().remove(CODE_CREATE_BEGIN_ID);
    }

    /**-
    * @Author: lijiangguo
    * @Date: 2020/12/7 19:26
    * @remark 先暂存code  1系统的code不存在 2系统的code存在
    */
    private static void tempSaveCode(CodeInfo codeInfo, String code, Boolean newCode){
        String beginId = getNotLossCodeBeginId();
        if (beginId == null){
            throw new ClearNoneException("请添加注解");
        }
        String key = CacheKeyUtil.createTenantKey(RedisCommonKey.CODE_NOT_LOST_CODE_KEY, beginId);
        TempSaveCodeInfo tempSaveCode = new TempSaveCodeInfo();
        tempSaveCode.setCodeType(codeInfo.getCodeTypeEnum().getType());
        tempSaveCode.setTableName(codeInfo.getTableName());
        tempSaveCode.setColName(codeInfo.getColName());
        tempSaveCode.setCode(code);
        tempSaveCode.setCodeGroupType(codeInfo.getCodeGroupKey());
        tempSaveCode.setNewCode(newCode);
        redisTemplate.opsForList().leftPush(key, tempSaveCode);
        redisTemplate.expire(key, 30, TimeUnit.MINUTES);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/30 13:24
    * @remark 删除set的code
    */
    public static void removeCacheCode(CodeInfo codeInfo, String code){
        if (StringUtils.isBlank(code)){
            return;
        }
        redisTemplate.opsForSet().remove(codeInfo.codeCacheKey(), code);
    }

}
