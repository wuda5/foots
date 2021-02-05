package com.cdqckj.gmis.invoice.controller;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.QueryWrap;
import com.cdqckj.gmis.invoice.dto.ReceiptPageDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptSaveDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptUpdateDTO;
import com.cdqckj.gmis.invoice.entity.Receipt;
import com.cdqckj.gmis.invoice.service.ReceiptService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 
 * </p>
 *
 * @author songyz
 * @date 2020-09-04
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/receipt")
@Api(value = "Receipt", tags = "票据记录")
@PreAuth(replace = "receipt:")
public class ReceiptController extends SuperController<ReceiptService, Long, Receipt, ReceiptPageDTO, ReceiptSaveDTO, ReceiptUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<Receipt> receiptList = list.stream().map((map) -> {
            Receipt receipt = Receipt.builder().build();
            //TODO 请在这里完成转换
            return receipt;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(receiptList));
    }

    @ApiOperation(value = "查询一段时间内的票据")
    @PostMapping("/queryReceipt")
    R<List<Receipt>> queryReceipt(@RequestParam(value = "startTime") LocalDateTime startTime,
                                  @RequestParam(value = "endTime") LocalDateTime endTime){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        QueryWrapper<Receipt> wrapper = new QueryWrapper<>();
        wrapper.lambda()
                .and(dateSql -> dateSql.ge(Receipt::getCreateTime, startTime.format(dateTimeFormatter))
                        .le(Receipt::getCreateTime, endTime.format(dateTimeFormatter)));
        return success(getBaseService().list(wrapper));
    }

    @Override
    public void query(PageParams<ReceiptPageDTO> params, IPage<Receipt> page, Long defSize) {
        handlerQueryParams(params);

        if (defSize != null) {
            page.setSize(defSize);
        }

        QueryWrap<Receipt> wrapper = Wraps.q();

        handlerWrapper(wrapper, params);
        baseService.findPage(params, page);

        // 处理结果
        handlerResult(page);
    }
}
