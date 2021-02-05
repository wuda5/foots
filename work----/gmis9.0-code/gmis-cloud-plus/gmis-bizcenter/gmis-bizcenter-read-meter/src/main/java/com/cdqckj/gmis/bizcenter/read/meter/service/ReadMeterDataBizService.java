package com.cdqckj.gmis.bizcenter.read.meter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataErrorSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataPageDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.userarchive.dto.CustomerGasDto;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ReadMeterDataBizService extends SuperCenterService {
    /**
     * 导入excel
     * @param simpleFile
     * @return
     */
    R<List<ReadMeterDataErrorSaveDTO>> importExcel(MultipartFile simpleFile,String readTime);

    /**
     * 获取抄表数据
     * @param params
     * @return
     */
    R<Page<ReadMeterData>> pageReadMeterData(@RequestBody PageParams<ReadMeterDataPageDTO> params);

    R<List<ReadMeterDataErrorSaveDTO>> importExcelByTime(@RequestPart(value = "file") MultipartFile simpleFile, @RequestParam(value = "readTime") String readTime) throws IOException;

    R<Page<ReadMeterData>> page(@RequestBody PageParams<ReadMeterDataPageDTO> params);

    /**
     * 拆表，换表时旧表费用结算
     * @param updateDTO
     * @return
     */
    public R<ReadMeterData> costSettlement(@RequestBody ReadMeterData updateDTO);

    /**
     * 判断表是否可以过户
     * false-有未结算数据，不能开户，true-可以开户
     * @param gasMeter
     * @return
     */
    R<Boolean> isFinished(@RequestBody CustomerGasDto gasMeter);
}