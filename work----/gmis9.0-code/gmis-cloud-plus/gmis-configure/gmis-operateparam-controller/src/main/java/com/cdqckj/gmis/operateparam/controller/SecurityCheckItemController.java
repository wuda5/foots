package com.cdqckj.gmis.operateparam.controller;

import com.cdqckj.gmis.authority.entity.common.Dictionary;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.oauth.api.DictionaryItemApi;
import com.cdqckj.gmis.operateparam.entity.SecurityCheckItem;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemSaveDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.SecurityCheckItemPageDTO;
import com.cdqckj.gmis.operateparam.service.SecurityCheckItemService;

import java.util.*;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.operateparam.vo.ScItemsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.cdqckj.gmis.security.annotation.PreAuth;


/**
 * <p>
 * 前端控制器
 * 安检子项配置
 * </p>
 *
 * @author gmis
 * @date 2020-11-03
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/securityCheckItem")
@Api(value = "SecurityCheckItem", tags = "安检子项配置")
@PreAuth(replace = "securityCheckItem:")
public class SecurityCheckItemController extends SuperController<SecurityCheckItemService, Long, SecurityCheckItem, SecurityCheckItemPageDTO, SecurityCheckItemSaveDTO, SecurityCheckItemUpdateDTO> {

    @Autowired
    DictionaryItemApi dictionaryItemApi;

    @ApiOperation(value = "查询所有安检配置子项", notes = "查询所有安检配置子项")
    @GetMapping(value = "/getAllPzCks")
    public R< List<SecurityCheckItem> > getAllPzCks(){

        List<DictionaryItem> dics = dictionaryItemApi.listByType("SECURITY_TYPE").getData();
        List<String> itemCodeList = dics.stream().map(DictionaryItem::getCode).collect(Collectors.toList());
        List<SecurityCheckItem> list = this.baseService.list(Wraps.<SecurityCheckItem>lbQ().in(SecurityCheckItem::getSecurityCode, itemCodeList));

        return R.success(list);
    }

    @ApiOperation(value = "查询所有安检项及对应的子项信息", notes = "查询所有安检项及对应的子项信息")
    @PostMapping(value = "/getAllCheckInfos")
    @ResponseBody
    public R< List<ScItemsVo>> getAllCheckInfos(@RequestBody Map<String,Object> map){

        List<DictionaryItem> dics = dictionaryItemApi.listByType("SECURITY_TYPE").getData();
        List<String> itemCodeList = dics.stream().map(DictionaryItem::getCode).collect(Collectors.toList());
        List<SecurityCheckItem> list = this.baseService.list(Wraps.<SecurityCheckItem>lbQ().in(SecurityCheckItem::getSecurityCode, itemCodeList));

//        Map<String, List<SecurityCheckItem>> temp = list.stream().collect(Collectors.groupingBy(SecurityCheckItem::getSecurityCode));
        List<ScItemsVo> re = new ArrayList<>();
        dics.forEach(x->{
            List<SecurityCheckItem> collect = list.stream().filter(zi -> Objects.equals(x.getCode(), zi.getSecurityCode())).collect(Collectors.toList());
            ScItemsVo scItemsVo = new ScItemsVo();
            scItemsVo.setScTermName(x.getName()).setScTermCode(x.getCode())
                     .setScItemList(collect);

            re.add(scItemsVo);
        });

        return R.success(re);
    }

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<SecurityCheckItem> securityCheckItemList = list.stream().map((map) -> {
            SecurityCheckItem securityCheckItem = SecurityCheckItem.builder().build();
            //TODO 请在这里完成转换
            return securityCheckItem;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(securityCheckItemList));
    }
    @PostMapping(value = "/check")
    public R<Boolean> check(@RequestBody SecurityCheckItemSaveDTO securityCheckItemSaveDTO){
        return R.success(baseService.check(securityCheckItemSaveDTO));
    }

    @PostMapping(value = "/checkupadate")
    public R<Boolean> checkupadate(@RequestBody SecurityCheckItemUpdateDTO securityCheckItemUpdateDTO){
        return R.success(baseService.checkupadate(securityCheckItemUpdateDTO));
    };
}
