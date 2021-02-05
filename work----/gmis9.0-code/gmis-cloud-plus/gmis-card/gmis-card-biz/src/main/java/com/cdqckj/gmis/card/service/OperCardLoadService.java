package com.cdqckj.gmis.card.service;

import com.alibaba.fastjson.JSONObject;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.card.dto.CardSupplementGasDTO;
import com.cdqckj.gmis.card.dto.RecCardLoadDTO;

/**
 * 写卡数据加载
 * @author tp
 * @date 2020-09-04
 */
public interface OperCardLoadService {
    /**
     * 开户
     * @param
     * @return
     */
    R<JSONObject> issueCardLoad(Long id,JSONObject data);
    /**
     * 购气
     * @return
     */
    R<JSONObject> buyGasLoad(String gasMeterCode,JSONObject data);
    /**
     * 补卡
     * @param id
     * @return
     */
    R<JSONObject> repCardLoad(Long id,JSONObject data);
    /**
     * 补气
     * @param
     * @return
     */
    R<JSONObject> repGasLoad(CardSupplementGasDTO repGasData);
    /**
     * 退气
     * @param gasMeterCode
     * @return
     */
    R<JSONObject> backGasLoad(String gasMeterCode,JSONObject readData);

    /**
     * 换表
     * @param gasMeterCode
     * @return
     */
    R<JSONObject> changeMeterLoad(String gasMeterCode);
    /**
     * 读卡
     * @return
     */
    R<JSONObject> readCardLoad();

    /**
     * 回收
     * @return
     */
    R<RecCardLoadDTO> recCardLoad(JSONObject readData);
}
