package com.cdqckj.gmis.biztemporary;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.biztemporary.dto.GasTypeChangeRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.GasTypeChangeRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.entity.GasTypeChangeRecord;
import com.cdqckj.gmis.biztemporary.vo.GasTypeChangeRecordVO;
import com.cdqckj.gmis.biztemporary.vo.PriceChangeVO;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.operateparam.entity.PriceScheme;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * 用气类型变更Api
 * @author HC
 * @date 2020/11/17
 */
@FeignClient(name = "${gmis.feign.gmis-business-server:gmis-business-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/gasTypeChangeRecord", qualifier = "GasTypeChangeRecordBizApi")
public interface GasTypeChangeRecordBizApi {

    /**
     * 批量新增
     *
     * @param list 保存参数
     * @return 实体
     */
    @PostMapping(value = "/saveList")
    R<GasTypeChangeRecord> save(@RequestBody @Valid List<GasTypeChangeRecordSaveDTO> list);

    /**
     * 单体新增
     * @param gasTypeChangeRecordSaveDTO 保存参数
     * @return 实体
     */
    @PostMapping
    R<GasTypeChangeRecord> save(@RequestBody @Valid GasTypeChangeRecordSaveDTO gasTypeChangeRecordSaveDTO);


    /**
     * 分页查询
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<IPage<GasTypeChangeRecord>> page(PageParams<GasTypeChangeRecordPageDTO> params);


    /**
     * 批量查询
     * @param data 批量查询
     * @return 查询结果
     */
    @PostMapping("/queryEx")
    R<List<HashMap<String,Object>>> queryEx(@RequestBody GasTypeChangeRecord data);

    /**
     * 业务关注点用气类型变更记录列表查询
     * @param queryInfo 查询参数
     * @return 变更记录列表
     */
    @PostMapping(value = "/queryFocusInfo")
    R<List<GasTypeChangeRecordVO>> queryFocusInfo(@RequestBody GasTypeChangeRecord queryInfo);

    /**
     * 根据变更记录获取价格方案
     * @param priceChangeVO
     * @return
     */
    @PostMapping("/getPriceSchemeByRecord")
    PriceScheme getPriceSchemeByRecord(@RequestBody PriceChangeVO priceChangeVO);

}
