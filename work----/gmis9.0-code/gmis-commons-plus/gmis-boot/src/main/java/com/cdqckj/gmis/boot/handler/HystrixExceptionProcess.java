package com.cdqckj.gmis.boot.handler;

import cn.hutool.core.thread.ThreadUtil;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.utils.I18nUtil;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.log4j.Log4j2;

/**
 * @author: lijianguo
 * @time: 2020/10/19 10:07
 * @remark: 熔断器的日志打印
 */
@Log4j2
public class HystrixExceptionProcess {

    private static final String REMOTE = "";
    private static final String REMOTE_EN = "";

    /**
     * @auth lijianguo
     * @date 2020/10/19 10:08
     * @remark 打印日志
     */
    public static String hystrixRuntimeExceptionProcess(HystrixRuntimeException e) {

        log.error("HystrixRuntimeException:", e);
        String detailMsg = e.getMessage();
        String causeMsg = e.getCause().getMessage();
        String serviceName = "";
        if (causeMsg == null) {
            causeMsg = "";
        }
        String[] causeMsgSplit = causeMsg.split(":");
        if (causeMsgSplit.length >= 3) {
            serviceName = causeMsgSplit[2];
        }
        if (detailMsg == null) {
            detailMsg = "";
        }
        String[] detailMsgSplit = detailMsg.split(" ");
        if (detailMsgSplit.length > 0) {
            detailMsg = detailMsgSplit[0];
        }
        log.error("上游服务器关闭:【{}】 【{}】", serviceName, detailMsg);
        return I18nUtil.getMsg(REMOTE, REMOTE_EN) + I18nUtil.getMsg(R.HYSTRIX_ERROR_MESSAGE, R.HYSTRIX_ERROR_MESSAGE_En);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/19 10:22
     * @remark 处理上游的请求异常
     */
    public static String hystrixBadRequestExceptionProcess(HystrixBadRequestException e) {

        log.error("hystrixBadRequestExceptionProcess:", e);
        if (e.getMessage() != null) {
            return I18nUtil.getMsg(REMOTE, REMOTE_EN) + e.getMessage();
        } else {
            return I18nUtil.getMsg(REMOTE, REMOTE_EN) + I18nUtil.getMsg(R.DEF_ERROR_MESSAGE, R.DEF_ERROR_MESSAGE_En);
        }
    }

}
