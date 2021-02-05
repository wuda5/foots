package com.cdqckj.gmis.statistics.domain.log;

import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.authority.api.UserBizApi;
import com.cdqckj.gmis.charges.api.ChargeInsuranceDetailBizApi;
import com.cdqckj.gmis.charges.api.ChargeItemRecordBizApi;
import com.cdqckj.gmis.device.GasMeterVersionBizApi;
import com.cdqckj.gmis.operateparam.BusinessHallBizApi;
import com.cdqckj.gmis.statistics.service.*;
import com.cdqckj.gmis.systemparam.entity.BusinessHall;
import com.cdqckj.gmis.userarchive.entity.Customer;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: lijianguo
 * @time: 2020/10/27 8:51
 * @remark: 系统的服务类
 */
@Getter
@Setter
@Component
public class SysServiceBean {

    @Autowired
    FeiDayService feiDayService;

    @Autowired
    InvoiceDayService invoiceDayService;

    @Autowired
    MeterPlanNowService meterPlanNowService;

    @Autowired
    AccountNowService accountNowService;

    @Autowired
    CustomerNowService customerNowService;

    @Autowired
    InsureNowService insureNowService;

    @Autowired
    MeterNowService meterNowService;

    @Autowired
    MeterUploadStsNowService meterUploadStsNowService;

    @Autowired
    GasFeiNowService gasFeiNowService;

    ///////////////////////我是华丽的分割线///////////////////////////
    @Autowired
    ChargeItemRecordBizApi chargeItemRecordBizApi;

    @Autowired
    UserBizApi userBizApi;

    @Autowired
    BusinessHallBizApi businessHallBizApi;

    @Autowired
    ChargeInsuranceDetailBizApi chargeInsuranceDetailBizApi;

    @Autowired
    GasMeterBizApi gasMeterBizApi;

    @Autowired
    CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;

    @Autowired
    CustomerBizApi customerBizApi;

    @Autowired
    GasMeterVersionBizApi gasMeterVersionBizApi;


    /**
     * @auth lijianguo
     * @date 2020/11/12 14:59
     * @remark 获取营业厅的信息
     */
    public BusinessHall getBusinessHall(Long orgId){
        if (orgId == null){
            return null;
        }
        return getBusinessHallBizApi().queryByOrgId(orgId);
    }

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/4 17:18
    * @remark 获取机构的Id
    */
    public Long getBusinessHallId(Long orgId){
        if (orgId == null){
            return null;
        }
        BusinessHall businessHall = getBusinessHallBizApi().queryByOrgId(orgId);
        if (businessHall == null){
            return null;
        }
        return businessHall.getId();
    }
}
