package com.cdqckj.gmis.common.domain.code.clear;

import com.cdqckj.gmis.common.domain.code.CodeInfo;
import com.cdqckj.gmis.common.domain.code.CodeTypeEnum;
import com.cdqckj.gmis.common.domain.code.TempSaveCodeInfo;
import com.cdqckj.gmis.common.key.RedisCommonKey;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.exception.BizException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/12/22 14:14
 * @remark: 请添加类说明
 */
public class CodeClearAll implements CodeClear {

    /** 缓存 **/
    private RedisTemplate redisTemplate;

    public CodeClearAll(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/22 14:14
    * @remark 清空redis的缓存的code 要清除关于这个的所有的缓存信息
    */
    @Override
    public void clearCache(String beginId) {
        // 保存队列的key
        String key = CacheKeyUtil.createTenantKey(RedisCommonKey.CODE_NOT_LOST_CODE_KEY, beginId);
        List<TempSaveCodeInfo> tempSaveList = redisTemplate.opsForList().range(key, 0, -1);
        for (TempSaveCodeInfo tempSave: tempSaveList){
            // 删除这个的所有的key
            CodeTypeEnum typeEnum = CodeTypeEnum.get(tempSave.getCodeType());
            CodeInfo codeInfo = new CodeInfo(typeEnum, tempSave.getTableName(), tempSave.getColName(), tempSave.getCodeGroupType());
            String cacheKey = codeInfo.codeCacheKey();
            redisTemplate.delete(cacheKey);
            // 自增编码
            if (typeEnum.getType() == CodeTypeEnum.INC_TYPE_ONE.getType()){
                String nextKey = codeInfo.nextIncKey();
                redisTemplate.delete(nextKey);
            }
        }
        redisTemplate.delete(key);
    }
}
