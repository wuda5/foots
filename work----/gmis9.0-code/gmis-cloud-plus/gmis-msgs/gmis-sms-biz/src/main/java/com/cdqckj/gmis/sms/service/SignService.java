package com.cdqckj.gmis.sms.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.sms.dto.SignSaveDTO;
import com.cdqckj.gmis.sms.dto.SignUpdateDTO;
import com.cdqckj.gmis.sms.entity.Sign;
import com.tencentcloudapi.sms.v20190711.models.DescribeSignListStatus;

/**
 * <p>
 * 业务接口
 * 短信签名
 * </p>
 *
 * @author gmis
 * @date 2020-08-05
 */
public interface SignService extends SuperService<Sign> {

    Boolean saveSign(SignSaveDTO signSaveDTO,String str);

    String updateSign(SignUpdateDTO signUpdateDTO,String str);

    void getStstus(Sign sign);

    R<Sign> getDefaultSign();
}
