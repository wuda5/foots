package com.cdqckj.gmis.bizcenter.sys.config.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.api.CommonConfigurationApi;
import com.cdqckj.gmis.base.DeleteStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.sys.config.service.TollItemService;
import com.cdqckj.gmis.operateparam.TollItemBizApi;
import com.cdqckj.gmis.operateparam.UseGasTypeBizApi;
import com.cdqckj.gmis.systemparam.dto.TollItemPageDTO;
import com.cdqckj.gmis.systemparam.dto.TollItemSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TollItemUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import com.cdqckj.gmis.systemparam.enumeration.TolltemSceneEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 收费配置前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/sysparam/toll")
@Api(value = "toll", tags = "收费配置")
//@PreAuth(replace = "tollItem:")
public class TollItemController {

    @Autowired
    public TollItemBizApi tollItemBizApi;
    @Autowired
    public CommonConfigurationApi commonConfigurationApi;
    @Autowired
    public UseGasTypeBizApi useGasTypeBizApi;
    @Autowired
    private TollItemService tollItemService;
    @ApiOperation(value = "新增收费信息")
    @PostMapping("/tollItem/add")
    public R<TollItem> saveTollItem(@RequestBody @Valid TollItemSaveDTO tollItemSaveDTO){
        return tollItemService.saveTollItem(tollItemSaveDTO);
    }

    @ApiOperation(value = "更新收费信息")
    @PutMapping("/tollItem/update")
    public R<TollItem> updateTollItem(@RequestBody @Valid TollItemUpdateDTO tollItemUpdateDTO){
       return tollItemService.updateTollItem(tollItemUpdateDTO);
    }

    @ApiOperation(value = "批量更新收费信息")
    @PutMapping("/tollItem/updateBatch")
    public R<Boolean> updateTollItemBatch(@RequestBody List<TollItemUpdateDTO> list){
        return tollItemBizApi.updateBatch(list);
    }

    @ApiOperation(value = "删除收费信息")
    @DeleteMapping("/tollItem/delete")
    public R<Boolean> deleteTollItem(@RequestParam("ids[]") List<Long> ids){
        return tollItemBizApi.logicalDelete(ids);
    }

    @ApiOperation(value = "分页查询收费信息信息")
    @PostMapping("/tollItem/page")
    public R<Page<TollItem>> pageTollItem(@RequestBody PageParams<TollItemPageDTO> params){
        params.getModel().setDeleteStatus(DeleteStatusEnum.NORMAL.getValue());
        Page<TollItem> data = tollItemBizApi.page(params).getData();
        data.getRecords().stream().forEach(item->{
            if(TolltemSceneEnum.GAS_FEE.eq(item.getSceneCode())
                    || TolltemSceneEnum.GAS_COMPENSATION.eq(item.getSceneCode())
                ||TolltemSceneEnum.INSURANCE_FEE.eq(item.getSceneCode())
            ) {
                item.setOperate(false);
            }else{
                item.setOperate(true);
            }
        });
        return R.success(data);
    }

    @ApiOperation(value = "获取枚举参数")
    @PostMapping("/tollItem/params")
    public R<Map<String,Object>> getTollItemParams(){
        Map<String,Object> map = new HashMap<>();
        map.put("SCENES",commonConfigurationApi.findCommonConfigbytype("SCENES").getData());
        map.put("CHARGE_FREQUENCY",commonConfigurationApi.findCommonConfigbytype("CHARGE_FREQUENCY").getData());
        map.put("SETTLEMENT_TYPE",commonConfigurationApi.findCommonConfigbytype("SETTLEMENT_TYPE").getData());
        map.put("SETTLEMENT_MODE",commonConfigurationApi.findCommonConfigbytype("SETTLEMENT_MODE").getData());
        return R.success(map);
    }

    /**
     * 当前收费场景是否存在收费项
     * @auther HC
     * @param sceneCode 收费场景编码
     * @return
     */
    @ApiOperation(value = "是否存在收费项")
    @ApiImplicitParam(name = "sceneCode",value = "收费场景编码")
    @GetMapping("/tollItem/exist")
    public R<Boolean> existTollItem(@RequestParam("sceneCode") String sceneCode){

        return tollItemBizApi.whetherSceneCharge(sceneCode);
    }
}
