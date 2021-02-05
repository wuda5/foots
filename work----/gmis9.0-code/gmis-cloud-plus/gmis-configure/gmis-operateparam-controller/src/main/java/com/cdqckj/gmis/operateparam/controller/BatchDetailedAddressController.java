package com.cdqckj.gmis.operateparam.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressPageDTO;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressSaveDTO;
import com.cdqckj.gmis.operateparam.dto.BatchDetailedAddressUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.BatchDetailedAddress;
import com.cdqckj.gmis.operateparam.service.BatchDetailedAddressService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author 王华侨
 * @date 2020-09-17
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/batchdetailedAddress")
@Api(value = "DetailedAddress", tags = "")
@PreAuth(replace = "detailedAddress:")
public class BatchDetailedAddressController extends SuperController<BatchDetailedAddressService, Long, BatchDetailedAddress, BatchDetailedAddressPageDTO, BatchDetailedAddressSaveDTO, BatchDetailedAddressUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<BatchDetailedAddress> detailedAddressList = list.stream().map((map) -> {
            BatchDetailedAddress detailedAddress = BatchDetailedAddress.builder().build();
            //TODO 请在这里完成转换
            return detailedAddress;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(detailedAddressList));
    }
    @PostMapping(value = "/check")
    R<Boolean> check(@RequestBody  BatchDetailedAddressUpdateDTO detailedAddressUpdateDTO){
       return success(baseService.check(detailedAddressUpdateDTO));
    }
    @Override
    public void query(PageParams<BatchDetailedAddressPageDTO> params, IPage<BatchDetailedAddress> page, Long defSize){
        baseService.findPage(page, params.getModel());
    }
}
