package com.cdqckj.gmis.common.domain.tenant;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: lijianguo
 * @time: 2020/11/6 10:43
 * @remark: 租户的事务的注解的说明--线程的保存
 */
public class TxInfoTypeThreadLocal {

    /** 只要用这个注解就必须有事务 默认为本地事务 1本地事务 2分布式事务 其他没有事务**/
    private static final ThreadLocal<TxInfo> txTypeLocal = new ThreadLocal<>();

    /**
     * @auth lijianguo
     * @date 2020/11/6 10:49
     * @remark 保存事务的基本信息
     */
    @Data
    public static class TxInfo{

        @ApiModelProperty(value = "0没有事务,只切换租户可以不用添加这个注解 1本地事务 2分布式事务")
        Integer type = 0;

        @ApiModelProperty(value = "0事务自动回滚 1事务手动回滚")
        Integer roll = 0;
    }

    /**
     * @auth lijianguo
     * @date 2020/11/6 10:51
     * @remark 设置事务的状态
     */
    public static void setCurrentTxInfo(int type, int roll){
        TxInfo txInfo = txTypeLocal.get();
        if (txInfo == null) {
            txInfo = new TxInfo();
        }
        txInfo.setType(type);
        txInfo.setRoll(roll);
        txTypeLocal.set(txInfo);
    }

    /**
     * @auth lijianguo
     * @date 2020/11/6 10:57
     * @remark 获取当前的状态
     */
    public static Integer getTxType(){
        TxInfo txInfo = txTypeLocal.get();
        if (txInfo == null){
            return 0;
        }else {
            return txInfo.type;
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/11/6 10:59
     * @remark 回滚状态
     */
    public static Integer getTxRoll(){
        TxInfo txInfo = txTypeLocal.get();
        if (txInfo == null){
            return 0;
        }else {
            return txInfo.roll;
        }
    }


    /**
     * @auth lijianguo
     * @date 2020/11/6 10:59
     * @remark 清楚这个线程的状态
     */
    public static void clearTxInfo(){
        txTypeLocal.remove();
    }

}
