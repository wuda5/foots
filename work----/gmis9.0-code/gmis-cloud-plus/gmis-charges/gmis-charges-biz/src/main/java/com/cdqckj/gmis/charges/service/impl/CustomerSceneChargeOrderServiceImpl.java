package com.cdqckj.gmis.charges.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.charges.dao.CustomerSceneChargeOrderMapper;
import com.cdqckj.gmis.charges.entity.CustomerSceneChargeOrder;
import com.cdqckj.gmis.charges.enums.ChargeStatusEnum;
import com.cdqckj.gmis.charges.service.CustomerSceneChargeOrderService;
import com.cdqckj.gmis.common.enums.BizFCode;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.BizCodeUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import com.cdqckj.gmis.systemparam.service.TollItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 客户场景费用单
 * </p>
 *
 * @author tp
 * @date 2020-08-25
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerSceneChargeOrderServiceImpl extends SuperServiceImpl<CustomerSceneChargeOrderMapper, CustomerSceneChargeOrder> implements CustomerSceneChargeOrderService {
    @Autowired
    private TollItemService tollItemService;

    @Autowired
    private GasMeterService gasMeterService;

    public int updateChargeStatusComplete(List<Long> ids,String chargeNo){
        return baseMapper.updateChargeStatusComplete(ids,chargeNo);
    }
    public int updateChargeStatusUncharge(List<Long> ids){
        return baseMapper.updateChargeStatusUncharge(ids);
    }
    public Boolean creatSceneOrders(String gasMeterCode,String customerCode,String sceneCode,String bizNoOrId,Boolean isImportOpenAccount){
        //加载系统场景收费项进行生成场景收费项记录---后续如果每个公司单独配置是否对该场景收费，在这里进行判断。
        List<TollItem> items=tollItemService.list(new LbqWrapper<TollItem>()
                .eq(TollItem::getSceneCode,sceneCode)
                .eq(TollItem::getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
        if(CollectionUtils.isEmpty(items)){
//            throw new BizException("该场景未配置收费项,");
            return true;
        }

        GasMeter meter=gasMeterService.findGasMeterByCode(gasMeterCode);

        List<CustomerSceneChargeOrder> saveDTOs=new ArrayList<>();
        CustomerSceneChargeOrder order;
        String chargeOrderNo=genChargeNo(sceneCode);
        for (TollItem item : items) {
            order=new CustomerSceneChargeOrder()
                    .setGasmeterCode(gasMeterCode)
                    .setUseGasTypeId(meter.getUseGasTypeId())
                    .setUseGasTypeName(meter.getUseGasTypeName())
                    .setCustomerCode(customerCode)
//                    .setGasmeterName(meter.getGasMeterTypeName())
                    .setSceneChargeNo(chargeOrderNo)
                    .setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue())
                    .setBusinessNo(bizNoOrId) //不生成直接用ID
                    .setChargeMoney(item.getMoney())
                    .setTollItemFrequency(item.getChargeFrequency())
                    .setTollItemId(item.getId())
                    .setTollItemName(item.getItemName())
                    .setTollItemScene(item.getSceneCode())
                    .setChargeStatus(ChargeStatusEnum.UNCHARGE.getCode())
                    .setTotalMoney(item.getMoney());
            if(isImportOpenAccount!=null && isImportOpenAccount){
                order.setDataStatus(DataStatusEnum.NORMAL.getValue());
            }
            saveDTOs.add(order);
        }
        //批量保存
        return saveBatch(saveDTOs);
//        this.baseMapper.insertBatchSomeColumn(saveDTOs);
    }

    public List<CustomerSceneChargeOrder> loadSceneOrders(String sceneCode,String bizNoOrId){
        return list(new LbqWrapper<CustomerSceneChargeOrder>()
                .eq(CustomerSceneChargeOrder::getTollItemScene,sceneCode)
                .eq(CustomerSceneChargeOrder::getBusinessNo,bizNoOrId)
        );
    }

    public CustomerSceneChargeOrder loadOpenAccountSceneOrders(String sceneCode,String gasMeterCode,String customerCode){
        return getOne(new LbqWrapper<CustomerSceneChargeOrder>()
                .eq(CustomerSceneChargeOrder::getTollItemScene,sceneCode)
                .eq(CustomerSceneChargeOrder::getGasmeterCode,gasMeterCode)
                .eq(CustomerSceneChargeOrder::getCustomerCode,customerCode)
                .eq(CustomerSceneChargeOrder::getDataStatus,DataStatusEnum.NORMAL.getValue())
        );
    }

    private String genChargeNo(String sceneCode){
        StringBuilder genKey;
        if(sceneCode.equals(TolltemSceneEnum.ISSUE_CARD.getCode())){
           genKey=new StringBuilder(BizCodeUtil.CHARGE_ORDER_ISSUECARD).append(":").append(BaseContextHandler.getTenant());
//            return BizCodeUtil.genBizChargeNumber(BizFCode.KK,genKey.toString());
            return BizCodeNewUtil.genCustomerSceneChargeNumber(BizFCode.KK);
        }

        if(sceneCode.equals(TolltemSceneEnum.CARD_REPLACEMENT.getCode())){
//            return BizCodeUtil.genBizChargeNumber(BizFCode.BK,BizCodeUtil.CHARGE_ORDER_REPCARD);
            return BizCodeNewUtil.genCustomerSceneChargeNumber(BizFCode.BK);
        }

        if(sceneCode.equals(TolltemSceneEnum.GAS_COMPENSATION.getCode())){
//            return BizCodeUtil.genBizChargeNumber(BizFCode.BK,BizCodeUtil.CHARGE_ORDER_REPGAS);
            return BizCodeNewUtil.genCustomerSceneChargeNumber(BizFCode.BK);
        }

        if(sceneCode.equals(TolltemSceneEnum.TRANSFER.getCode())){
//            return BizCodeUtil.genBizChargeNumber(BizFCode.GH,BizCodeUtil.CHARGE_ORDER_TRANACCOUNT);
            return BizCodeNewUtil.genCustomerSceneChargeNumber(BizFCode.GH);
        }

        if(sceneCode.equals(TolltemSceneEnum.OPEN_ACCOUNT.getCode())){
  //          return BizCodeUtil.genBizChargeNumber(BizFCode.KH,BizCodeUtil.CHARGE_ORDER_OPENACCOUNT);
            return BizCodeNewUtil.genCustomerSceneChargeNumber(BizFCode.KH);
        }

        if(sceneCode.equals(TolltemSceneEnum.CHANGE_METER.getCode())){
  //          return BizCodeUtil.genBizChargeNumber(BizFCode.HB,BizCodeUtil.CHARGE_ORDER_REPMETER);
            return BizCodeNewUtil.genCustomerSceneChargeNumber(BizFCode.HB);
        }

        if(sceneCode.equals(TolltemSceneEnum.DIS_METER.getCode())){
  //          return BizCodeUtil.genBizChargeNumber(BizFCode.CB,BizCodeUtil.CHARGE_ORDER_DISMETER);
            return BizCodeNewUtil.genCustomerSceneChargeNumber(BizFCode.CB);
        }

        return null;
    }
}
