package com.cdqckj.gmis.invoice;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.invoice.dto.ReceiptInvoiceAssociationPageDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptInvoiceAssociationSaveDTO;
import com.cdqckj.gmis.invoice.dto.ReceiptInvoiceAssociationUpdateDTO;
import com.cdqckj.gmis.invoice.entity.ReceiptInvoiceAssociation;
import com.cdqckj.gmis.invoice.hystrix.ReceiptInvoiceAssociationBizApiFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "${gmis.feign.gmis-invoice-server:gmis-invoice-server}", fallback = ReceiptInvoiceAssociationBizApiFallback.class
        , path = "/receiptInvoiceAssociation", qualifier = "ReceiptInvoiceAssociationBizApi")
public interface ReceiptInvoiceAssociationBizApi {

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    public R<ReceiptInvoiceAssociation> save(@RequestBody @Valid ReceiptInvoiceAssociationSaveDTO saveDTO);

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    public R<ReceiptInvoiceAssociation> update(@RequestBody @Validated(SuperEntity.Update.class) ReceiptInvoiceAssociationUpdateDTO updateDTO);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<ReceiptInvoiceAssociation>> query(@RequestBody ReceiptInvoiceAssociation data);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public R<IPage<ReceiptInvoiceAssociation>> page(@RequestBody @Validated PageParams<ReceiptInvoiceAssociationPageDTO> params);
}
