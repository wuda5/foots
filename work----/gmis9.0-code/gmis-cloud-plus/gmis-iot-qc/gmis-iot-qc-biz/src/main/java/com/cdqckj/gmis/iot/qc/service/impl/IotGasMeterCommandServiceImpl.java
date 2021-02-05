package com.cdqckj.gmis.iot.qc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.ErrorCode;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.base.utils.ExecuteStatus;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.iot.qc.dao.IotGasMeterCommandMapper;
import com.cdqckj.gmis.iot.qc.dto.BusinessStageModel;
import com.cdqckj.gmis.iot.qc.dto.MessageDTO;
import com.cdqckj.gmis.iot.qc.dto.RetryModel;
import com.cdqckj.gmis.iot.qc.entity.*;
import com.cdqckj.gmis.iot.qc.enumeration.CommandState;
import com.cdqckj.gmis.iot.qc.enumeration.ExecuteState;
import com.cdqckj.gmis.iot.qc.qnms.constant.GMISConstant;
import com.cdqckj.gmis.iot.qc.qnms.constant.HttpCodeConstant;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotCacheUtil;
import com.cdqckj.gmis.iot.qc.qnms.utils.IotRestUtil;
import com.cdqckj.gmis.iot.qc.qnms.utils.MQUtils;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterCommandDetailService;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterCommandService;
import com.cdqckj.gmis.iot.qc.service.IotGasMeterUploadDataService;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import com.cdqckj.gmis.iot.qc.vo.RetryVO;
import com.cdqckj.gmis.mq.producer.utils.ProducerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * <p>
 * 业务实现类
 * 物联网气表指令数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class IotGasMeterCommandServiceImpl extends SuperServiceImpl<IotGasMeterCommandMapper, IotGasMeterCommand> implements IotGasMeterCommandService {

    @Autowired
    private ProducerUtils producerUtils;

    @Autowired
    private IotGasMeterUploadDataService iotGasMeterUploadDataService;

    @Autowired
    private IotCacheUtil iotCacheUtil;

    @Autowired
    private IotRestUtil iotRestUtil;

    @Autowired
    private IotGasMeterCommandDetailService iotGasMeterCommandDetailService;

    @Override
    public Page<CommandEchoVO> queryCommand(CommandVO params) {
        Page<CommandEchoVO> page = new Page<>(params.getPageNo(),params.getPageSize());
        return baseMapper.queryCommand(page, params);
    }

    @Override
    public IotR retry(String domainId,RetryVO retryVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){return IotR.error(ErrorCode.DOMAIN_ID_NOTEXIST);}
        if(retryVO.getGasMeterNumber()==null){return IotR.error(ErrorCode.METERNO_NOTEXIST);}
        retryVO.setDomainId(domainId);
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(retryVO.getDomainId());
        //设置重试参数
        RetryModel params = new RetryModel();
        params.setDomainId(retryVO.getDomainId());
        params.setTransactionNo(retryVO.getTransactionNo());
        String sendData = JSONObject.toJSONString(params);
        //向3.0发送重试指令
        JSONObject res= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/retrybus",sendData,
                true,retryVO.getDomainId());
        if(!HttpCodeConstant.SUCCESS.equals(res.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request retry command faild,meterNo:"+retryVO.getGasMeterNumber()+", error:"
                    +res.getString(HttpCodeConstant.MESSAGE)+", code:"+res.getString(HttpCodeConstant.RESULT_CODE));
            return IotR.error(-1,res.getString(HttpCodeConstant.MESSAGE));
        }
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR cancel(String domainId, RetryVO retryVO) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){return IotR.error(ErrorCode.DOMAIN_ID_NOTEXIST);}
        if(retryVO.getGasMeterNumber()==null){return IotR.error(ErrorCode.METERNO_NOTEXIST);}
        retryVO.setDomainId(domainId);
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(retryVO.getDomainId());
        //设置重试参数
        RetryModel params = new RetryModel();
        params.setDomainId(retryVO.getDomainId());
        params.setTransactionNo(retryVO.getTransactionNo());
        String sendData = JSONObject.toJSONString(params);
        //向3.0发送撤销指令
        JSONObject res= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/cancelbus",sendData,
                true,retryVO.getDomainId());
        if(!HttpCodeConstant.SUCCESS.equals(res.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request cancel command faild,meterNo:"+retryVO.getGasMeterNumber()+", error:"
                    +res.getString(HttpCodeConstant.MESSAGE)+", code:"+res.getString(HttpCodeConstant.RESULT_CODE));
            return IotR.error(-1,res.getString(HttpCodeConstant.MESSAGE));
        }
        LbqWrapper<IotGasMeterCommandDetail> wrapper = new LbqWrapper<>();
        wrapper.eq(IotGasMeterCommandDetail::getTransactionNo,retryVO.getTransactionNo());
        IotGasMeterCommandDetail iotGasMeterCommandDetail = iotGasMeterCommandDetailService.getOne(wrapper);
        iotGasMeterCommandDetail.setCommandStage(CommandState.CancleExecute.getCodeValue());
        iotGasMeterCommandDetailService.updateById(iotGasMeterCommandDetail);
        return IotR.ok(ErrorCode.SUCCESS);
    }

    @Override
    public IotR businessStage(String domainId, String transactionNo) throws Exception {
        if(StringUtil.isNullOrBlank(domainId)){return IotR.error(ErrorCode.DOMAIN_ID_NOTEXIST);}
        //设置重试参数
        BusinessStageModel params = new BusinessStageModel();
        params.setDomainId(domainId);
        params.setTransactionNo(transactionNo);
        IotSubscribe iotGlobal = iotCacheUtil.getMessageFactoryEntity(domainId);
        String sendData = JSONObject.toJSONString(params);
        //向3.0发送获取业务指令状态指令
        JSONObject res= iotRestUtil.postMessage(iotGlobal.getGatewayUrl()+"device/cancelbus",sendData,
                true,domainId);
        if(!HttpCodeConstant.SUCCESS.equals(res.getString(HttpCodeConstant.RESULT_CODE))){
            log.error("request cancel command faild, error:"
                    +res.getString(HttpCodeConstant.MESSAGE)+", code:"+res.getString(HttpCodeConstant.RESULT_CODE));
            return IotR.error(-1,res.getString(HttpCodeConstant.MESSAGE));
        }
        return IotR.ok();
    }

}
