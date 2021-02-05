package com.cdqckj.gmis.bizcenter.customer.archives.service.impl;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import com.cdqckj.gmis.archive.BatchGasBizApi;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.CustomerBlacklistBizApi;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterServiceImpl;
import com.cdqckj.gmis.base.utils.ExcelSelectortDTO;
import com.cdqckj.gmis.bizcenter.customer.archives.service.UserArchiveService;
import com.cdqckj.gmis.charges.api.CustomerAccountBizApi;
import com.cdqckj.gmis.charges.dto.CustomerAccountSaveDTO;
import com.cdqckj.gmis.charges.enums.AccountStateEnum;
import com.cdqckj.gmis.common.enums.BizBCode;
import com.cdqckj.gmis.common.enums.BizCCode;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.BizCodeUtil;
import com.cdqckj.gmis.common.utils.ExportUtil;
import com.cdqckj.gmis.devicearchive.dto.BatchGasSaveDTO;
import com.cdqckj.gmis.operateparam.CommunityBizApi;
import com.cdqckj.gmis.operateparam.StreetBizApi;
import com.cdqckj.gmis.operateparam.entity.Community;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.userarchive.dto.*;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.entity.CustomerBlacklist;
import com.cdqckj.gmis.userarchive.enumeration.BlackStatusEnum;
import com.cdqckj.gmis.utils.I18nUtil;
import feign.Response;
import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.cdqckj.gmis.base.MessageConstants.IMPORT_DATA;

@Service
public class UserArchiveServiceImpl extends SuperCenterServiceImpl implements UserArchiveService {
    @Autowired
    CustomerBizApi customerBizApi;

    @Autowired
    CustomerAccountBizApi customerAccountBizApi;

    @Autowired
    private StreetBizApi streetBizApi;
    @Autowired
    private CommunityBizApi communityBizApi;
    @Autowired
    private BatchGasBizApi batchGasBizApi;
    @Autowired
    private CommonConfigurationApi commonConfigurationApi;
    @Autowired
    private I18nUtil i18nUtil;
    @Autowired
    private CustomerBlacklistBizApi customerBlacklistBizApi;
    @Autowired
    private GasMeterBookRecordApi recordApi;
    @Autowired
    private ReadMeterDataApi dataApi;

    @Override
    public R<Boolean> batchgas(BatchGasSaveDTO batchGasSaveDTO) {
        //楼层
        Integer storey =batchGasSaveDTO.getStorey();
        //户数
        Integer households =batchGasSaveDTO.getHouseholds();
        Street street = streetBizApi.get(Long.valueOf(batchGasSaveDTO.getStreetId())).getData();
        String streetName=street.getStreetName();
        String provinceName = street.getProvinceName();
        String cityName = street.getCityName();
        String areaName = street.getAreaName();
        Community community = communityBizApi.get(Long.valueOf(batchGasSaveDTO.getCommunityId())).getData();
        String communityName=community.getCommunityName();
        StringBuilder address = new StringBuilder();
        BatchGasSaveDTO temp;
        List<BatchGasSaveDTO> saveDTOS= Lists.newArrayList();
        for (int i = 1; i <=storey ; i++) {//楼层
            for (int j = 1; j <=households; j++) {//户数
                address=address.append(provinceName).append(cityName).append(areaName).append(streetName).append(communityName);
                temp=new BatchGasSaveDTO();
                address=address.append(batchGasSaveDTO.getBuildings()).append("栋").append(batchGasSaveDTO.getUnit()).append("单元第").append(storey).append("层").append(households).append("号");
                BeanUtils.copyProperties(batchGasSaveDTO,temp);
                temp.setAddress(address.toString());
                saveDTOS.add(temp);
                if(saveDTOS.size()==100){
                    //调用批量存储方法
                    batchGasBizApi.saveList(saveDTOS);

                    saveDTOS.clear();//0
                }
            }
        }
        //keyneng 10
        if(saveDTOS.size()>0){
            //调用批量存储方法
            batchGasBizApi.saveList(saveDTOS);
        }
        return R.success(true);
    }

