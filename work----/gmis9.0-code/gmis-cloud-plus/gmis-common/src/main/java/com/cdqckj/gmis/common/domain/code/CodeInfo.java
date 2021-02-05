package com.cdqckj.gmis.common.domain.code;

import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.common.key.RedisCommonKey;
import com.cdqckj.gmis.common.utils.CacheKeyUtil;
import com.cdqckj.gmis.exception.BizException;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author: lijianguo
 * @time: 2020/12/07 08:44
 * @remark: 生成code的接口
 */
public class CodeInfo implements CodeSql {

    /** 生成code的类型 */
    private CodeTypeEnum codeTypeEnum;

    /** 表的名字 */
    private String tableName;
    
    /** 列的名字 */
    private String colName;

    /** code的类型  */
    private String codeGroupKey;

    /** 查询数据的sql */
    private String searchSql;

    /** 查询下一个数据的sql */
    private String nextCodeSql;

    public CodeInfo(CodeTypeEnum codeTypeEnum, String tableName, String colName, String codeGroupType, String searchSql,  String nextCodeSql) {
        this(codeTypeEnum,  tableName, colName, codeGroupType);
        this.searchSql = searchSql;
        this.nextCodeSql = nextCodeSql;
    }

    public CodeInfo(CodeTypeEnum codeTypeEnum, String tableName, String colName, String codeGroupType) {
        this(codeTypeEnum,  tableName, colName);
        this.codeGroupKey = codeGroupType;
    }

    public CodeInfo(CodeTypeEnum codeTypeEnum, String tableName, String colName) {
        this.codeTypeEnum = codeTypeEnum;
        this.tableName = tableName;
        this.colName = colName;
    }

    public CodeInfo() {

    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/31 9:49
    * @remark 枚举
    */
    public CodeTypeEnum getCodeTypeEnum() {
        return codeTypeEnum;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/7 9:00
    * @remark 数据库的表的名字
    */
    public String getTableName() {
        return StrUtil.toSymbolCase(tableName,'_');
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/7 9:00
    * @remark 列名字
    */
    public String getColName() {
        return StrUtil.toSymbolCase(colName,'_');
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/31 9:50
    * @remark 类型
    */
    public String getCodeGroupKey() {
        return codeGroupKey;
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/7 9:00
    * @remark 缓存的key
    */
    public String codeCacheKey(){
        return createTheKey(RedisCommonKey.SYS_CODE_CACHE_KEY);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/7 9:07
    * @remark 生成分布式锁的code
    */
    public String lockKey(){
        return createTheKey(RedisCommonKey.DIS_LOCK_CODE_CACHE_KEY);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 9:08
    * @remark queue 下一个自增的key
    */
    public String nextIncKey(){
        return createTheKey(RedisCommonKey.NEXT_INC_CODE_KEY);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/8 11:10
    * @remark 生成key
    */
    private String createTheKey(String sysCodeCacheKey) {
        StringBuilder sb = new StringBuilder();
        sb.append(sysCodeCacheKey);
        if ((CodeTypeEnum.RANDOM_TYPE_ONE.equals(codeTypeEnum))) {
            sb.append("ro:");
        } else if((CodeTypeEnum.INC_TYPE_ONE.equals(codeTypeEnum))){
            sb.append("io:");
        }else {
            throw new BizException("编码类型不存在");
        }
        sb.append(getTableName()).append(":").append(getColName());
        if (StringUtils.isNotBlank(codeGroupKey)){
            sb.append(":").append(codeGroupKey);
        }
        return CacheKeyUtil.createTenantKey(sb.toString());
    }

    @Override
    public String allCodeSql() {
        if (StringUtils.isNotBlank(searchSql)){
            return searchSql;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(getColName()).append(" FROM ").append(getTableName());
        return sb.toString();
    }

    @Override
    public String nextCodeSql() {
        if (StringUtils.isNotBlank(nextCodeSql)){
            return nextCodeSql;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ").append(getColName()).append(" FROM ").append(getTableName());
        sb.append(" ORDER BY create_time DESC LIMIT 200");
        return sb.toString();
    }
}
