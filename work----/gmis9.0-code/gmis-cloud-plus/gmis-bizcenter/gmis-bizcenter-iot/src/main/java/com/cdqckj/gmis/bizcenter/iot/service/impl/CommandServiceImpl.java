package com.cdqckj.gmis.bizcenter.iot.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.bizcenter.iot.service.CommandService;
import com.cdqckj.gmis.common.utils.MeterFactoryCacheConfig;
import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommandDetail;
import com.cdqckj.gmis.iot.qc.entity.MeterCacheVO;
import com.cdqckj.gmis.iot.qc.enumeration.CommandType;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import com.cdqckj.gmis.iot.qc.vo.RetryVO;
import com.cdqckj.gmis.lot.DeviceBizApi;
import com.cdqckj.gmis.lot.IotGasMeterCommandBizApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommandServiceImpl extends SuperCenterServiceImpl implements CommandService {
    @Autowired
    private IotGasMeterCommandBizApi iotGasMeterCommandBizApi;
    @Autowired
    private MeterFactoryCacheConfig meterFactoryCacheConfig;
    @Autowired
    private DeviceBizApi deviceBizApi;
    @Autowired
    CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @Override
    public R<Page<CommandEchoVO>> queryCommand(CommandVO params) {

        String customerChargeNo = params.getCustomerChargeNo();
        if (!StringUtils.isEmpty(customerChargeNo)) {
            List<CustomerGasMeterRelated> relatedList = customerGasMeterRelatedBizApi.queryByChargeNo(customerChargeNo).getData();
            List<String> collect = relatedList.stream().map(CustomerGasMeterRelated::getGasMeterCode).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(collect)){
                return R.success(new Page<>(params.getPageNo(),params.getPageSize()));
            }
            params.setGasMeterCodeList(collect);
        }
        return iotGasMeterCommandBizApi.queryCommand(params);
    }

    @Override
    public IotR retry(List<RetryVO> retryList) throws Exception {
        //气表缓存
        List<String> meterList = new ArrayList<>();
        for(RetryVO vo:retryList){
            meterList.add(vo.getGasMeterNumber());
        }
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        deviceBizApi.meterCache(meterCacheVO);

        IotR rs = null;
        if(retryList.size()>0){
           for(RetryVO retryVO:retryList){
               String domainId = meterFactoryCacheConfig.getReceiveDomainId(retryVO.getGasMeterNumber());
               if(StringUtil.isNullOrBlank(retryVO.getTransactionNo())){
                   IotGasMeterCommandDetail detail = iotGasMeterCommandBizApi.getByCommandId(retryVO.getCommandId());
                   if(null!=detail){
                       if(detail.getCommandType()== CommandType.ADDDOMAIN){
                           iotGasMeterCommandBizApi.addDomainRetry(domainId,detail);
                       }else if(detail.getCommandType() == CommandType.REGISTER){
                           iotGasMeterCommandBizApi.registerRetry(domainId,detail);
                       }else if(detail.getCommandType() == CommandType.UPDATEDEVICE){
                           iotGasMeterCommandBizApi.updateRetry(domainId,detail);
                       }else if(detail.getCommandType() == CommandType.REMOVEDEVICE){
                           iotGasMeterCommandBizApi.removeDeviceRetry(domainId,detail);
                       }else if(detail.getCommandType() == CommandType.REMOVEDOMAIN){
                           iotGasMeterCommandBizApi.removeDomainRetry(domainId,detail);
                       }
                   }
               }else{
                   rs = iotGasMeterCommandBizApi.retry(domainId,retryVO);
               }
           }
           return rs;
        }
        return IotR.ok();
    }

    @Override
    public IotR cancel(List<RetryVO> retryList) {
        //气表缓存
        List<String> meterList = new ArrayList<>();
        for(RetryVO vo:retryList){
            meterList.add(vo.getGasMeterNumber());
        }
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        deviceBizApi.meterCache(meterCacheVO);

        IotR rs = null;
        if(retryList.size()>0){
            for(RetryVO retryVO:retryList){
                String domainId = meterFactoryCacheConfig.getReceiveDomainId(retryVO.getGasMeterNumber());
                rs = iotGasMeterCommandBizApi.cancel(domainId,retryVO);
            }
            return rs;
        }
        return IotR.ok();
    }

    @Override
    public IotR businessStage(RetryVO retryVO) {
        //气表缓存
        List<String> meterList = new ArrayList<>();
        meterList.add(retryVO.getGasMeterNumber());
        MeterCacheVO meterCacheVO = new MeterCacheVO();
        meterCacheVO.setMeterList(meterList);
        deviceBizApi.meterCache(meterCacheVO);
        String domainId = meterFactoryCacheConfig.getReceiveDomainId(retryVO.getGasMeterNumber());
        return iotGasMeterCommandBizApi.businessStage(domainId,retryVO.getTransactionNo());
    }
}
