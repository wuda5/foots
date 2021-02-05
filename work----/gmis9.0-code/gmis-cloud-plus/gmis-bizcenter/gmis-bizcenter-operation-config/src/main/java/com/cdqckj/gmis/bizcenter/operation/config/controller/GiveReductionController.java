package com.cdqckj.gmis.bizcenter.operation.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.operation.config.service.impl.GiveReductionServiceImpl;
import com.cdqckj.gmis.bizcenter.operation.config.vo.GiveReductionConfVo;
import com.cdqckj.gmis.operateparam.GiveReductionConfBizApi;
import com.cdqckj.gmis.operateparam.TollItemBizApi;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfPageDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfSaveDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionConfUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.GiveReductionQueryDTO;
import com.cdqckj.gmis.operateparam.entity.GiveReductionConf;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 赠送活动总览
 * </p>
 * @author lmj
 * @date 2020-06-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/operparam/giveReduction")
@Api(value = "giveReduction", tags = "赠送活动")
//@PreAuth(replace = ":")
public class GiveReductionController {

    @Autowired
    public GiveReductionConfBizApi giveReductionConfBizApi;
    @Autowired
    public GiveReductionServiceImpl giveReductionService;
    @Autowired
    TollItemBizApi tollItemBizApi;

    @ApiOperation(value = "新增赠送活动信息")
    @PostMapping("/add")
    public R<GiveReductionConf> saveGive(@RequestBody GiveReductionConfSaveDTO saveDTO){
        return giveReductionService.saveGiveReduction(saveDTO);
    }

    @ApiOperation(value = "修改赠送活动信息")
    @PutMapping("/update")
    public R<GiveReductionConf> updateGive(@RequestBody GiveReductionConfUpdateDTO updateDTO){

        return giveReductionService.updateGiveReduction(updateDTO);
    }

/*    @ApiOperation(value = "批量修改赠送活动信息")
    @PutMapping("/updateBatchById")
    public R<Boolean> updateBatchById(@RequestBody List<GiveReductionConfUpdateDTO> updateDTO){
        return giveReductionConfBizApi.updateBatchById(updateDTO);
    }*/

    @ApiOperation(value = "查询赠送活动信息")
    @PostMapping("/page")
    public R<Page<GiveReductionConfVo>> pageGive(@RequestBody PageParams<GiveReductionConfPageDTO> params){
        R<Page<GiveReductionConfVo>> currResult;
        R<Page<GiveReductionConf>> pageR= giveReductionConfBizApi.page(params);
        if(pageR.getIsSuccess() && pageR.getData()!=null && CollectionUtils.isNotEmpty(pageR.getData().getRecords())){
            List<GiveReductionConf> confs=pageR.getData().getRecords();
            List<Long> tollIds=new ArrayList<>();
            List<GiveReductionConfVo>  resultList=new ArrayList<>();
            GiveReductionConfVo vo;
            for (GiveReductionConf conf : confs) {
                vo=new GiveReductionConfVo();
                BeanUtils.copyProperties(conf,vo);
                resultList.add(vo);
                if(conf.getTollItemId()!=null) {
                    tollIds.add(conf.getTollItemId());
                }
            }
            if(tollIds.size()>0){
               List<TollItem> items= tollItemBizApi.queryList(tollIds).getData();
               if(items!=null){
                  Map<Long,TollItem> map= items.stream().collect(Collectors.toMap(TollItem::getId, TollItem->TollItem));
                   for (GiveReductionConfVo confVo : resultList) {
                       if(confVo.getTollItemId()!=null && map.containsKey(confVo.getTollItemId()))
                       confVo.setTollItemName(map.get(confVo.getTollItemId()).getItemName());
                   }
               }
            }
            Page<GiveReductionConfVo> page=new Page<>();
            page.setRecords(resultList);
            if(pageR.getData()!=null) {
                page.setCurrent(pageR.getData().getCurrent());
                page.setSize(pageR.getData().getSize());
                page.setTotal(pageR.getData().getTotal());
                page.setOrders(pageR.getData().getOrders());
                page.setSearchCount(pageR.getData().isSearchCount());
                page.setPages(pageR.getData().getPages());
                page.setOptimizeCountSql(pageR.getData().optimizeCountSql());
            }
            currResult= R.success(page);
            return currResult;
        }else{
            if(pageR.getIsSuccess()) return R.success(null);
            return R.success(new Page<>());
        }

    }


    @ApiOperation(value = "批量查询赠送活动信息")
    @PostMapping("/query")
    public R<List<GiveReductionConf>> queryGive(@RequestBody GiveReductionConf data){
        return giveReductionConfBizApi.query(data);
    }

    @ApiOperation(value = "查询当前赠送活动信息")
    @PostMapping("/queryEffectiveGiveReduction")
    public R<List<GiveReductionConf>> queryEffectiveGiveReduction(@RequestBody GiveReductionQueryDTO queryDTO){
        return giveReductionConfBizApi.queryEffectiveGiveReduction(queryDTO);
    }


}
