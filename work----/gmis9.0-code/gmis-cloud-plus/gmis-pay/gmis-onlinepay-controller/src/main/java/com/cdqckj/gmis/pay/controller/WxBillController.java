package com.cdqckj.gmis.pay.controller;

import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.pay.entity.WxBill;
import com.cdqckj.gmis.pay.dto.WxBillSaveDTO;
import com.cdqckj.gmis.pay.dto.WxBillUpdateDTO;
import com.cdqckj.gmis.pay.dto.WxBillPageDTO;
import com.cdqckj.gmis.pay.enumeration.BillReturnCodeEnum;
import com.cdqckj.gmis.pay.enumeration.BusinessTypeEnum;
import com.cdqckj.gmis.pay.enumeration.ContrastStateEnum;
import com.cdqckj.gmis.pay.enumeration.TradeTypeEnum;
import com.cdqckj.gmis.pay.service.WxBillService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


/**
 * <p>
 * 前端控制器
 * 微信交易账单
 * </p>
 *
 * @author gmis
 * @date 2021-01-11
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/wxBill")
@Api(value = "WxBill", tags = "微信交易账单")
@PreAuth(replace = "wxBill:")
public class WxBillController extends SuperController<WxBillService, Long, WxBill, WxBillPageDTO, WxBillSaveDTO, WxBillUpdateDTO> {

    @ApiOperation(value = "导出微信账单")
    @PostMapping("/exportBill")
    public void exportBill(@RequestBody @Validated PageParams<WxBillPageDTO> params, HttpServletResponse response) {
        try {
            WxBillPageDTO model = params.getModel();
            LbqWrapper<WxBill> wraps = Wraps.<WxBill>lbQ().orderByDesc(WxBill::getTransactionTime);
            if(null!=model.getContrastState()){
                wraps.eq(WxBill::getContrastState,model.getContrastState());
            }
            if(null!=model.getStartTime()){
                wraps.ge(WxBill::getTransactionDate,model.getStartTime());
            }
            if(null!=model.getEndTime()){
                wraps.le(WxBill::getTransactionDate,model.getEndTime());
            }
            if(StringUtils.isNotBlank(model.getCustomerName())){
                wraps.like(WxBill::getCustomerName,model.getCustomerName());
            }
            if(StringUtils.isNotBlank(model.getOrderNumber())){
                wraps.like(WxBill::getOrderNumber,model.getOrderNumber());
            }
            List<WxBill> dataList = baseService.page(params.getPage(),wraps).getRecords();
            dataList.forEach(e ->{
                e.setTradeTypeName(TradeTypeEnum.getDescByType(e.getTradeType()));
                e.setTradeStatename(BillReturnCodeEnum.getDescByType(e.getTradeState()));
                e.setRefundTypeName(BillReturnCodeEnum.getDescByType(e.getRefundType()));
                e.setRefundStateName(BillReturnCodeEnum.getDescByType(e.getRefundState()));
                e.setContrastStateName(ContrastStateEnum.getDesc(e.getContrastState()));
            });
            exportExcel(response, null, dataList, getaClass());
        } catch (Exception e) {
            e.printStackTrace();
        }
    };
}
