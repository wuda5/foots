package com.cdqckj.gmis.devicearchive.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.common.utils.UserOrgIdUtil;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.database.mybatis.conditions.update.LbuWrapper;
import com.cdqckj.gmis.devicearchive.dao.GasMeterMapper;
import com.cdqckj.gmis.devicearchive.dto.GasMeterExVo;
import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterVo;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterStatusEnum;
import com.cdqckj.gmis.devicearchive.enumeration.GasMeterVentilateStatusEnum;
import com.cdqckj.gmis.devicearchive.service.CustomerGasMeterRelatedService;
import com.cdqckj.gmis.devicearchive.service.GasMeterService;
import com.cdqckj.gmis.devicearchive.vo.FactoryAndVersion;
import com.cdqckj.gmis.devicearchive.vo.PageGasMeter;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.utils.I18nUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 业务实现类
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-14
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class GasMeterServiceImpl extends SuperServiceImpl<GasMeterMapper, GasMeter> implements GasMeterService {
    @Autowired
    private CustomerGasMeterRelatedService customerGasMeterRelatedService;
    @Resource
    private I18nUtil i18nUtil;
    @Override
    public List<GasMeter> findGasMeterByCustomerCode(String customerCode) {
        LbqWrapper<CustomerGasMeterRelated> wrapper = new LbqWrapper<>();
        wrapper.eq(CustomerGasMeterRelated::getCustomerCode,customerCode);
        wrapper.in(CustomerGasMeterRelated::getDataStatus, DataStatusEnum.NORMAL.getValue());
        List<CustomerGasMeterRelated> customerGasMeterRelatedList = customerGasMeterRelatedService.getBaseMapper().selectList(wrapper);
        List<String> gasMeterList =new ArrayList<>();
        if(customerGasMeterRelatedList !=null && customerGasMeterRelatedList.size()>0){
            for (CustomerGasMeterRelated customerGasMeterRelated : customerGasMeterRelatedList) {
                gasMeterList.add(customerGasMeterRelated.getGasMeterCode());
            }
        }
        if(!CollectionUtils.isEmpty(gasMeterList)){
            LbqWrapper<GasMeter> gasMeterwrapper = new LbqWrapper<>();
            gasMeterwrapper.in(GasMeter::getGasCode,gasMeterList);
            List<GasMeter> gasMeterResultList = baseMapper.selectList(gasMeterwrapper);
            return gasMeterResultList;
        }

        return new ArrayList<>();
    }

    @Override
    public IPage<GasMeter> findGasMeterPage(GasMeterVo params) {
        Page<GasMeter> page = new Page<>(params.getPageNo(),params.getPageSize());
        LbqWrapper<GasMeter> gasMeterLbqWrapper =new LbqWrapper<>();
        gasMeterLbqWrapper.eq(GasMeter::getGasCode,params.getGasCode());
        gasMeterLbqWrapper.like(GasMeter::getGasMeterAddress,params.getGasMeterAddress());
        gasMeterLbqWrapper.in(GasMeter::getDataStatus,params.getDataStatus());
        gasMeterLbqWrapper.eq(GasMeter::getGasMeterNumber,params.getGasMeterNumber());
        gasMeterLbqWrapper.like(GasMeter::getMoreGasMeterAddress,params.getMoreGasMeterAddress());
        gasMeterLbqWrapper.eq(GasMeter::getGasMeterFactoryId,params.getGasMeterFactoryId());
        gasMeterLbqWrapper.eq(GasMeter::getGasMeterVersionId,params.getGasMeterVersionId());
        gasMeterLbqWrapper.eq(GasMeter::getGasMeterModelId,params.getGasMeterModelId());
        gasMeterLbqWrapper.orderByDesc(GasMeter::getCreateTime);
        return this.getBaseMapper().selectPage(page,gasMeterLbqWrapper);
    }

    /**
     * 获取气表分页信息
     * @author hc
     * @param params
     * @return
     */
    @Override
    public IPage<PageGasMeter> findGasMeterPageEx(GasMeterVo params){
        //分页
        Page<GasMeter> page = new Page<>(params.getPageNo(),params.getPageSize());

        QueryWrapper<GasMeter> query = Wrappers.query();
        if(StringUtils.isNotEmpty(params.getGasCode())) {
            query.like("gm.gas_code", params.getGasCode());
        }
        if(StringUtils.isNotEmpty(params.getInstallNumber())) {
            query.like("gm.install_number", params.getInstallNumber());
        }
        if(StringUtils.isNotEmpty(params.getGasMeterNumber())){
            query.like("gm.gas_meter_number",params.getGasMeterNumber());
        }

        if(StringUtils.isNotEmpty(params.getMoreGasMeterAddress())){
            query.like("gm.more_gas_meter_address",params.getMoreGasMeterAddress());
        }

        if(null!=params.getDataStatus() && params.getDataStatus().length>0){
            query.in("gm.data_status",params.getDataStatus());
        }

        if(null!=params.getGasMeterFactoryId()) {
            query.eq("mf.id", params.getGasMeterFactoryId());
        }
        if(null!=params.getGasMeterVersionId()) {
            query.eq("mv.id", params.getGasMeterVersionId());
        }
        if(null!=params.getGasMeterModelId()) {
            query.eq("mm.id", params.getGasMeterModelId());
        }
        if(StringUtils.isNotEmpty(params.getCustomerChargeNo())) {
            query.eq("cg.customer_charge_no", params.getCustomerChargeNo());
        }

        if(!CollectionUtils.isEmpty(params.getOrderSourceName())){
            query.in("mv.order_source_name", params.getOrderSourceName());
        }
        if(ObjectUtil.isNotNull(params.getUseGasTypeId())){
            query.eq("gm.use_gas_type_id", params.getUseGasTypeId());
        }
        if(StringUtils.isNotBlank(params.getAmountMark())){
            query.eq("mv.amount_mark", params.getAmountMark());
        }
        query.ne("gm.data_status", GasMeterStatusEnum.UNVALID.getCode());
        query.orderByDesc("gm.create_time");

        return baseMapper.findGasMeterPageEx(page,query);
    }
    /**
     * 获取调价补差气表分页信息
     * @param params
     * @return
     */
    @Override
    public IPage<PageGasMeter> findAdjustPriceGasMeterPage(GasMeterVo params){
        //分页
        Page<GasMeter> page = new Page<>(params.getPageNo(),params.getPageSize());
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        QueryWrapper<GasMeter> query = Wrappers.query();
        query.notExists("select gapr.gasmeter_code from gt_adjust_price_record gapr where gm.gas_code = gapr.gasmeter_code" +
                " and gapr.check_end_time>='"+params.getCheckStartTime()+
                "' and gapr.check_end_time<='"+params.getCheckEndTime()+
                "' and gapr.check_start_time>='"+params.getCheckStartTime()+
                "' and gapr.check_start_time<='"+params.getCheckEndTime()+"'");
        if(StringUtils.isNotEmpty(params.getGasCode())) {
            query.like("gm.gas_code", params.getGasCode());
        }
        if(StringUtils.isNotEmpty(params.getInstallNumber())) {
            query.like("gm.install_number", params.getInstallNumber());
        }
        if(StringUtils.isNotEmpty(params.getGasMeterNumber())){
            query.like("gm.gas_meter_number",params.getGasMeterNumber());
        }

        if(StringUtils.isNotEmpty(params.getMoreGasMeterAddress())){
            query.like("gm.more_gas_meter_address",params.getMoreGasMeterAddress());
        }

        if(null!=params.getDataStatus() && params.getDataStatus().length>0){
            query.in("gm.data_status",params.getDataStatus());
        }

        if(null!=params.getGasMeterFactoryId()) {
            query.eq("mf.id", params.getGasMeterFactoryId());
        }
        if(null!=params.getGasMeterVersionId()) {
            query.eq("mv.id", params.getGasMeterVersionId());
        }
        if(null!=params.getGasMeterModelId()) {
            query.eq("mm.id", params.getGasMeterModelId());
        }
        if(StringUtils.isNotEmpty(params.getCustomerChargeNo())) {
            query.eq("cg.customer_charge_no", params.getCustomerChargeNo());
        }

        if(!CollectionUtils.isEmpty(params.getOrderSourceName())){
            query.in("mv.order_source_name", params.getOrderSourceName());
        }
        if(ObjectUtil.isNotNull(params.getUseGasTypeId())){
            query.eq("gm.use_gas_type_id", params.getUseGasTypeId());
        }
        if(StringUtils.isNotBlank(params.getAmountMark())){
            query.eq("mv.amount_mark", params.getAmountMark());
        }

        query.orderByDesc("gm.create_time");

        return baseMapper.findAdjustPriceGasMeterPage(page,query);
    }

    @Override
    public GasMeter findGasMeterByCode(String gascode) {
        LbqWrapper <GasMeter> gasMeterLbqWrapper=new LbqWrapper<>();
        gasMeterLbqWrapper.eq(GasMeter::getGasCode,gascode);
        return baseMapper.selectOne(gasMeterLbqWrapper);
    }

    @Override
    public PageGasMeter findGasMeterBygasCode(String gasMeterCode) {
        return baseMapper.findGasMeterBygasCode(gasMeterCode);
    }

    @Override
    public R<List<GasMeter>> addGasMeterList(List<GasMeter> gasMeterList) {
        /*List<String> gasMeterNumbers = baseMapper.findGasMeterNumber();*/
   /*     List<String> failList=new ArrayList<>();
        List<GasMeter> newList=new ArrayList<>();*/
        List<GasMeter> saveList=new ArrayList<>();
      /*  Set<String> gasMeterNumbersSet=new HashSet<>();*/
        for (GasMeter gasMeter : gasMeterList) {
    /*        if(gasMeterNumbersSet.contains(gasMeter.getGasMeterNumber())){
                failList.add(gasMeter.getGasMeterNumber());
            }else {
                gasMeterNumbersSet.add(gasMeter.getGasMeterNumber());
                newList.add(gasMeter);
            }
        }
        if(failList.size()>0){
            return R.fail(getLangMessage(REPEAT_GAS_METER_NUMBER)+ Arrays.toString(failList.toArray()));
        }*/
      /*  for (GasMeter gasMeter : newList) {*/
                //气表初始化
//                gasMeter.setGasCode(BizCodeUtil.genBizDataDeviceCode(BizBCode.M,BizCodeUtil.ARCHIVE_METER,null));
                if(ObjectUtil.isNull(gasMeter.getDataStatus())) {
                    gasMeter.setDataStatus(GasMeterStatusEnum.Pre_doc.getCode());
                }
                gasMeter.setVentilateStatus(GasMeterVentilateStatusEnum.NOT_VENTILATE.getValue());
            saveList.add(gasMeter);
        }
        //int i= baseMapper.insertBatchSomeColumn(saveList);
        boolean isSuccess = saveBatch(saveList);
        if(isSuccess){
            return R.success(saveList);
        } else {
            return R.fail("新增失败");
        }
    }


    @Override
    public GasMeter findGasMeterByGasMeterNumber(String gasMeterNumber) {
        LbqWrapper <GasMeter> gasMeterLbqWrapper=new LbqWrapper<>();
        gasMeterLbqWrapper.eq(GasMeter::getGasMeterNumber,gasMeterNumber);
        return baseMapper.selectOne(gasMeterLbqWrapper);
    }

    @Override
    public GasMeter getMeterByBodyNoFactory(GasMeter gasMeter) {
        //表具
        LbqWrapper<GasMeter> wrapper = Wraps.lbQ();
        wrapper.eq(GasMeter::getGasMeterNumber, gasMeter.getGasMeterNumber())
                .eq(GasMeter::getGasMeterFactoryId, gasMeter.getGasMeterFactoryId())
                .eq(GasMeter::getGasMeterVersionId, gasMeter.getGasMeterVersionId())
                .eq(GasMeter::getGasMeterModelId, gasMeter.getGasMeterModelId());
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public Integer findGasMeterNumber(FactoryAndVersion factoryAndVersion) {
        return baseMapper.findGasMeterNumber(factoryAndVersion);
    }

    @Override
    public R<Boolean> updateByCode(GasMeter gasMeter) {
        LbuWrapper<GasMeter> lambdaUpdate =
                Wraps.<GasMeter>lbU()
                        .eq(GasMeter::getGasCode, gasMeter.getGasCode());
        return R.success(this.update(gasMeter, lambdaUpdate));
    }

    @Override
    public R<GasMeter> ventilation(GasMeter gasMeter){
        GasMeter gasMeterParam = new GasMeter();
        if(gasMeter.getVentilateStatus().equals(GasMeterVentilateStatusEnum.VENTILATE.getValue())){
            Boolean flag = super.count(Wraps.<GasMeter>lbQ().eq(GasMeter::getGasCode,gasMeter.getGasCode()).
                    eq(GasMeter::getDataStatus,GasMeterStatusEnum.UnVentilation.getCode())) > 0;
            if(flag){
                gasMeterParam.setDataStatus(GasMeterStatusEnum.Ventilated.getCode());
                gasMeterParam.setVentilateStatus(GasMeterVentilateStatusEnum.VENTILATE.getValue());
                gasMeterParam.setVentilateTime(gasMeter.getVentilateTime());
                gasMeterParam.setVentilateUserId(gasMeter.getVentilateUserId());
                gasMeterParam.setVentilateUserName(gasMeter.getVentilateUserName());
            }
            else{
                log.error(i18nUtil.getMessage(MessageConstants.ONLY_WHEN_GAS_METER_IN_VENTILATION_CAN_VENTILATE));
                throw new BizException(i18nUtil.getMessage(MessageConstants.ONLY_WHEN_GAS_METER_IN_VENTILATION_CAN_VENTILATE));
            }
        }else{
            gasMeterParam.setDataStatus(GasMeterStatusEnum.UnVentilation.getCode());
            gasMeterParam.setVentilateStatus(GasMeterVentilateStatusEnum.NOT_VENTILATE.getValue());
        }
        LbuWrapper<GasMeter> lambdaUpdate =
                Wraps.<GasMeter>lbU()
                        .eq(GasMeter::getGasCode, gasMeter.getGasCode());
        this.update(gasMeterParam, lambdaUpdate);
        return R.success(gasMeter);
    }

    @Override
    public Boolean check(GasMeterUpdateDTO gasMeterUpdateDTO) {
        if(!StringUtils.isEmpty(gasMeterUpdateDTO.getGasMeterNumber())){
            return super.count(Wraps.<GasMeter>lbQ().eq(GasMeter::getGasMeterNumber,gasMeterUpdateDTO.getGasMeterNumber()).
                    ne(GasMeter::getId,gasMeterUpdateDTO.getId()).
                    notIn(GasMeter::getDataStatus,5,6)
            )>0;
        }
        return false;
    }
    @Override
    public Boolean checkByGasCode(GasMeterUpdateDTO gasMeterUpdateDTO) {
        return super.count(Wraps.<GasMeter>lbQ().eq(GasMeter::getGasCode,gasMeterUpdateDTO.getGasCode()))>0;
    }

    @Override
    public R<List<GasMeter>> queryMeterList(List<String> meterNoList) {
        return R.success(baseMapper.selectList(Wraps.<GasMeter>lbQ().in(GasMeter::getGasMeterNumber,meterNoList)));
    }

    @Override
    public HashMap<String,Object> findGasInfo(String gascode) {
        return baseMapper.findGasInfo(gascode);
    }

    @Override
    public GasMeter findMeterByNumber(String number) {
        return this.baseMapper.findMeterByNumber(number);
    }

    @Override
    public GasMeterExVo findGasMeterInfoByCodeEx(String gasMeterCode) {

        return this.baseMapper.findGasMeterInfoByCodeEx(gasMeterCode);
    }

    /**
     * 通过表身号查询有效状态的表具
     *
     * @param gasMeterNumber 表身号
     * @return 表具信息
     */
    @Override
    public GasMeter findEffectiveMeterByNumber(String gasMeterNumber) {
        return baseMapper.findEffectiveMeterByNumber(gasMeterNumber);
    }

    @Override
    public List<PageGasMeter> findGasMeterListBygasCode(String customerCode) {
        return baseMapper.findGasMeterListBygasCode(customerCode);
    }

    /**
     * 拆除表具时清空档案开户时填写的数据
     *
     * @param updateDTO 表具信息
     * @return 清空结果
     */
    @Override
    public Boolean clearMeter(GasMeterUpdateDTO updateDTO) {
        Integer result = baseMapper.clearMeter(updateDTO.getId());
        return result > 0;
    }

    @Override
    public GasMeter findOnLineMeterByNumber(String number) {
        return this.baseMapper.findOnLineMeterByNumber(number);
    }

    @Override
    public Long sendCardNum(StsSearchParam stsSearchParam) {
        return this.baseMapper.sendCardNum(stsSearchParam);
    }

    @Override
    public List<StsInfoBaseVo<String, Long>> stsGasMeterType(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsGasMeterType(stsSearchParam, GasMeterStatusEnum.UNVALID.getCode(), dataScope);
    }

    @Override
    public List<StsInfoBaseVo<String, Long>> stsFactory(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsFactory(stsSearchParam, GasMeterStatusEnum.UNVALID.getCode(), dataScope);
    }

    @Override
    public List<StsInfoBaseVo<Integer, Long>> stsStatus(StsSearchParam stsSearchParam) {
        String dataScope = UserOrgIdUtil.getUserDataScopeStr();
        return this.baseMapper.stsStatus(stsSearchParam, GasMeterStatusEnum.UNVALID.getCode(), dataScope);
    }

    @Override
    public Boolean isExistsBodyNumber(Long fachtoryId, String bodyNumber) {
        return super.count(Wraps.<GasMeter>lbQ()
                .eq(GasMeter::getGasMeterFactoryId,fachtoryId)
                .eq(GasMeter::getGasMeterNumber, bodyNumber).ne(GasMeter::getDataStatus, GasMeterStatusEnum.UNVALID.getCode()))>0;
    }
}
