package com.cdqckj.gmis.operationcenter.app.securityed.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.entity.common.DictionaryItem;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.installed.InstallProcessRecordApi;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.installed.vo.WorkOrderOperCommonVo;
import com.cdqckj.gmis.oauth.api.DictionaryItemApi;
import com.cdqckj.gmis.operateparam.SecurityCheckItemApi;
import com.cdqckj.gmis.operateparam.entity.SecurityCheckItem;
import com.cdqckj.gmis.operateparam.vo.ScItemsVo;
import com.cdqckj.gmis.operation.OrderJobFileBizApi;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.dto.OrderRecordPageDTO;
import com.cdqckj.gmis.operation.entity.OrderJobFile;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.enumeration.OrderStateEnum;
import com.cdqckj.gmis.operation.enumeration.OrderTypeEnum;
import com.cdqckj.gmis.operation.vo.CheckSelfOrderDto;
import com.cdqckj.gmis.operationcenter.app.securityed.dto.CheckSelfCheckRecordDto;
import com.cdqckj.gmis.securityed.SecurityCheckItemsApi;
import com.cdqckj.gmis.securityed.SecurityCheckRecordApi;
import com.cdqckj.gmis.securityed.SecurityCheckResultApi;
import com.cdqckj.gmis.securityed.dto.*;
import com.cdqckj.gmis.securityed.entity.SecurityCheckItems;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import com.cdqckj.gmis.securityed.entity.SecurityCheckResult;
import com.cdqckj.gmis.securityed.vo.AppCkResultVo;
import com.cdqckj.gmis.securityed.vo.ResultVo;
import com.cdqckj.gmis.securityed.vo.ScItemsOperVo;

import com.cdqckj.gmis.securityed.vo.ScItemsResultVo;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 *
 * </p>
 *
 * @author tp
 * @date 2020-07-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/appCheck/record")
@Api(value = "/appCheck/record", tags = "app 安检计划")
public class AppCheckRecordController {
    @Autowired
    SecurityCheckItemApi pzScApi;
    @Autowired
    DictionaryItemApi dictionaryItemApi;

    @Autowired
    public OrderJobFileBizApi jobFileBizApi;

    @Autowired
    private OrderRecordApi orderRecordApi;

    @Autowired
    private InstallRecordApi installRecordApi;
    @Autowired
    private InstallProcessRecordApi installProcessRecordApi;

    @Autowired
    private SecurityCheckItemsApi securityCheckItemsApi;

    @Autowired
    private SecurityCheckResultApi securityCheckResultApi;

    @Autowired
    private SecurityCheckRecordApi securityCheckRecordApi;


//    /**工单列表
//     * @param
//     * @return
//     */
//    @ApiOperation(value = "根据派单人查询 工单列表 列表")
//    @PostMapping(value = "/pageCheckOrder")
//    public R<Page<OrderRecord>> pageBy(@RequestBody @Validated CheckSelfOrderDto dto) {
////
//        PageParams<OrderRecordPageDTO> scopeParams = new PageParams<OrderRecordPageDTO>();
//        scopeParams.setSize(dto.getPageSize());
//        scopeParams.setCurrent(dto.getPageNo());
//
//        OrderRecordPageDTO pa = new OrderRecordPageDTO()
//                .setReceiveUserId(dto.getReceiveUserId())
//                .setOrderStatus(dto.getOrderState())
//                .setBusinessType(OrderTypeEnum.SECURITY_ORDER.getCode());
//
//        scopeParams.setModel(pa);
//        // 1.查询工单
//        R<Page<OrderRecord>> re = orderRecordApi.page(scopeParams);
//
//        return re;
//    }
    /**
     * 安检计划列表
     * @param
     * @return
     */
    @ApiOperation(value = "根据派单人查询 安检计划列表")
    @PostMapping(value = "/pageRecord")
    public R<Page<SecurityCheckRecord>> pageRecord(@RequestBody @Validated CheckSelfCheckRecordDto dto) {
//
        PageParams<SecurityCheckRecordPageDTO> scopeParams = new PageParams<>();
        scopeParams.setSize(dto.getPageSize());
        scopeParams.setCurrent(dto.getPageNo());

        SecurityCheckRecordPageDTO pa = new SecurityCheckRecordPageDTO()
//                .setDistributionUserId(dto.getDistributionUserId())
                .setSecurityCheckUserId(dto.getSecurityCheckUserId())
                .setDataStatusList(dto.getDataStatus());

        scopeParams.setModel(pa);
        // 1.查询安检记录
        R<Page<SecurityCheckRecord>> re = securityCheckRecordApi.page(scopeParams);

        return re;
    }

