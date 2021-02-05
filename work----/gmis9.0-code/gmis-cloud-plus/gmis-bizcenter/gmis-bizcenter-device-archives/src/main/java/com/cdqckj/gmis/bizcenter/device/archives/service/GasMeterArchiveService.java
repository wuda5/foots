package com.cdqckj.gmis.bizcenter.device.archives.service;

import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.devicearchive.dto.GasMeterConfDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterPageDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.InputOutputMeterStoryVo;
import com.cdqckj.gmis.devicearchive.entity.CustomerGasMeterRelated;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.userarchive.entity.Customer;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface GasMeterArchiveService extends SuperCenterService {
    R<List<GasMeter>> addGasMeterList(List<GasMeter> gasMeterList);
    void export(@RequestBody @Validated PageParams<GasMeterPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws Exception;
    R<List<InputOutputMeterStoryVo>> importExcelDiy(@RequestParam(value = "file") MultipartFile simpleFile  ) throws Exception;

    R<Customer> findCustomer(CustomerGasMeterRelated customerGasMeterRelated);


    R<GasMeter> update(GasMeterUpdateDTO updateDTO);

    GasMeterConfDTO gasMeterConfDTOByCode(String gasMeterCode);

    /**
     * 拆除的设备重新加入网关
     *
     * @param gasMeterList
     * @return
     */
    IotR addDeviceAgain(List<GasMeter> gasMeterList);
}
