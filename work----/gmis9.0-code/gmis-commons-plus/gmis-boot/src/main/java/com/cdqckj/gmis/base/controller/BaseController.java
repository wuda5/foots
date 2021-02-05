package com.cdqckj.gmis.base.controller;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.exception.code.BaseExceptionCode;
import com.cdqckj.gmis.base.service.SuperService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 基础接口
 *
 * @param <Entity> 实体
 * @author gmis
 * @date 2020年03月07日21:56:32
 */
public interface BaseController<Entity> {

    Class<Entity> getEntityClass();

    SuperService<Entity> getBaseService();

    RedisService getRedisService();

    String getLangMessage(String key);

    Entity setCommonParams(Entity obj);
    /**
     * 成功返回
     *
     * @param data 返回内容
     * @param <T>  返回类型
     * @return R
     */
    default <T> R<T> success(T data) {
        return R.success(data);
    }

    /**
     * 成功返回
     *
     * @return R.true
     */
    default R<Boolean> success() {
        return R.success();
    }

    /**
     * 失败返回
     *
     * @param msg 失败消息
     * @param <T> 返回类型
     * @return
     */
    default <T> R<T> fail(String msg) {
        return R.fail(msg);
    }
//
//    /**
//     * 失败返回
//     *
//     * @param msg  失败消息
//     * @param args 动态参数
//     * @param <T>  返回类型
//     * @return
//     */
//    @Deprecated
//    default <T> R<T> fail(String msg,String msgEn ,Object... args) {
//        return R.fail(msg, msgEn, args);
//    }
//
//    /**
//     * 失败返回
//     *
//     * @param code 失败编码
//     * @param msg  失败消息
//     * @param <T>  返回类型
//     * @return
//     */
//    default <T> R<T> fail(int code, String msg) {
//        return R.fail(code, msg);
//    }
//
//    /**
//     * 失败返回
//     *
//     * @param exceptionCode 失败异常码
//     * @param <T>
//     * @return
//     */
//    default <T> R<T> fail(BaseExceptionCode exceptionCode) {
//        return R.fail(exceptionCode);
//    }
//
//    /**
//     * 失败返回
//     *
//     * @param exception 异常
//     * @param <T>
//     * @return
//     */
//    default <T> R<T> fail(BizException exception) {
//        return R.fail(exception);
//    }
//
//    /**
//     * 失败返回
//     *
//     * @param throwable 异常
//     * @param <T>
//     * @return
//     */
//    default <T> R<T> fail(Throwable throwable) {
//        return R.fail(throwable);
//    }
//
    /**
     * 参数校验失败返回
     *
     * @param msg 错误消息
     * @param <T>
     * @return
     */
    default <T> R<T> validFail(String msg) {
        return R.validFail(msg);
    }
//
//    /**
//     * 参数校验失败返回
//     *
//     * @param msg  错误消息
//     * @param args 错误参数
//     * @param <T>
//     * @return
//     */
//    default <T> R<T> validFail(String msg, Object... args) {
//        return R.validFail(msg, args);
//    }
//
//    /**
//     * 参数校验失败返回
//     *
//     * @param exceptionCode 错误编码
//     * @param <T>
//     * @return
//     */
//    default <T> R<T> validFail(BaseExceptionCode exceptionCode) {
//        return R.validFail(exceptionCode);
//    }

    /**
     * 获取当前id
     *
     * @return userId
     */
    default Long getUserId() {
        return BaseContextHandler.getUserId();
    }

    /**
     * 当前请求租户
     *
     * @return 租户编码
     */
    default String getTenant() {
        return BaseContextHandler.getTenant();
    }

    /**
     * 登录人账号
     *
     * @return 账号
     */
    default String getAccount() {
        return BaseContextHandler.getAccount();
    }

    /**
     * 登录人姓名
     *
     * @return 姓名
     */
    default String getName() {
        return BaseContextHandler.getName();
    }

    /**
     * 组织id
      * @return
     */
    default Long getOrgId() {
        return BaseContextHandler.getOrgId();
    }

    /**
     * 组织名称
     * @return
     */
    default String getOrgName() {
        return BaseContextHandler.getOrgName();
    }

    /**
     * 公司编码
     * @return
     */
    default String getCompanyCode() {
        return BaseContextHandler.getTenantId();
    }

    /**
     * 公司名称
     * @return
     */
    default String getCompanyName() {
        return BaseContextHandler.getTenantName();
    }
}