    /**
     * 录入安检结果（含安全隐患列表信息）
     * @param params
     * @return*/

    @ApiOperation(value = "录入或编辑安检结果（含安全隐患列表信息）")
    @PostMapping(value = "/saveCheckResult")
    R<SecurityCheckResult> saveCheckResult(@RequestBody AppCkResultVo params) {
        SecurityCheckResult data = securityCheckResultApi.saveCheckResult(params).getData();
//        SecurityCheckResult data;
//        // 一：.只要有安检
//        SecurityCheckResultUpdateDTO scResultDto = params.getScResultDto();
//        if (null!=scResultDto.getId()){
//            // 修改
//            data = securityCheckResultApi.update(scResultDto).getData();
//
//        }else {
//            // 新增
//            // 1. 保存安检结果result, (必须传安检 计划的业务编号 做关联)
//             data = securityCheckResultApi.save(BeanPlusUtil.toBean(scResultDto,SecurityCheckResultSaveDTO.class)).getData();
//        }
//
//        // 二. 安检项相关，先删除再 新增...(如果没有选择的话，就不用做删除--也可看做没改安检项)
//        List<SecurityCheckItemsSaveDTO> scItemsSaveDtoS = params.getScItemsSaveDtoS();
//        if (CollectionUtils.isNotEmpty(scItemsSaveDtoS)) {
//            // a.先删除
//            securityCheckItemsApi.deleteReal(new SecurityCheckItems().setScNo(params.getScResultDto().getScNo()));
//
//            SecurityCheckResult finalData = data;
//            scItemsSaveDtoS.forEach(sc -> {
//                sc.setScResultId(finalData.getId())
//                        .setScNo(finalData.getScNo())
////                                        .setHandleUserId(BaseContextHandler.getUserId())
////                                        .setHandleTime(LocalDateTime.now())
//                        .setHandleStatus(0);// 隐患处理状态，0 未处理， 1 已处理（在输入对应的隐患整改信息时，完成修改为 1 已处理 ）
//            });
//            // 2. b.保存隐患列表
//            securityCheckItemsApi.saveList(scItemsSaveDtoS);
//        }
        return R.success(data);
    }
    // 计划编号，查询安装结果（隐患列表）信息

    /**
     * 根据 安检计划编号 获取对应 安检隐患列表信息
     *
     * @return
     */
    @ApiOperation(value = "根据 安检计划编号scNo 获取对应 选中的安检隐患列表信息")
    @GetMapping("/findChecks")
    public R<List<ScItemsOperVo>> findFilesByInstallNumber(@RequestParam(name = "scNo") String scNo) {

        R<List<ScItemsOperVo>> re = securityCheckItemsApi.findFilesByInstallNumber(scNo);
        return  re;
    }

    @ApiOperation(value = "编辑 安檢隐患列表（整改+隐患）")
    @PostMapping(value = "/updateCheckResult")
    R<Boolean> updateCheckResult(@RequestBody  List<SecurityCheckItemsUpdateDTO>  params){
        params.forEach(x->x.setHandleStatus(1));// 隐患处理状态，0 未处理， 1 已处理（在输入对应的隐患整改信息时，完成修改为 1 已处理 ）
        securityCheckItemsApi.updateBatchById(params);
        return R.success();
    }
    //---------------------test -----------------------

