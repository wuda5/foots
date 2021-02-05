package com.cdqckj.gmis.bizcenter.charges.service.impl;

import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.bizcenter.charges.service.BizOrderService;
import com.cdqckj.gmis.bizcenter.temp.counter.service.ChangeMeterService;
import com.cdqckj.gmis.bizcenter.temp.counter.service.OpenAccountService;
import com.cdqckj.gmis.bizcenter.temp.counter.service.RemoveMeterService;
import com.cdqckj.gmis.biztemporary.BusinessTemporaryBizApi;
import com.cdqckj.gmis.biztemporary.entity.GasmeterTransferAccount;
import com.cdqckj.gmis.calculate.api.AdjustPriceRecordBizApi;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.card.api.CardRepRecordBizApi;
import com.cdqckj.gmis.card.dto.CardInfoUpdateDTO;
import com.cdqckj.gmis.card.dto.CardRepRecordUpdateDTO;
import com.cdqckj.gmis.card.enums.CardStatusEnum;
import com.cdqckj.gmis.card.enums.RepCardStatusEnum;
import com.cdqckj.gmis.charges.api.CustomerSceneChargeOrderBizApi;
import com.cdqckj.gmis.charges.api.GasmeterArrearsDetailBizApi;
import com.cdqckj.gmis.charges.dto.AdjustPriceRecordUpdateDTO;
import com.cdqckj.gmis.charges.entity.CustomerSceneChargeOrder;
import com.cdqckj.gmis.charges.entity.GasmeterArrearsDetail;
import com.cdqckj.gmis.charges.enums.AdjustPriceStateEnum;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataUpdateDTO;
import com.cdqckj.gmis.readmeter.enumeration.ChargeEnum;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 改场景业务状态相关操作
 * @author tp
 * @date 2020-09-04
 */
@Slf4j
@Service
public class BizOrderServiceImpl implements BizOrderService {

    @Autowired
    CustomerSceneChargeOrderBizApi customerSceneChargeOrderBizApi;

    @Autowired
    GasmeterArrearsDetailBizApi gasmeterArrearsDetailBizApi;

    @Autowired
    RemoveMeterService removeMeterService;

    @Autowired
    ChangeMeterService changeMeterService;

    @Autowired
    BusinessTemporaryBizApi businessTemporaryBizApi;

    @Autowired
    AdjustPriceRecordBizApi adjustPriceRecordBizApi;

    @Autowired
    ReadMeterDataApi readMeterDataApi;
    @Autowired
    CardInfoBizApi cardInfoBizApi;

    @Autowired
    CardRepRecordBizApi cardRepRecordBizApi;
    @Autowired
    I18nUtil i18nUtil;

    @Autowired
    OpenAccountService openAccountService;

    @Override
    public R<Boolean> updateSceneBizStatus(List<Long> sceneIds, Boolean isBack) {
        if(CollectionUtils.isNotEmpty(sceneIds)) {
            List<CustomerSceneChargeOrder> chargeOrders = customerSceneChargeOrderBizApi.queryList(sceneIds).getData();
            if (CollectionUtils.isNotEmpty(chargeOrders)) {
                //注意这里如果每个场景收费项不止一个，当前设计是可以是一个也可以支持多个收费项，这里需要按场景业务去重后处理，保证只调用一次
                chargeOrders=chargeOrders.stream().filter(distinctByKey(e -> e.getBusinessNo())).collect(Collectors.toList());
                R<Boolean> bizDealResult;
                for (CustomerSceneChargeOrder chargeOrder : chargeOrders) {
                    bizDealResult=switchBiz(chargeOrder.getBusinessNo(),isBack,chargeOrder.getTollItemScene());
                    if(bizDealResult.getIsError()){
                        //业务但处理失败直接返回回滚事务，回滚在收费中回滚
                        return bizDealResult;
                    }
                }
            }
        }
        return R.success(true);
    }

    /**
     * 不同场景调用不同业务回调方法
     * @param bizNo
     * @param isBack
     * @param scenceType
     */
    private  R<Boolean> switchBiz(String bizNo,Boolean isBack,String scenceType ){
        if(TolltemSceneEnum.CARD_REPLACEMENT.getCode().equals(scenceType)){
            //补卡
            return repCard(bizNo,isBack);
        }
        if(TolltemSceneEnum.ISSUE_CARD.getCode().equals(scenceType)){
            //开卡
            return openCard(bizNo,isBack);
        }
//        if(TolltemSceneEnum.CHANGE_METER.getCode().equals(scenceType)){
//            //换表
//            return repMeter(bizNo,isBack);
//        }
//        if(TolltemSceneEnum.DIS_METER.getCode().equals(scenceType)){
//            //拆表
//            return disMeter(bizNo,isBack);
//        }
        if(TolltemSceneEnum.TRANSFER.getCode().equals(scenceType)){
            //过户
            return repAccount(bizNo,isBack);
        }
        if(TolltemSceneEnum.OPEN_ACCOUNT.getCode().equals(scenceType)){
            //开户
            return openAccount(bizNo,isBack);
        }
//        if(TolltemSceneEnum.INSTALL.getCode().equals(scenceType)){
//            //报装
//            return install(bizNo,isBack);
//        }
        return R.success(true);
    }