    @Override
  public R<Customer> save(CustomerSaveDTO customerSaveDTO) {
        List<String> idNumber = customerBizApi.findIdNumber().getData();
        if(idNumber.contains(customerSaveDTO.getIdNumber())){
            return R.fail(i18nUtil.getMessage("idnumber.not.repeat"));
        }
        //customerSaveDTO.setCustomerCode(BizCodeUtil.genBizDataCode(BizBCode.C,BizCodeUtil.ARCHIVE_CUSTOMER));
        customerSaveDTO.setCustomerCode(BizCodeNewUtil.getCustomCode());
        return customerBizApi.save(customerSaveDTO);
    }
    @Override
    public R<Customer> importCustomerData(MultipartFile file) throws Exception{
        List<CustomerSaveDTO> customerSaveList=new ArrayList<>();
        Map<String,String> dictionaryItemMap=new HashMap<>();
        List<DictionaryItem> dictionaryItems = commonConfigurationApi.query(new DictionaryItem()).getData();
        for (DictionaryItem dictionaryItem : dictionaryItems) {
            dictionaryItemMap.put(dictionaryItem.getName(),dictionaryItem.getCode());
        }
        List<Customer> customers = customerBizApi.importCustomerData(file).getData();
        if(customers ==null || customers.size()==0){
            return R.fail(getLangMessage(IMPORT_DATA));
        }
        for (Customer customer : customers) {
            String customerTypeName = dictionaryItemMap.get(customer.getCustomerTypeName());
            //idTypeCode  这里没设置进来
            String sexcode = dictionaryItemMap.get(customer.getSex());
            customer.setSex(sexcode);
            String idTypeCode = dictionaryItemMap.get(customer.getIdTypeName());
            customer.setCustomerTypeCode(customerTypeName);
            customer.setIdTypeCode(idTypeCode);
            CustomerSaveDTO customerSaveDTO=new CustomerSaveDTO();
            BeanUtils.copyProperties(customer,customerSaveDTO);
            customerSaveList.add(customerSaveDTO);
        }
        R<List<Customer>> r= customerBizApi.saveBatch(customerSaveList);
        return R.success(createCustomerAccount(r));
    }

    private Customer createCustomerAccount(R<List<Customer>> r){
        Customer customer=null;

        if(r.getData()!=null){
//            List<CustomerAccountSaveDTO> saveDTOS=new ArrayList<>();
            for (Customer cust : r.getData()) {
                customer=cust;
//                saveDTOS.add(CustomerAccountSaveDTO
//                        .builder()
////                        .accountCode(BizCodeUtil.genAccountDataCode(BizCCode.T, BizCodeUtil.ACCOUNT_NO))
//                        .accountCode(BizCodeNewUtil.genAccountDataCode())
//                        .customerCode(customer.getCustomerCode())
//                        .customerName(customer.getCustomerName())
//                        .accountMoney(BigDecimal.ZERO)
//                        .giveMoney(BigDecimal.ZERO)
//                        .status(AccountStateEnum.ACTIVE.getCode())
//                        .build()) ;
            }
//            if(saveDTOS.size()>0){
//                customerAccountBizApi.saveList(saveDTOS);
//            }
        }
        return customer;
    }

    @Override
    public void export(PageParams<CustomerPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        // feign文件下载
        List<String> userType=new ArrayList<>();
        List<String> sexList=new ArrayList<>();
        List<String> certificateList=new ArrayList<>();
        List<ExcelSelectortDTO> comboboxList = new ArrayList<>();
        List<DictionaryItem> user_type = commonConfigurationApi.findCommonConfigbytype("USER_TYPE").getData();
        for (DictionaryItem dictionaryItem : user_type) {
            userType.add(dictionaryItem.getName());
        }
        ExcelSelectortDTO selectortDTO = new ExcelSelectortDTO(1,1,userType.toArray(new String[(userType.size())]));
        List<DictionaryItem> sex = commonConfigurationApi.findCommonConfigbytype("SEX").getData();
        for (DictionaryItem dictionaryItem : sex) {
            sexList.add(dictionaryItem.getName());
        }
        ExcelSelectortDTO selectortSEXDTO = new ExcelSelectortDTO(2,2,sexList.toArray(new String[(sexList.size())]));
      /*  List<DictionaryItem> certificateType = commonConfigurationApi.findCommonConfigbytype("CERTIFICATE_TYPE").getData();
        for (DictionaryItem dictionaryItem : certificateType) {
            certificateList.add(dictionaryItem.getName());
        }
        ExcelSelectortDTO selectortIcDTO = new ExcelSelectortDTO(5,5,certificateList.toArray(new String[(certificateList.size())]));*/
        comboboxList.add(selectortDTO);
        comboboxList.add(selectortSEXDTO);
     /*   comboboxList.add(selectortIcDTO);*/
        params.setComboboxList(comboboxList);
        Response response = customerBizApi.exportCombobox(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, "临时文件");
        ExportUtil.exportExcel(response,httpResponse,fileName);
    }

