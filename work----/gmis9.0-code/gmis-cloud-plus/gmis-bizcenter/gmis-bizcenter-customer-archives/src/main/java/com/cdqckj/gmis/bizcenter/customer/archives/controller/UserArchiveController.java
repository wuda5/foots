package com.cdqckj.gmis.bizcenter.customer.archives.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.archive.CustomerGasMeterRelatedBizApi;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.cache.repository.RedisService;
import com.cdqckj.gmis.common.domain.excell.*;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.customer.archives.service.UserArchiveService;
import com.cdqckj.gmis.devicearchive.dto.BatchGasSaveDTO;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.vo.PageGasMeter;
import com.cdqckj.gmis.file.api.AttachmentApi;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import com.cdqckj.gmis.userarchive.dto.CustomerPageDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerSaveDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerUpdateDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import com.cdqckj.gmis.userarchive.vo.CustomGasMesterVO;
import com.cdqckj.gmis.userarchive.vo.CustomerMaterUpdateVO;
import com.cdqckj.gmis.userarchive.vo.CustomerPageVo;
import com.cdqckj.gmis.userarchive.vo.CustomerVo;
import io.seata.spring.annotation.GlobalTransactional;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.omg.CORBA.Object;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Slf4j
@Validated
@RestController
@RequestMapping("/archive/userarchive")
@Api(value = "userarchive", tags = "客户档案")
public class UserArchiveController {

    @Autowired
    private GmisUploadFile gmisUploadFile;

    @Autowired
    private CustomerBizApi customerBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private CustomerGasMeterRelatedBizApi customerGasMeterRelatedBizApi;
    @Autowired
    private UserArchiveService userArchiveService;
    @Autowired
    private AttachmentApi attachmentApi;

    @Autowired
    RedisService redisService;

    @Autowired
    private CommonConfigurationApi commonConfigurationApi;

