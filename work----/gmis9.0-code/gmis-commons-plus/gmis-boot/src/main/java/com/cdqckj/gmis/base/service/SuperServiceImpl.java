package com.cdqckj.gmis.base.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.Entity;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.exception.code.ExceptionCode;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.StrPool;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;
import java.util.Collection;

import static com.cdqckj.gmis.context.BaseContextHandler.*;

/**
 * 不含缓存的Service实现
 * <p>
 * <p>
 * 2，removeById：重写 ServiceImpl 类的方法，删除db
 * 3，removeByIds：重写 ServiceImpl 类的方法，删除db
 * 4，updateAllById： 新增的方法： 修改数据（所有字段）
 * 5，updateById：重写 ServiceImpl 类的方法，修改db后
 *
 * @param <M> Mapper
 * @param <T> 实体
 * @author gmis
 * @date 2020年02月27日18:15:17
 */
public class SuperServiceImpl<M extends SuperMapper<T>, T> extends ServiceImpl<M, T> implements SuperService<T> {

    public SuperMapper getSuperMapper() {
        if (baseMapper instanceof SuperMapper) {
            return baseMapper;
        }
        throw BizException.wrap(ExceptionCode.SERVICE_MAPPER_ERROR);
    }

    /**
     * 构建没有租户信息的key
     *
     * @param args
     * @return
     */
    protected static String buildKey(Object... args) {
        if (args.length == 1) {
            return String.valueOf(args[0]);
        } else if (args.length > 0) {
            return StrUtil.join(StrPool.COLON, args);
        } else {
            return "";
        }
    }

    /**
     * 构建key
     *
     * @param args
     * @return
     */
    protected String key(Object... args) {
        return buildKey(args);
    }

    @Override
    public boolean save(T model) {
        setCommonParams(model);
        R<Boolean> result = handlerSave(model);
        if (result.getDefExec()) {
            return super.save(model);
        }
        return result.getData();
    }

    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize){
        String sqlStatement = this.sqlStatement(SqlMethod.INSERT_ONE);
        return this.executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            sqlSession.insert(sqlStatement, setCommonParams(entity));
        });
    }

    /**
     * 处理新增相关处理
     *
     * @param model
     * @return
     */
    protected R<Boolean> handlerSave(T model) {
        return R.successDef();
    }

    /**
     * 处理修改相关处理
     *
     * @param model
     * @return
     */
    protected R<Boolean> handlerUpdateAllById(T model) {
        return R.successDef();
    }

    /**
     * 处理修改相关处理
     *
     * @param model
     * @return
     */
    protected R<Boolean> handlerUpdateById(T model) {
        return R.successDef();
    }

    @Override
    public boolean updateAllById(T model) {
        R<Boolean> result = handlerUpdateAllById(model);
        if (result.getDefExec()) {
            return SqlHelper.retBool(getSuperMapper().updateAllById(model));
        }
        return result.getData();
    }

    @Override
    public boolean updateById(T model) {
        R<Boolean> result = handlerUpdateById(model);
        if (result.getDefExec()) {
            return super.updateById(model);
        }
        return result.getData();
    }
    @Autowired
    RedisService redisService;
    @Override
    public String getLangMessage(String key) {
        String message = null;
        Integer langType = (Integer) redisService.get("lang"+ BaseContextHandler.getTenant());
        if(langType==null){langType=1;}
        switch(langType){
            case 1:
                message = (String) redisService.hmget("langZh").get(key);
                break;
            case 2:
                message = (String) redisService.hmget("langEn").get(key);
                break;
            default:
                message = null;
        }
        return message;
    }

    @SneakyThrows
    @Override
    public T setCommonParams(T obj) {
        setParams(obj,"orgId","setOrgId",getOrgId(),Long.class);
        setParams(obj,"orgName","setOrgName",getOrgName(),String.class);
        setParams(obj,"companyCode","setCompanyCode",getTenantId(),String.class);
        setParams(obj,"companyName","setCompanyName",getTenantName(),String.class);
        setParams(obj,"deleteStatus","setDeleteStatus",0,Integer.class);
        setParams(obj,"createUserName","setCreateUserName",getName(),String.class);
        return obj;
    }

    @SneakyThrows
    private void setParams(T obj, String key, String methodName, Object value, Class tclass){
        Boolean isSet = BeanPlusUtil.isNullField(key,obj);
        if(isSet){
            Method setMethod = obj.getClass().getMethod(methodName,tclass);
            setMethod.invoke(obj, value);
        }
    }

}
