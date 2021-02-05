package com.cdqckj.gmis.invoice;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.invoice.dto.BuyerTaxpayerInfoPageDTO;
import com.cdqckj.gmis.invoice.dto.BuyerTaxpayerInfoSaveDTO;
import com.cdqckj.gmis.invoice.dto.BuyerTaxpayerInfoUpdateDTO;
import com.cdqckj.gmis.invoice.entity.BuyerTaxpayerInfo;
import com.cdqckj.gmis.invoice.hystrix.BuyerTaxpayerInfoBizApiFallback;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;


/**
 * @author houp
 */
@FeignClient(name = "${gmis.feign.gmis-invoice-server:gmis-invoice-server}", fallback = BuyerTaxpayerInfoBizApiFallback.class
        , path = "/buyerTaxpayerInfo", qualifier = "BuyerTaxpayerInfoBizApi")
public interface BuyerTaxpayerInfoBizApi {

    /**
     * 新增
     *
     * @param saveDTO 保存参数
     * @return 实体
     */
    @ApiOperation(value = "新增")
    @PostMapping
    public R<BuyerTaxpayerInfo> save(@RequestBody @Valid BuyerTaxpayerInfoSaveDTO saveDTO);

    /**
     * 修改
     *
     * @param updateDTO
     * @return
     */
    @ApiOperation(value = "修改")
    @PutMapping
    public R<BuyerTaxpayerInfo> update(@RequestBody @Validated(SuperEntity.Update.class) BuyerTaxpayerInfoUpdateDTO updateDTO);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<BuyerTaxpayerInfo>> query(@RequestBody BuyerTaxpayerInfo data);

    /**
     * 分页查询
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页列表查询")
    @PostMapping(value = "/page")
    public R<Page<BuyerTaxpayerInfo>> page(@RequestBody @Validated PageParams<BuyerTaxpayerInfoPageDTO> params);


    /**
     * 纳税人识别号、购买方名称、购买方开户行条件精确查询
     *
     * @param data 条件精确查询
     * @return 查询结果
     */
    @ApiOperation(value = "条件精确查询", notes = "条件精确查询")
    @PostMapping("/queryBuyerTaxpayerInfo")
    public R<BuyerTaxpayerInfo> queryBuyerTaxpayerInfo(@RequestBody BuyerTaxpayerInfo data);
}