    public static boolean isSpecialChar(String str) {
        String regEx = "[ _`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }
    public static boolean isIdcard(String str) {
        String regEx = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|" +
                "(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    public static boolean telPhone(String str) {
        String regEx = "^[1](([3][0-9])|([4][5,7,9])|([5][^4,6,9])|([6][6])|([7][3,5,6,7,8])|([8][0-9])|([9][8,9]))[0-9]{8}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    @ApiOperation(value = "批量新增")
    @PostMapping("/customer/saveList")
    R<Customer> saveList(@RequestBody @Valid List<CustomerSaveDTO> customerSaveDTOs){
        CustomerSaveDTO customerSaveDTO = customerSaveDTOs.get(0);
        if(isSpecialChar(customerSaveDTO.getCustomerName())==true){
            return R.fail("不能输入特殊字符");
        }else if(isIdcard(customerSaveDTO.getIdCard())==false){
            return R.fail("输入正确的身份证号码");
        }else if(telPhone(customerSaveDTO.getTelphone())==false) {
            return R.fail("输入正确的电话号码");
        }else {
            return userArchiveService.saveList(customerSaveDTOs);
        }
    }

    @GetMapping("/customer/customerBycode")
    @ApiOperation(value = "根据客户编号查询客户信息")
    R<Customer> customerBycode(@RequestParam("customerCode") String customerCode){
        return customerBizApi.findCustomerByCode(customerCode);
    }
    /**
     * @param updateDTO
     * @return
     */
    @PutMapping(value = "/customer/update")
    @ApiOperation(value = "更新客户")
    public R<Customer> updateCustomer(@RequestBody CustomerUpdateDTO updateDTO){
        if(isSpecialChar(updateDTO.getCustomerName())==true){
            return R.fail("不能输入特殊字符");
        }else if(isIdcard(updateDTO.getIdCard())==false){
            return R.fail("输入正确的身份证号码");
        }else if(telPhone(updateDTO.getTelphone())==false) {
            return R.fail("输入正确的电话号码");
        }else{
            return userArchiveService.update(updateDTO);
        }
    }

    /**
     * @param ids
     * @return
     */
    @DeleteMapping(value = "/customer/delete")
    @ApiOperation(value = "删除客户")
    public R<Boolean> deleteCustomer(@RequestParam("ids[]") List<Long> ids){
        return customerBizApi.delete(ids);
    }

    @DeleteMapping(value = "/customer/logicalDelete")
    @ApiOperation(value = "逻辑删除客户")
    public R<Boolean> logicalDelete(@RequestParam("ids[]") List<Long> ids){
        return customerBizApi.logicalDelete(ids);
    }

    /**
     * @param
     * @return
     */
    @PostMapping(value = "/customer/page")
    @ApiOperation(value = "分页查询客户")
  public R<Page<Customer>> pageCustomer(@RequestBody PageParams<CustomerPageDTO> params){
        return customerBizApi.page(params);
    }
    @PostMapping(value = "/customer/list")
    @ApiOperation(value = "分页查询客户(连表查询)")
    public R<Page<Customer>> pageCustomer(@RequestBody CustomerPageVo customerPageVo){
        return customerBizApi.findCustomerPage(customerPageVo);
    }
    @PostMapping(value = "/customer/import")
    @ApiOperation(value = "批量导入客户档案")
    public R<Map<String,Object>> handlerImport(@RequestPart(value = "file") MultipartFile file, HttpServletResponse response) throws Exception{
        Workbook workBook = new HSSFWorkbook(file.getInputStream());
        ExcelImportValid exCelImportValid = new ExcelImportValid((HSSFWorkbook) workBook,1);
        exCelImportValid.addValidBaseRule(ValidTypeEnum.customerName,"不能输入特殊字符","客户名称不能为空",0);
        exCelImportValid.addValidBaseRule(null,null,"客户类型名称不能为空", 1);
        exCelImportValid.addValidBaseRule(null,null,"性别不能为空",2);
        exCelImportValid.addValidBaseRule(new VaildRuleId(customerBizApi,redisService,"身份证号码不能重复","证件号码不能为空"));
        exCelImportValid.colUniqueRule(3,"身份证唯一");
        exCelImportValid.addValidBaseRule(ValidTypeEnum.REGEX_ID_CARD,"输入正确的身份证号码","证件号码不能为空",3);
        exCelImportValid.addValidBaseRule(null,null,"电话号码不为空",4);
        exCelImportValid.addValidBaseRule(ValidTypeEnum.REGEX_MOBILE,"输入正确的电话号码","电话号码不能为空",4);
        List<CustomerVo> success = exCelImportValid.getCellDataToArray(DataTypeEnum.SUC, CustomerVo.class);
        List<CustomerVo> failData = exCelImportValid.getCellDataToArray(DataTypeEnum.FAIL, CustomerVo.class);
        Map map=new HashMap<>();
        List<CustomerSaveDTO> customerSaveList=new ArrayList<>();
        Map<String,String> dictionaryItemMap=new HashMap<>();
        List<DictionaryItem> dictionaryItems = commonConfigurationApi.query(new DictionaryItem()).getData();
        for (DictionaryItem dictionaryItem : dictionaryItems) {
            dictionaryItemMap.put(dictionaryItem.getName(),dictionaryItem.getCode());
        }
        for (CustomerVo customerVo : success) {
            Customer customer=new Customer();
            String customerTypeName = dictionaryItemMap.get(customerVo.getCustomerTypeName());
                //idTypeCode  这里没设置进来
            String sexcode = dictionaryItemMap.get(customerVo.getSex());
            customer.setSex(sexcode);
            customer.setCustomerTypeCode(customerTypeName);
            customer.setTelphone(customerVo.getTelphone());
            customer.setCustomerTypeName(customerVo.getCustomerTypeName());
            customer.setCustomerName(customerVo.getCustomerName());
            customer.setIdCard(customerVo.getIdCard());
            if(!StringUtils.isEmpty(customerVo.getContactAddress())){
                customer.setContactAddress(customerVo.getContactAddress());
            }
            if(!StringUtils.isEmpty(customerVo.getRemark())){
                customer.setRemark(customerVo.getRemark());
            }
            CustomerSaveDTO customerSaveDTO=new CustomerSaveDTO();
            BeanUtils.copyProperties(customer,customerSaveDTO);
            customerSaveList.add(customerSaveDTO);
            }
            if(customerSaveList.size()>0){
                customerBizApi.saveBatch(customerSaveList);
            }
        exCelImportValid.clearRow(DataTypeEnum.SUC);
        map.put("success",  success.size());
        map.put("failData",  failData);
        map.put("fail",  failData.size());
        exCelImportValid.setCellWrongStyle();
        UploadFileInfo fileInfo = new UploadFileInfo();
        fileInfo.setFileName("客户档案导入错误数据.xls");
        String errUrl = exCelImportValid.uploadFile(fileInfo, gmisUploadFile);
        log.info("失败数据");
        map.put("errUrl", errUrl);
        return R.success(map);
    }
    @ApiOperation(value = "根据客户编号查询气表详情信息")
    @GetMapping("/customer/findGasMeterByCustomerCode")
    public R<List<PageGasMeter>>findGasMeterByCustomerCode(@RequestParam("customerCode") String customerCode){
        return gasMeterBizApi.findGasMeterListBygasCode(customerCode);
    }
    @ApiOperation(value = "设置黑名单")
    @PostMapping ("/customer/SetBlacklist")
    public R<Boolean> setBlacklist(@RequestBody List<CustomerUpdateDTO> list){
       return userArchiveService.setBlacklist(list);
    }

    @ApiOperation(value = "移除黑名单")
    @PostMapping ("/customer/RemoveBlacklist")
    public R<Boolean> removeBlacklist(@RequestBody List<CustomerUpdateDTO> list){
        return userArchiveService.removeBlacklist(list);
    }
    @ApiOperation(value = "编辑前的查询")
    @PostMapping ("/customer/findCustomerAndGasMeter")
    @GlobalTransactional
        /*
         * 客户档案中 气表信息的编辑 分别修改客户信息，和气表信息
         * */
    R<CustomerMaterUpdateVO> findCustomerAndGasMeter(@RequestBody String gascode){
        CustomerMaterUpdateVO customerMaterUpdateVO=new CustomerMaterUpdateVO();
        GasMeter gasMeter = gasMeterBizApi.findGasMeterByCode(gascode).getData();
        CustomerGasMeterRelated customerGasMeterRelated = customerGasMeterRelatedBizApi.findGasCodeByCode(gascode).getData();
        Customer customer= customerBizApi.findCustomerByCode(customerGasMeterRelated.getCustomerCode()).getData();
        customerMaterUpdateVO.setCustomer(customer);
        customerMaterUpdateVO.setGasMeter(gasMeter);
        return R.success(customerMaterUpdateVO);
    }



    @ApiOperation(value = "编辑客户和气表")
    @PostMapping ("/customer/updateCustomerMeter")
    @GlobalTransactional
    /*
    * 客户档案中 气表信息的编辑 分别修改客户信息，和气表信息
    * */
    R<Boolean> updateCustomerMeter(@RequestBody CustomGasMesterVO params) {
        return customerBizApi.updateCustomerAndGasMeter(params);
    }
    @ApiOperation(value = "批量建档")
    @PostMapping("/customer/batchgas")
    R<Boolean> batchgas(@RequestBody BatchGasSaveDTO batchGasSaveDTO){
       return userArchiveService.batchgas(batchGasSaveDTO);
    }

    @ApiOperation(value = "导出客户数据")
    @PostMapping("/customer/exportCustomer")
    public void export(@RequestBody @Validated PageParams<CustomerPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        userArchiveService.export(params,request,httpResponse);
    }

    @PostMapping(value = "/customerGas/pagelist")
    @ApiOperation(value = "日常综合")
    public R<Page<CustomerGasDto>> findCustomerGasMeterPage(@RequestBody CustomerPageVo customerPageVo){
        return customerBizApi.findCustomerGasMeterPage(customerPageVo);
    }

    @PostMapping(value = "/customerGas/pagelistTwo")
    @ApiOperation(value = "临时综合")
    public R<Page<CustomerGasDto>> findCustomerGasMeterPageTwo(@RequestBody CustomerPageVo customerPageVo){
        return customerBizApi.findCustomerGasMeterPageTwo(customerPageVo);
    }
}
