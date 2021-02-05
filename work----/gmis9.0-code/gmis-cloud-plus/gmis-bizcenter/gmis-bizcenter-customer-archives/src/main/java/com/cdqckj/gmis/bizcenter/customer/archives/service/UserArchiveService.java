package com.cdqckj.gmis.bizcenter.customer.archives.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.devicearchive.dto.BatchGasSaveDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerPageDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerSaveDTO;
import com.cdqckj.gmis.userarchive.dto.CustomerUpdateDTO;
import com.cdqckj.gmis.userarchive.entity.Customer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

public interface UserArchiveService extends SuperCenterService {
    R<Boolean> batchgas(@RequestBody BatchGasSaveDTO batchGasSaveDTO);

    R<Customer> save(@RequestBody  CustomerSaveDTO customerSaveDTO);

    R<Customer> importCustomerData(MultipartFile file) throws Exception;

    void export(@RequestBody @Validated PageParams<CustomerPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException;

    R<Customer> update(CustomerUpdateDTO updateDTO);

    R<Customer> saveList(List<CustomerSaveDTO> customerSaveDTOs);

    R<Boolean> setBlacklist(List<CustomerUpdateDTO> list);

    R<Boolean> removeBlacklist(List<CustomerUpdateDTO> list);
}
