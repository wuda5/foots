package com.cdqckj.gmis.systemparam.entity.vo;

import com.cdqckj.gmis.common.domain.Separate.SeparateKey;
import com.cdqckj.gmis.systemparam.entity.FunctionSwitchPlus;
import lombok.Data;

/**
 * @author: lijianguo
 * @time: 2020/12/04 20:06
 * @remark: 请添加类说明
 */
@Data
public class FunctionSwitchPlusVo extends FunctionSwitchPlus implements SeparateKey {

    @Override
    public String indexKey() {
        return super.getItem();
    }
}
