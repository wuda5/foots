package com.cdqckj.gmis.bizcenter.read.meter.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.readmeter.ReadMeterBankApi;
import com.cdqckj.gmis.readmeter.dto.BankWithholdRecordSaveDTO;
import com.cdqckj.gmis.readmeter.entity.BankWithholdRecord;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 银行代扣导入前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readmeter/bank")
@Api(value = "bank", tags = "银行代扣导入")
//@PreAuth(replace = "bankWithholdRecord:")
public class ReadMeterBankController {

    @Autowired
    public ReadMeterBankApi readMeterBankApi;

    @ApiOperation(value = "银行代扣导入")
    @PostMapping("/bankWithholdRecord/import")
    public R<BankWithholdRecord> importExcel(@RequestParam(value = "file") MultipartFile simpleFile, HttpServletRequest request,
                                             HttpServletResponse response) throws Exception {
        ImportParams params = new ImportParams();

        params.setTitleRows(StrUtil.isEmpty(request.getParameter("titleRows")) ? 0 : Convert.toInt(request.getParameter("titleRows")));
        params.setHeadRows(StrUtil.isEmpty(request.getParameter("headRows")) ? 1 : Convert.toInt(request.getParameter("headRows")));
        List<Map<String, String>> list = ExcelImportUtil.importExcel(simpleFile.getInputStream(), Map.class, params);
        if (list != null && !list.isEmpty()) {
            List<BankWithholdRecordSaveDTO> bankWithholdRecordList = list.stream().map((map) -> {
                BankWithholdRecordSaveDTO bankWithholdRecord = BankWithholdRecordSaveDTO.builder().build();
                //TODO 请在这里完成转换
                bankWithholdRecord.setCustomerCode(String.valueOf(map.get("客户编号")));
                bankWithholdRecord.setCustomerName(String.valueOf(map.get("客户姓名")));
                bankWithholdRecord.setGasMeterAddress(String.valueOf(map.get("安装地址")));
                bankWithholdRecord.setCardholder(String.valueOf(map.get("持卡人")));
                bankWithholdRecord.setBankAccount(String.valueOf(map.get("银行账号")));
                bankWithholdRecord.setAmount(new BigDecimal(String.valueOf(map.get("金额"))));
                //bankWithholdRecordApi.save(bankWithholdRecord);
                return bankWithholdRecord;
            }).collect(Collectors.toList());
            return readMeterBankApi.saveList(bankWithholdRecordList);
        }
        return R.fail("导入Excel无有效数据！");
    }

}
