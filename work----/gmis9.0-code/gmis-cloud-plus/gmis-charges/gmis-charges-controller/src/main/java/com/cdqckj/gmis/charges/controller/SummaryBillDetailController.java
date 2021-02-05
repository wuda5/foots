package com.cdqckj.gmis.charges.controller;

import com.cdqckj.gmis.charges.entity.SummaryBillDetail;
import com.cdqckj.gmis.charges.dto.SummaryBillDetailSaveDTO;
import com.cdqckj.gmis.charges.dto.SummaryBillDetailUpdateDTO;
import com.cdqckj.gmis.charges.dto.SummaryBillDetailPageDTO;
import com.cdqckj.gmis.charges.service.SummaryBillDetailService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author songyz
 * @date 2020-12-10
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/summaryBillDetail")
@Api(value = "SummaryBillDetail", tags = "")
@PreAuth(replace = "summaryBillDetail:")
public class SummaryBillDetailController extends SuperController<SummaryBillDetailService, Long, SummaryBillDetail, SummaryBillDetailPageDTO, SummaryBillDetailSaveDTO, SummaryBillDetailUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<SummaryBillDetail> summaryBillDetailList = list.stream().map((map) -> {
            SummaryBillDetail summaryBillDetail = SummaryBillDetail.builder().build();
            //TODO 请在这里完成转换
            return summaryBillDetail;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(summaryBillDetailList));
    }
}
