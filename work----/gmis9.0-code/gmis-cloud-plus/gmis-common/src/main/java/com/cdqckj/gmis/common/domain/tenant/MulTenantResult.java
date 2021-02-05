package com.cdqckj.gmis.common.domain.tenant;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lijianguo
 * @time: 2020/10/8 10:00
 * @remark: 多租户处理的结果-- 这个类是线程安全的
 */
public class MulTenantResult<T> {

    @ApiModelProperty(value = "所有租户的处理结果")
    private List<TenantResult<T>> resultList;

    @ApiModelProperty(value = "所有租户户的处理结果 1所有成功 0不是所有成功")
    private boolean resultState;

    /**
     * @auth lijianguo
     * @date 2020/10/8 11:00
     * @remark 多线程用这个构造函数
     */
    public MulTenantResult() {
        this.resultList = new ArrayList<>();
        this.resultState = true;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 11:01
     * @remark 单线程用这哦构造函数
     */
    public MulTenantResult(int size) {
        this.resultList = new ArrayList<>(size);
        this.resultState = true;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 10:24
     * @remark 添加一个线程的处理结果,如果错误就设置状态为
     */
    public synchronized void addOneResult(TenantResult tenantResult){

        if (tenantResult.getResultState() == false){
            resultState = false;
        }
        resultList.add(tenantResult);
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 10:46
     * @remark 1所有成功 0不是所有成功
     */
    public Boolean processMulSuc(){
        return resultState;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 14:39
     * @remark 获取所有的结果
     */
    public List<TenantResult<T>> getAllResultList(){
        return this.resultList;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 11:56
     * @remark 获取失败的的租户名字
     */
    public String failTenantName(){

        StringBuilder allFailName = new StringBuilder();
        allFailName.append("文字替换租户[ ");
        for (TenantResult tenantResult: resultList){
            if (tenantResult.getResultState() == false) {
                allFailName.append(tenantResult.getName()).append(" ");
            }
        }
        allFailName.append(" ]文字替换处理失败");
        return allFailName.toString();
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 13:15
     * @remark 检查和抛出异常
     */
    public void checkAndThrowException(){
        if (resultState == false){
            throw new MulTenantException(failTenantName());
        }
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 14:47
     * @remark 获取成功的值-失败的不处理
     */
    public List<T> getSucTList(){

        List<T> tList = new ArrayList<>(resultList.size());
        for (TenantResult<T> result: resultList){
            if (result.getResultState() == false){
                continue;
            }
            T t = result.getResult();
            if (t instanceof SetTenantInfo){
                SetTenantInfo addTenantInfo = (SetTenantInfo) t;
                addTenantInfo.setTenantCode(result.getCode());
                addTenantInfo.setTenantName(result.getName());
            }
            tList.add(t);
        }
        return tList;
    }

    /**
     * @auth lijianguo
     * @date 2020/10/8 15:31
     * @remark 获取所有的值，失败抛出异常
     */
    public List<T> getTList(){
        checkAndThrowException();
        return getSucTList();
    }

    /**
     * @auth lijianguo
     * @date 2020/10/30 14:29
     * @remark 获取所有的数据
     */
    public List<TenantResult<T>> getAllTenantResultList(){
        return resultList;
    }

}