    @ApiOperation(value = "回显安检结果 + 安检项（包含未选择的子项）相关 --》 根据计划编号scNo ",
            notes = "回显安检结果 + 安检项相关（包含未选择的子项） --》 根据计划编号scNo（当安检结果无则调新增，有掉修改）")
    @GetMapping(value = "/getScItemsResult")
    public R< ScItemsResultVo > getScItemsResult (@RequestParam(name = "scNo") String scNo){
        // 二. 查询 安检结果， 可能没有
        SecurityCheckResult reResult = securityCheckResultApi.queryOne(new SecurityCheckResult().setScNo(scNo)).getData();
        // 如果查询不到，也要封装一个没有结果id的对象，需要包含气表厂家等信息(这些信息可以用scNo从 安检计划取到)
        if (reResult==null){
            reResult = new SecurityCheckResult();
            SecurityCheckRecord scRecord = securityCheckRecordApi.queryOne(new SecurityCheckRecord().setScNo(scNo)).getData();
            BeanPlusUtil.copyProperties(scRecord,reResult);// 拷贝计划的相关基础信息做回显新增结果用
            reResult.setId(null);
        }

        // 1. 获取所有的安检隐患配置--》转换为安检（安检子项） allSc
        List<SecurityCheckItem> pzScItems = pzScApi.getAllPzCks().getData();

        List<SecurityCheckItems> allSc = pzScItems.stream().map(pz -> new SecurityCheckItems()
                .setScTermName(pz.getSecurityName()).setScTermCode(pz.getSecurityCode())
                .setScTermItemsName(pz.getName()).setScTermItemsId(pz.getId()).setDangerLeve(String.valueOf(pz.getDangerLevel()))).collect(Collectors.toList());
//        Map<String, List<SecurityCheckItem>> temp = list.stream().collect(Collectors.groupingBy(SecurityCheckItem::getSecurityCode));
        // 2. 查询已经是被选择了的 隐患列表 SecurityCheckItems
        List<SecurityCheckItems> items = securityCheckItemsApi.query(new SecurityCheckItems().setScNo(scNo)).getData();
        if (CollectionUtils.isNotEmpty(items)){
            // 3. 用2查询已经被选中的隐患列表 去 替换1 中allSc 的相同的，组成混合的----
            for(SecurityCheckItems al: allSc){
                SecurityCheckItems finalAl = al;
                SecurityCheckItems t = items.stream().filter(y -> Objects.equals(finalAl.getScTermItemsId(), y.getScTermItemsId())).findFirst().orElse(null);

                if (t!=null){
                    t.setScTermCode(al.getScTermCode());// 避免异常暂时处理
                    t.setScTermName(al.getScTermName());
                    t.setDangerLeve(al.getDangerLeve());
                    // 把已有的去替换掉xx
                    BeanPlusUtil.copyProperties(t,al);
                    al.setSelected(true);

                }
            }
        }

        // 4. 最后组装 allSc 为--》List<ScItemsOperVo>  SecurityCheckItems::getScTermCode必须不为空否则异常
        Map<String, List<SecurityCheckItems>> map = allSc.stream().collect(Collectors.groupingBy(SecurityCheckItems::getScTermCode));

        List<ScItemsOperVo> reItems = new ArrayList<>(); // 构建安检项相关的结果
        map.forEach((k, v) ->{
            ScItemsOperVo vo = new ScItemsOperVo();

            vo.setScItemList(v);
            vo.setScTermName(v.get(0).getScTermName());
            vo.setScTermCode(k);

            reItems.add(vo);

        });
        // 最后组装
        ScItemsResultVo re = new ScItemsResultVo(reItems, reResult);

        return R.success(re);
    }
}
