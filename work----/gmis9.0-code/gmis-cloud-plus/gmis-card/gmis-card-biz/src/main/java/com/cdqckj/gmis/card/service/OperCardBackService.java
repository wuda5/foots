package com.cdqckj.gmis.card.service;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardRepRecord;

/**
 * 写卡数据加载
 * @author tp
 * @date 2020-09-04
 */
public interface OperCardBackService {



    /**
     * 写开户卡完成回调
     * @param writeResult
     * @return
     */
    R<CardInfo> issueCardCallBack(Long id, JSONObject writeResult);

    /**
     * 购气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    R<CardInfo> buyGasCallBack(String gasMeterCode,JSONObject callBackData);

    /**
     * 补卡写卡完成回调
     * @param id
     * @return
     */
    R<CardRepRecord> repCardCallBack(Long id,JSONObject callBackData);


    /**
     * 补气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    R<Boolean> repGasCallBack(String gasMeterCode, JSONObject jsonObject);

    /**
     * 回收卡写卡完成回调
     * 前端读卡如果失败，不能回收卡，读卡成功肯定能获取气表编号
     * @param cardNo
     * @return
     */
    R<Boolean> recCardCallBack(String cardNo, JSONObject callBackData);


    /**
     * 退气写卡完成回调
     * 主要修改充值数据状态为待处理，否则不能申请退费
     * @param gasMeterCode
     * @return
     */
    R<CardInfo> backGasCallBack(String gasMeterCode,JSONObject callBackData);

    /**
     * 读卡回写并返回表具唯一标识--每次充值前调用
     * @param callBackData
     * @return
     */
    R<CardInfo> readCardCallBack(JSONObject callBackData);
}