    @Override
    public R<Customer> update(CustomerUpdateDTO updateDTO) {
        R<Boolean> check = customerBizApi.check(updateDTO);
        if(! check.getData()){
            GasMeterBookRecordUpdateDTO recordUpdateDTO = new GasMeterBookRecordUpdateDTO();
            recordUpdateDTO.setCustomerCode(updateDTO.getCustomerCode()).setCustomerName(updateDTO.getCustomerName());
            Boolean bool = recordApi.updateByCustomer(recordUpdateDTO).getData();
            ReadMeterDataUpdateDTO data = new ReadMeterDataUpdateDTO();
            data.setCustomerCode(updateDTO.getCustomerCode()).setCustomerName(updateDTO.getCustomerName());
            dataApi.updateDataByCustomer(data).getData();
            return customerBizApi.update(updateDTO);
        }
        return R.fail(i18nUtil.getMessage("idnumber.not.repeat"));
    }

    @Override
    public R<Customer> saveList(List<CustomerSaveDTO> customerSaveDTOs) {
        List<CustomerSaveDTO> saveList =new ArrayList<>();
        for (CustomerSaveDTO customerSaveDTO : customerSaveDTOs) {
            R<Boolean> check = customerBizApi.checkAdd(customerSaveDTO);
          if(check.getData()){
              return R.fail("身份证号码重复");
          }
          saveList.add(customerSaveDTO);
        }
        R<List<Customer>> r= customerBizApi.saveBatch(saveList);
        return R.success(createCustomerAccount(r));
    }

    @Override
    public R<Boolean> setBlacklist(List<CustomerUpdateDTO> list) {
        CustomerBlacklistSaveDTO customerBlacklistSaveDTO=null;
        List<CustomerBlacklistSaveDTO> customerBlacklistSaveDTOS=new ArrayList<>();
        List<CustomerBlacklistUpdateDTO> customerBlacklistUpdateDTOS=new ArrayList<>();
        CustomerBlacklistUpdateDTO customerBlacklistUpdateDTO=null;
        for (CustomerUpdateDTO customerUpdateDTO : list) {
            R<CustomerBlacklist> blacklistStatus = customerBlacklistBizApi.findBlacklistStatus(customerUpdateDTO.getCustomerCode());
          if(blacklistStatus.getData()==null){
              customerBlacklistSaveDTO=new CustomerBlacklistSaveDTO();
              customerBlacklistSaveDTO.setCustomerCode(customerUpdateDTO.getCustomerCode());
              customerBlacklistSaveDTO.setCustomerName(customerUpdateDTO.getCustomerName());
              customerBlacklistSaveDTO.setStatus(BlackStatusEnum.Set_Blacklist.getCode());
              customerBlacklistSaveDTOS.add(customerBlacklistSaveDTO);
          }else {
              customerBlacklistUpdateDTO =new CustomerBlacklistUpdateDTO();
              customerBlacklistUpdateDTO.setStatus(BlackStatusEnum.Set_Blacklist.getCode());
              customerBlacklistUpdateDTO.setId(blacklistStatus.getData().getId());
              customerBlacklistUpdateDTOS.add(customerBlacklistUpdateDTO);
              customerBlacklistUpdateDTOS.add(customerBlacklistUpdateDTO);
          }
        }
        if(customerBlacklistSaveDTOS.size()>0){
            Boolean data = customerBizApi.updateBatchById(list).getData();
            if(data==true){
                customerBlacklistBizApi.save(customerBlacklistSaveDTOS);
                return R.success(true);
            }
           return R.success(false);

        }
            Boolean data = customerBizApi.updateBatchById(list).getData();
        if(data==true){
            customerBlacklistBizApi.update(customerBlacklistUpdateDTOS);
            return R.success(true);
        }
        return R.success(false);

    }

    @Override
    public R<Boolean> removeBlacklist(List<CustomerUpdateDTO> list) {
        List<CustomerBlacklistUpdateDTO> customerBlacklistUpdateDTOS=new ArrayList<>();
        CustomerBlacklistUpdateDTO customerBlacklistUpdateDTO=null;
        for (CustomerUpdateDTO customerUpdateDTO : list) {
            CustomerBlacklist data = customerBlacklistBizApi.findBlacklistStatus(customerUpdateDTO.getCustomerCode()).getData();
            customerBlacklistUpdateDTO=new CustomerBlacklistUpdateDTO();
            customerBlacklistUpdateDTO.setStatus(BlackStatusEnum.Remove_Blacklist.getCode());
            customerBlacklistUpdateDTO.setId(data.getId());
            customerBlacklistUpdateDTO.setCustomerCode(data.getCustomerCode());
            customerBlacklistUpdateDTO.setCustomerName(data.getCustomerName());
            customerBlacklistUpdateDTOS.add(customerBlacklistUpdateDTO);
        }
        Boolean data = customerBizApi.updateBatchById(list).getData();
        if(data==true){
            customerBlacklistBizApi.update(customerBlacklistUpdateDTOS);
            return R.success(true);
        }
        return R.success(false);
    }
}