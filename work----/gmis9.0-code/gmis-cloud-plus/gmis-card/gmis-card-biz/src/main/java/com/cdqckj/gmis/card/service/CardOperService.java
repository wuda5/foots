package com.cdqckj.gmis.card.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.card.dto.CardRepRecordSaveDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.card.entity.CardRepRecord;

/**
 * 读写卡相关业务逻辑处理
 * @author tp
 * @date 2020-09-04
 */
public interface CardOperService {
    /**
     * 发卡加载 -----衍生：发卡只是个记录不作其他使用，发卡后更新表具使用信息表，将卡号回填。补卡后同样回填。
     * 首先查看是否有发卡记录
     * 没有发卡记录，生成一条默认IC状态是待收费。并记录卡类型：表具是否是 IC卡表，如果是IC卡表就发IC卡，如果不是就发ID卡。
     * 已有发卡记录，判断状态
     * 待收费：直接打开收费窗口进行收费（可以顺带充值）
     * 待写卡：加载数据显示写卡按钮--点击写卡按钮进行写卡
     * 已写卡：显示写卡记录信息，无任何操作按钮
     * @param gasMeterCode
     * @return
     */
    R<CardInfo> issueCard(String gasMeterCode,String customerCode);

    /**
     * 补卡
     * @param gasMeterCode
     * @return
     */
    R<CardRepRecord> repCard(String gasMeterCode,String customerCode);

    /**
     * 补卡保存
     * 补充基本信息，确认是否补上次充值，如果补上次充值，根据输入金额气量进行保存
     * @return gasMeterCode
     */
     R<CardRepRecord> repCardSave(CardRepRecordSaveDTO saveDTO, Long id);


}
