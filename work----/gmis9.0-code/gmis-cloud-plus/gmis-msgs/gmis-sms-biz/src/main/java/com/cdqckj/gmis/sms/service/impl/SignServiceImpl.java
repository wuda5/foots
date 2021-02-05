package com.cdqckj.gmis.sms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.sms.dao.SignMapper;
import com.cdqckj.gmis.sms.dto.SignSaveDTO;
import com.cdqckj.gmis.sms.dto.SignUpdateDTO;
import com.cdqckj.gmis.sms.entity.Sign;
import com.cdqckj.gmis.sms.enumeration.ExamineStatus;
import com.cdqckj.gmis.sms.service.SignService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import com.cdqckj.gmis.sms.strategy.SmsContext;
import com.tencentcloudapi.sms.v20190711.models.DescribeSignListStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 短信签名
 * </p>
 *
 * @author gmis
 * @date 2020-08-05
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class SignServiceImpl extends SuperServiceImpl<SignMapper, Sign> implements SignService {

    @Autowired
    private SmsContext smsContext;

    @Override
    public Boolean saveSign(SignSaveDTO signSaveDTO, String str) {
        smsContext.saveSign(signSaveDTO,str);
        Sign sign = BeanUtil.toBean(signSaveDTO, Sign.class);
        if(null!=sign.getSignId()){
            return save(sign);
        }else {
            return false;
        }
    }

    @Override
    public String updateSign(SignUpdateDTO signUpdateDTO, String str) {
        Sign sign = getById(signUpdateDTO.getId());
        signUpdateDTO.setSignId(sign.getSignId());
        if(null!=sign){
            String mess = smsContext.updateSign(signUpdateDTO,str);
            if(null==mess){
                sign = BeanUtil.toBean(signUpdateDTO, Sign.class);
                sign.setSignStatus(ExamineStatus.UNDER_REVIEW.getCode());
                sign.setReviewReply(null);
                updateById(sign);
            }else{
                return mess;
            }
        }
        return null;
    }

    @Override
    public void getStstus(Sign sign) {
        smsContext.getStstus(sign);
    }

    @Override
    public R<Sign> getDefaultSign() {
        Sign sign = new Sign();
        sign.setDeleteStatus(ExamineStatus.APPROVED.getCode()).setSignStatus(ExamineStatus.APPROVED.getCode());
        QueryWrap<Sign> wrapper = Wraps.q(sign);
        List<Sign> list = list(wrapper);
        return list.size()> 0? R.success(list.get(0)):R.fail("没有签名");
    }
}
