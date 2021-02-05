package com.cdqckj.gmis.base;

import com.alibaba.fastjson.JSON;
import com.cdqckj.gmis.constants.IotRConstant;
import com.cdqckj.gmis.utils.I18nUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.cdqckj.gmis.base.R.SUCCESS_CODE;

/**
 * All rights Reserved, Designed By www.cdqckj.com
 *
 * @version V1.0
 * @Description: 返回结果R类
 * @author: 秦川物联网科技
 * @date: 2020/10/13  16:37
 * @author: yjb
 * @Copyright: 2020 www.cdqckj.com Inc. All rights reserved.
 * 注意：本内容仅限于秦川物联网科技公司内部传阅，禁止外泄以及用于其他的商业目
 */
public class IotR extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = 1L;
    @JsonIgnore
    private Boolean defExec = true;

    @ApiModelProperty(value = "响应编码:0/200-请求处理成功")
    private static int code;

    public IotR() {
        put(IotRConstant.CODE, 0);
        put(IotRConstant.MESSAGE,"");
        put(IotRConstant.CONTENT,new ArrayList<>());
        put(IotRConstant.defExec,defExec);
        put(IotRConstant.IS_SUCCESS,true);
        put(IotRConstant.IS_ERROR,false);
        IotR.code = 0;
    }

    public static IotR error() {
        return error(500, "系统繁忙，请联系管理员");
    }

    public static IotR error(String msg) {
        return error(500, msg);
    }

    public static IotR error(Object msg) {
        return error(500, msg);
    }

    public static IotR error(int code, String message) {
        IotR.code = code;
        IotR r = new IotR();
        r.put(IotRConstant.CODE, code);
        r.put(IotRConstant.MESSAGE, message);
        r.put(IotRConstant.defExec,false);
        r.put(IotRConstant.IS_SUCCESS,false);
        r.put(IotRConstant.IS_ERROR,true);
        return r;
    }

    public static IotR error(int code, Object message) {
        IotR.code = code;
        IotR r = new IotR();
        r.put(IotRConstant.CODE, code);
        r.put(IotRConstant.MESSAGE, JSON.toJSON(message));
        r.put(IotRConstant.defExec,false);
        r.put(IotRConstant.IS_SUCCESS,false);
        r.put(IotRConstant.IS_ERROR,true);
        return r;
    }

    public static IotR error(ErrorCode errorCode) {
        IotR r = new IotR();
        IotR.code = Integer.parseInt(errorCode.getValue());
        r.put(IotRConstant.CODE, errorCode.getValue());
        r.put(IotRConstant.MESSAGE, errorCode.getDesc());
        r.put(IotRConstant.defExec,false);
        r.put(IotRConstant.IS_SUCCESS,false);
        r.put(IotRConstant.IS_ERROR,true);
        return r;
    }

    public static IotR ok(String message) {
        IotR r = new IotR();
        IotR.code = 0;
        r.put(IotRConstant.IS_SUCCESS,true);
        r.put(IotRConstant.IS_ERROR,false);
        r.put(IotRConstant.MESSAGE, message);
        return r;
    }

    public static IotR ok(ErrorCode errorCode) {
        IotR r = new IotR();
        IotR.code = Integer.parseInt(errorCode.getValue());
        r.put(IotRConstant.IS_SUCCESS,true);
        r.put(IotRConstant.IS_ERROR,false);
        r.put(IotRConstant.MESSAGE, errorCode.getDesc());
        return r;
    }

    public static IotR ok() {
        return new IotR();
    }

    /**
     * 逻辑处理是否成功
     *
     * @return 是否成功
     */
    public Boolean getIsSuccess() {
        return IotR.code == SUCCESS_CODE || IotR.code == 200;
    }

    /**
     * 逻辑处理是否失败
     *
     * @return
     */
    public Boolean getIsError() {
        return !getIsSuccess();
    }
    /**
     * 返回消息国际化
     * @param lang
     * 		编码:比如zh_CN,en_US
     * @return
     */
    public IotR lang(String lang){
        if(!Objects.isNull(get(IotRConstant.CODE))){
            super.put(IotRConstant.MESSAGE,
                    (new I18nUtil()).getMessage(get(""+IotRConstant.CODE).toString()));
        }
        return this;
    }

    public IotR object(Object obj){
        ((List)get(IotRConstant.CONTENT)).clear();
        if(!Objects.isNull(obj)) {
            ((List)get(IotRConstant.CONTENT)).add(obj);
        }
        return this;
    }

    public IotR list(List list){
        ((List)get(IotRConstant.CONTENT)).clear();
        if(!Objects.isNull(list)) {
            super.put(IotRConstant.CONTENT, list);
        }
        return this;
    }

    public IotR addObject(Object obj){
        if(!Objects.isNull(obj)){
            ((List)get(IotRConstant.CONTENT)).add(obj);
        }
        return this;
    }

    public IotR addList(List list){
        if(!Objects.isNull(list) && list.size()>0) {
            ((List) get(IotRConstant.CONTENT)).addAll(list);
        }
        return this;
    }

    @Override
    public IotR put(String key,Object val){
        super.put(key,val);
        return this;
    }
}
