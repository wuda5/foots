package com.cdqckj.gmis.bizcenter.card.service;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.card.dto.CardRepLoadDTO;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.card.dto.CardRepRecordSaveDTO;
import com.cdqckj.gmis.card.entity.CardInfo;

/**
 * 读写卡相关业务逻辑处理
 * @author tp
 * @date 2020-09-04
 */
public interface CardBizService {
    /**
     * 发卡加载
     * @param gasMeterCode
     * @return
     */
    R<CardInfo> issueCard(String gasMeterCode,String customerCode);

    R<Boolean> watherIssueCard(String gasMeterCode,String customerCode);


    /**
     * 补卡加载
     * @param gasMeterCode
     * @return
     */
    R<CardRepLoadDTO> repCard(String gasMeterCode,String customerCode);

    /**
     * 补卡保存
     * @param saveDto
     * @return
     */
    R<CardRepLoadDTO> repCardSave(CardRepRecordSaveDTO saveDto,Long id);



    /**
     * 写开户卡完成回调
     * @param callBackData
     * @return
     */
    R<CardInfo> issueCardCallBack(Long id, JSONObject callBackData);

    /**
     * 读卡回写并返回表具唯一标识--每次充值前调用
     * @param callBackData
     * @return
     */
    R<CardInfo> readCardCallBack(JSONObject callBackData);

    /**
     * 退气写卡完成回调
     * 主要修改充值数据状态为待处理，否则不能申请退费
     * @param gasMeterCode
     * @return
     */
    R<CardInfo> backGasCallBack(String gasMeterCode,String customerCode,JSONObject callBackData);

    /**
     * 购气写卡完成回调
     * @param gasMeterCode
     * @return
     */
    R<CardInfo> buyGasCallBack(String gasMeterCode,String customerCode,JSONObject callBackData);


    /**
     * 补气回调
     * @param gasMeterCode
     * @return
     */
    R<SupplementGasRecord> repGasCallBack(String gasMeterCode,Long repGasRecordId, JSONObject callBackData);

}
