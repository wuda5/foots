package com.cdqckj.gmis.biztemporary.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.archive.GasMeterInfoBizApi;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.biztemporary.dao.GasmeterTransferAccountMapper;
import com.cdqckj.gmis.biztemporary.dto.GasMeterInfoVO;
import com.cdqckj.gmis.biztemporary.entity.GasmeterTransferAccount;
import com.cdqckj.gmis.biztemporary.enums.TransferAccountStatusEnum;
import com.cdqckj.gmis.biztemporary.service.GasmeterTransferAccountService;
import com.cdqckj.gmis.biztemporary.vo.GasMeterTransferAccountVO;
import com.cdqckj.gmis.card.api.CardInfoBizApi;
import com.cdqckj.gmis.card.dto.CardInfoSaveDTO;
import com.cdqckj.gmis.card.entity.CardInfo;
import com.cdqckj.gmis.charges.api.ChargeBizApi;
import com.cdqckj.gmis.charges.dto.ChargeLoadDTO;
import com.cdqckj.gmis.charges.dto.ChargeLoadReqDTO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.enums.OrderSourceNameEnum;
import com.cdqckj.gmis.common.enums.gasmeter.MeterCustomerRelatedOperTypeEnum;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoUpdateDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.entity.GasMeterInfo;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterUploadData;
import com.cdqckj.gmis.lot.DeviceBizApi;
import com.cdqckj.gmis.operateparam.util.GmisSysSettingUtil;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.userarchive.dto.CustomerSaveDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerUpdateDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 柜台综合：过户业务表，业务状态未完成之前不会更改主表数据
 * </p>
 *
 * @author gmis
 * @date 2020-11-06
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasmeterTransferAccountServiceImpl extends SuperServiceImpl<GasmeterTransferAccountMapper, GasmeterTransferAccount> implements GasmeterTransferAccountService {


    @Autowired
    private CustomerGasMeterRelatedService customerGasMeterRelatedService;

    @Autowired
    private CustomerBizApi customerBizApi;

    @Autowired
    private ChargeBizApi chargeBizApi;

    @Autowired
    private GasMeterBookRecordApi gasMeterBookRecordApi;

    @Autowired
    private GasMeterInfoBizApi gasMeterInfoBizApi;

    @Autowired
    private CardInfoBizApi cardInfoBizApi;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private DeviceBizApi deviceBizApi;

    @Override
    public GasmeterTransferAccount getOneByGasCode(String gasCode) {

        LbqWrapper<GasmeterTransferAccount> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(GasmeterTransferAccount::getGasMeterCode, gasCode)
                .gt(GasmeterTransferAccount::getStatus, TransferAccountStatusEnum.SUCCESS.getKey());

        return super.getOne(lbqWrapper);
    }

    @Override
    public GasmeterTransferAccount transferAccountDataDeal(GasmeterTransferAccount transferAccount, GasMeterInfoVO gasMeter, Customer customer) {

        String customerCode = transferAccount.getCustomerCode();
        String gasMeterCode = transferAccount.getGasMeterCode();
        String oldCustomerCode = transferAccount.getOldCustomerCode();

        //如果是物联网表需要更新物联网转接服务档案管理
        if (OrderSourceNameEnum.CENTER_RECHARGE.getCode().equals(gasMeter.getGasMeterTypeCode())
                || OrderSourceNameEnum.REMOTE_READMETER.getCode().equals(gasMeter.getGasMeterTypeCode())
                || OrderSourceNameEnum.REMOTE_RECHARGE.getCode().equals(gasMeter.getGasMeterTypeCode())) {
            IotGasMeterUploadData iot = deviceBizApi.queryIotGasMeter(gasMeterCode, oldCustomerCode);
            if (null != iot) {
                iot.setCustomerCode(customerCode);
                iot.setCustomerName(customer.getCustomerName());
                deviceBizApi.updateIotGasMeter(iot);
            }
        }
        //变更客户档案为有效
        CustomerUpdateDTO updateDTO = new CustomerUpdateDTO();
        updateDTO.setId(customer.getId());
        updateDTO.setCustomerStatus(1);
        customerBizApi.update(updateDTO);
        /**变更客户表具关联关系**/
        //获取表具用户关联关系
        CustomerGasMeterRelated cusGasRelated = customerGasMeterRelatedService.findOneByCode(transferAccount.getGasMeterCode());

        //修改旧关联关系为过户状态
        cusGasRelated.setDataStatus(0);
        cusGasRelated.setOperType(MeterCustomerRelatedOperTypeEnum.TRANS_ACCOUNT.getCode());
        customerGasMeterRelatedService.updateById(cusGasRelated);

        //新增新的表具关联关系
        CustomerGasMeterRelated saveData = new CustomerGasMeterRelated();
        saveData.setDataStatus(1);
        saveData.setOperType(MeterCustomerRelatedOperTypeEnum.TRANS_ACCOUNT.getCode());
        saveData.setCustomerCode(customerCode);
        saveData.setGasMeterCode(gasMeterCode);
        saveData.setCustomerChargeNo(transferAccount.getCustomerChargeNo());
        saveData.setCustomerChargeFlag(GmisSysSettingUtil.getOpenCustomerPrefix());
        saveData.setCompanyCode(BaseContextHandler.getTenant());
        saveData.setCompanyName(BaseContextHandler.getTenantName());
        saveData.setOrgId(BaseContextHandler.getOrgId());
        saveData.setOrgName(BaseContextHandler.getOrgName());
        customerGasMeterRelatedService.save(saveData);

        //变更过户单状态为完成
        transferAccount.setStatus(TransferAccountStatusEnum.SUCCESS.getKey());
        super.updateById(transferAccount);

        //变更info表
        //新增info表
        GasMeterInfoSaveDTO saveDTO = new GasMeterInfoSaveDTO();
        R<GasMeterInfo> meterInfoR = gasMeterInfoBizApi.getByMeterAndCustomerCode(gasMeterCode, oldCustomerCode);
        if (meterInfoR.getData() != null) {
            //将原有的更新为删除状态
            GasMeterInfo data = meterInfoR.getData();
            GasMeterInfoUpdateDTO upData = new GasMeterInfoUpdateDTO();
            upData.setDataStatus(DataStatusEnum.NOT_AVAILABLE.getValue());
            upData.setId(data.getId());

            saveDTO = BeanPlusUtil.copyProperties(data, GasMeterInfoSaveDTO.class);

            gasMeterInfoBizApi.update(upData);
        }

        saveDTO.setGasmeterCode(gasMeterCode);
        saveDTO.setCustomerCode(customerCode);
        saveDTO.setDataStatus(DataStatusEnum.NORMAL.getValue());
        gasMeterInfoBizApi.save(saveDTO);

        //变更cardInfo表
        CardInfo cardInfo = cardInfoBizApi.getByGasMeterCode(gasMeterCode, oldCustomerCode).getData();
        if (null != cardInfo) {
            cardInfoBizApi.invalidCardById(cardInfo.getId().longValue());
            CardInfoSaveDTO saveDTO1 = BeanPlusUtil.copyProperties(cardInfo, CardInfoSaveDTO.class);
            saveDTO1.setCustomerCode(customerCode);
            saveDTO1.setCustomerName("");
            saveDTO1.setDataStatus(DataStatusEnum.NORMAL.getValue());
            saveDTO1.setCardNo(cardInfo.getCardNo());
            saveDTO1.setCardStatus(cardInfo.getCardStatus());
            cardInfoBizApi.save(saveDTO1);
        }
        //修改气表使用人数
        GasMeter updata = new GasMeter();
        updata.setGasCode(transferAccount.getGasMeterCode());
        updata.setPopulation(transferAccount.getPopulation());
        gasMeterBizApi.updateByCode(updata);

        //修改抄表测
        GasMeterBookRecord querDa = new GasMeterBookRecord();
        querDa.setGasMeterCode(gasMeterCode);
        querDa.setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
        R<GasMeterBookRecord> recordR = gasMeterBookRecordApi.queryOne(querDa);
        if (recordR.getIsSuccess() && null != recordR.getData()) {
            GasMeterBookRecord rData = recordR.getData();
            GasMeterBookRecordUpdateDTO build = GasMeterBookRecordUpdateDTO.builder()
                    .customerCode(customerCode)
                    .customerName(customer.getCustomerName())
                    .customerChargeNo(transferAccount.getCustomerChargeNo())
                    .id(rData.getId())
                    .build();
            gasMeterBookRecordApi.update(build);
        }

        return transferAccount;
    }

    @Override
    public R<Boolean> checkAdd(String idCard) {

        //新客户身份证重复性校验
        CustomerSaveDTO queryData = new CustomerSaveDTO();
        queryData.setIdCard(idCard);
        R<Boolean> check = customerBizApi.checkAdd(queryData);

        return check;
    }

    @Override
    public GasmeterTransferAccount transferAccountCheck(String gasMeterCode, String customerCode) {

        GasmeterTransferAccount oneByGasCode = this.getOneByGasCode(gasMeterCode);
        if (null == oneByGasCode) {
            oneByGasCode = new GasmeterTransferAccount();
            oneByGasCode.setGasMeterCode(gasMeterCode);
        }

        return oneByGasCode;
    }

    @Override
    public Boolean checkCharge(String customerCode, String gasMeterCode) {

        Boolean flag = false;
        R<ChargeLoadDTO> chargeLoadDTOR = chargeBizApi.chargeLoad(ChargeLoadReqDTO.builder().gasMeterCode(gasMeterCode).customerCode(customerCode).build());
        ChargeLoadDTO loadDTORData = chargeLoadDTOR.getData();
        if (null != loadDTORData && loadDTORData.getTotalMoney().compareTo(BigDecimal.ZERO) > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 业务关注点查询过户记录列表
     *
     * @param customerCode 客户编号
     * @param gasMeterCode 表具编号
     * @return 过户记录列表
     */
    @Override
    public List<GasMeterTransferAccountVO> queryFocusInfo(String customerCode, String gasMeterCode) {
        List<Long> orgIds = UserOrgIdUtil.getUserDataScopeList();
        return baseMapper.queryFocusInfo(customerCode, gasMeterCode, orgIds);
    }


    /**
     * 新增用户
     *
     * @param transferAccount
     * @author hc
     */
    private R<Customer> addCustomer(GasmeterTransferAccount transferAccount) {
        List<CustomerSaveDTO> saveList = new ArrayList<>();
        CustomerSaveDTO saveDTO = BeanPlusUtil.copyProperties(transferAccount, CustomerSaveDTO.class);
        saveList.add(saveDTO);

        R<List<Customer>> r = customerBizApi.saveBatch(saveList);
        if (CollectionUtils.isEmpty(r.getData())) {
            return R.success(null);
        }
        return R.success(r.getData().get(0));
    }

    @Override
    public Long stsTransferNum(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsTransferNum(stsSearchParam, dataScope);
    }
}