    /**
     * 根据属性中某一个字段去重
     * @param keyExtractor
     * @param <T>
     * @return
     */
    private  <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
    /**
     * 补卡
     * @return
     */
    private R<Boolean> repCard(String bizNo,Boolean isBack){
        Long id=Long.parseLong(bizNo);
        if(isBack){
            cardRepRecordBizApi.update(new CardRepRecordUpdateDTO()
                            .setId(id)
                            .setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue())
//                .setRepCardStatus(isBack? RepCardStatusEnum.WAITE_CHARGE.getCode():RepCardStatusEnum.WAITE_WRITE_CARD.getCode())
            );
        }else{
            cardRepRecordBizApi.update(new CardRepRecordUpdateDTO()
                            .setId(id)
                            .setDataStatus(DataStatusEnum.NORMAL.getValue())
                            .setRepCardStatus(RepCardStatusEnum.WAITE_WRITE_CARD.getCode())
            );
        }
        return R.success(true);
    }
    /**
     * 开卡
     * @return
     */
    private R<Boolean> openCard(String bizNo,Boolean isBack){
        //业务单号直接是ID，所以直接可以转换，调用开卡处理接口回调处理
        Long id=Long.parseLong(bizNo);
//        GasMeterCard gasMeterCard=gasMeterCardBizApi.get(id).getData();
        if(isBack){
            cardInfoBizApi.invalidCardById(Long.parseLong(bizNo));
        }
        //发卡回调外面已处理
//        else{
//            cardInfoBizApi.update(new CardInfoUpdateDTO()
//                    .setId(id)
//                    .setCardStatus(CardStatusEnum.WAITE_WRITE_CARD.getCode())
//                    .setDataStatus(DataStatusEnum.NORMAL.getValue())
//            );
//        }
        return R.success(true);
    }




    /**
     * 过户
     * @return
     */
    private R<Boolean> repAccount(String bizNo,Boolean isBack){
        if(isBack){
            R<Boolean> r= businessTemporaryBizApi.transferAccountCallChargeBack(Long.parseLong(bizNo));
            if(r.getIsError() || r.getData()==null || !r.getData()){
                throw BizException.wrap(r.getMsg());
            }
        }
        R<GasmeterTransferAccount> r1= businessTemporaryBizApi.transferAccountCallBack(Long.parseLong(bizNo));
        if(r1.getIsError()){
            throw BizException.wrap(r1.getMsg());
        }
        return R.success(true);
    }


    /**
     * 开户
     * @return
     */
    private R<Boolean> openAccount(String bizNo,Boolean isBack){
        openAccountService.chargeCallback(bizNo,isBack);
        return R.success(true);
    }
    /**
     * 气量补差
     * @return
     */
    public R<Boolean> updateAdjustPriceBizStatus(List<Long> ids,Boolean isBack){
        if(CollectionUtils.isEmpty(ids)){
            return R.success(true);
        }
        List<AdjustPriceRecordUpdateDTO> updateDTOS=new ArrayList<>();
        AdjustPriceRecordUpdateDTO temp;
        for (Long id : ids) {
            temp=new AdjustPriceRecordUpdateDTO();
            temp.setId(id);
            temp.setDataStatus(isBack?AdjustPriceStateEnum.WAIT_CHARGE.getCode():AdjustPriceStateEnum.CHARGED.getCode());
            updateDTOS.add(temp);
        }
        adjustPriceRecordBizApi.updateBatchById(updateDTOS);
        return R.success(true);
    }

    /**
     * 抄表数据
     * @return
     */
    @Override
    public R<Boolean> updateReadMeterBizStatus(List<Long> ids,Boolean isBack){
        List<ReadMeterDataUpdateDTO> updateDTOS=new ArrayList<>();
        if(ids==null){
            return R.success(true);
        }
        List<GasmeterArrearsDetail> arrears=gasmeterArrearsDetailBizApi.queryList(ids).getData();
        for (GasmeterArrearsDetail arrear : arrears) {
            updateDTOS.add(new ReadMeterDataUpdateDTO().setChargeStatus(!isBack?ChargeEnum.CHARGED:ChargeEnum.NO_CHARGE).setId(arrear.getReadmeterDataId()));
        }
        if(updateDTOS.size()>0) {
            return readMeterDataApi.updateBatch(updateDTOS);
        }

        //            Boolean readMeterResult=readMeterDataService.updateBatchById(updateReadMeters);
//            if(!readMeterResult){
//                R.fail(i18nUtil.getMessage(ChargeMessageConstants.CHARGE_REFUND_ERR_RESTORE_READMETER_SERVICE_EX));
//            }
        return R.success(true);
    }
}