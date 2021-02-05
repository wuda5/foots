package com.cdqckj.gmis.bizcenter.securitycheckrecord.controller;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.oauth.api.UserApi;
import com.cdqckj.gmis.operateparam.SecurityCheckItemApi;
import com.cdqckj.gmis.operateparam.entity.SecurityCheckItem;
import com.cdqckj.gmis.operation.OrderJobFileBizApi;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.dto.OrderRecordSaveDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.enumeration.OrderStateEnum;
import com.cdqckj.gmis.operation.enumeration.OrderTypeEnum;
import com.cdqckj.gmis.securityed.SecurityCheckItemsApi;
import com.cdqckj.gmis.securityed.SecurityCheckProcessApi;
import com.cdqckj.gmis.securityed.SecurityCheckRecordApi;
import com.cdqckj.gmis.securityed.SecurityCheckResultApi;
import com.cdqckj.gmis.securityed.dto.*;
import com.cdqckj.gmis.securityed.entity.Enum.SecurityCheckRecordStatusEnum;
import com.cdqckj.gmis.securityed.entity.SecurityCheckItems;
import com.cdqckj.gmis.securityed.entity.SecurityCheckProcess;
import com.cdqckj.gmis.securityed.entity.SecurityCheckRecord;
import com.cdqckj.gmis.securityed.entity.SecurityCheckResult;
import com.cdqckj.gmis.securityed.vo.AppCkResultVo;
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

@Slf4j
@Validated
@RestController
@RequestMapping("/securityCheckRecord")
@Api(value = "securityCheckRecord", tags = "安检计划")
public class SecurityCheckRecordControllor {
    @Autowired
    SecurityCheckItemApi pzScApi;
    @Autowired
    private SecurityCheckRecordApi securityCheckRecordApi;

    @Autowired
    private SecurityCheckResultApi securityCheckResultApi;

    @Autowired
    OrderJobFileBizApi orderJobFileBizApi;

    @Autowired
    private SecurityCheckItemsApi securityCheckItemsApi;

    @Autowired
    private UserApi userApi;

    @Autowired
    private SecurityCheckProcessApi securityCheckProcessApi;

    /*
    * 查询所用用户  以后通过部门id差安检部门的用户
    * */
    @ApiOperation(value = "查询用户（目前查询出所有用户以后通过部门id查出来）")
    @PostMapping("/queryUser")
    public R<List<User>> queryUser(@RequestBody User data){
        data.setStatus(true);
        return userApi.query(data);
    }

    /*
     * 根据安检编号查询流程信息
     * */
    @ApiOperation(value = "根据安检编号查询流程信息")
    @PostMapping("/queryProcess")
    public R<List<SecurityCheckProcess>> queryProcess(@RequestBody SecurityCheckProcess securityCheckProcess){
        return securityCheckProcessApi.query(securityCheckProcess);
    }

    /**
     * 新增安检计划信息
     * @param securityCheckRecordSaveDTOS
     * @return
     */
    @ApiOperation(value = "新增安检计划信息")
    @PostMapping("/saveSecurityCheckRecord")
    public R<Boolean> saveSecurityCheckRecord(@RequestBody @Validated List<SecurityCheckRecord> securityCheckRecordSaveDTOS){
        return securityCheckRecordApi.saveSecurityCheckRecord(securityCheckRecordSaveDTOS);
    }


    /**
     * 编辑安检计划信息
     * @param
     * @return
     */
    @ApiOperation(value = "编辑安检计划信息")
    @PostMapping("/updateSecurityCheckRecord")
    public R<SecurityCheckRecord> saveSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecordUpdateDTO params){
        return securityCheckRecordApi.update(params);
    }

    /**
     * 分页查询安检计划信息
     * @param params
     * @return
     */
    @ApiOperation(value = "分页查询安检计划信息", notes = "分页查询安检计划信息")
    @PostMapping(value = "/pageSecurityCheckRecord")
    @SysLog(value = "'分页列表查询:第' + #params?.current + '页, 显示' + #params?.size + '行'", response = false)
    public R<Page<SecurityCheckRecord>> securityCheckRecordPage(@RequestBody @Validated PageParams<SecurityCheckRecordPageDTO> params){
        return securityCheckRecordApi.page(params);
    }

    /**
     * 提审
     * @param params
     * @return
     */
    @ApiOperation(value = "提审")
    @PostMapping(value = "/approval")
    R<SecurityCheckRecord> approvalSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecordUpdateDTO params){
        params.setDataStatus(SecurityCheckRecordStatusEnum.PENDING_APPROVAL.getCode());
        return securityCheckRecordApi.update(params);
    }

    /**
     * 审核
     * @param params
     * @return
     */
    @ApiOperation(value = "审核")
    @PostMapping(value = "/approvaled")
    R<Integer> approvaledSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params){
        return securityCheckRecordApi.approvaledSecurityCheckRecord(params);
    }

    /**
     * 驳回
     * @param params
     * @return
     */
    @ApiOperation(value = "驳回")
    @PostMapping(value = "/reject")
    R<Integer> rejectSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params){
        return securityCheckRecordApi.reject(params);
    }

    /**
     * 撤回
     * @param params
     * @return
     */
    @ApiOperation(value = "撤回")
    @PostMapping(value = "/recall")
    R<SecurityCheckRecord> recallSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecordUpdateDTO params){
        params.setDataStatus(SecurityCheckRecordStatusEnum.PENDING_INITIATE_REVIEW.getCode());
        return securityCheckRecordApi.update(params);
    }

    /**
     * 撤回
     * @param params
     * @return
     */
    @ApiOperation(value = "结束")
    @PostMapping(value = "/end")
    R<Integer> endSecurityCheckRecord(@RequestBody @Validated SecurityCheckRecord params){
        return securityCheckRecordApi.endSecurityCheckRecord(params);
    }
      /*
     * 录入安检结果
     * @param params
     * @return*/

    @ApiOperation(value = "录入安检结果")
    @PostMapping(value = "/saveCheckResult")
    R<SecurityCheckResult> saveCheckResult(@RequestBody AppCkResultVo params){
        SecurityCheckResult data = securityCheckResultApi.saveCheckResult(params).getData();
 /*       SecurityCheckRecord securityCheckRecord =new SecurityCheckRecord();
        securityCheckRecord.setScNo(data.getScNo());
        securityCheckRecord= securityCheckRecordApi.queryOne(securityCheckRecord).getData();
        if(securityCheckRecord!=null){
            securityCheckRecord.setSecurityCheckResult(0);
            securityCheckRecordApi.update(BeanPlusUtil.toBean(securityCheckRecord,new SecurityCheckRecordUpdateDTO().getClass()));
        }*/
        return R.success(data);
    }
    @ApiOperation(value = "获取安检结果")
    @PostMapping(value = "/getCheckResult")
    R<SecurityCheckResult> getCheckResult(@RequestBody SecurityCheckResult query){
        return securityCheckResultApi.queryOne(query);
    }


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

    @ApiOperation(value = "分页查询安检结果安檢結果")
    @PostMapping(value = "/pageCheckResult")
    R<Page<SecurityCheckResult>> pageCheckResult(@RequestBody @Validated PageParams<SecurityCheckResultPageDTO> params){
       return securityCheckResultApi.page(params);
    }

    @ApiOperation(value = "编辑安檢結果")
    @PostMapping(value = "/updateCheckResult")
    R<SecurityCheckResult> updateCheckResult(@RequestBody UpdateResult params){
        R<SecurityCheckResult> update = securityCheckResultApi.update(params.getParams());
        if(update.getIsSuccess()){
            if(CollectionUtils.isNotEmpty(params.getSecurityCheckItemsUpdateDTOS())){
                securityCheckItemsApi.updateBatchById(params.getSecurityCheckItemsUpdateDTOS());
            }
            return update;
        }
        return R.fail("修改失败");
    }

    @ApiOperation(value = "分页查询隐患信息")
    @PostMapping(value = "/pageSecurityCheckItems")
    R<Page<SecurityCheckItems>> page(@RequestBody @Validated PageParams<SecurityCheckItemsPageDTO> params){
       return securityCheckItemsApi.page(params);
    }

    @ApiOperation(value = "录入整改隐患信息")
    @PostMapping(value = "/update")
    R<SecurityCheckItems> update(@RequestBody @Validated(SuperEntity.Update.class) SecurityCheckItemsUpdateDTO updateDTO){
        updateDTO.setHandleStatus(1);
        updateDTO.setHandleTime(LocalDateTime.now());
        updateDTO.setHandleUserId(BaseContextHandler.getUserId());
        R<SecurityCheckItems> update = securityCheckItemsApi.update(updateDTO);
     /*   if(update.getIsSuccess()){
            SecurityCheckRecord data = securityCheckRecordApi.queryOne(new SecurityCheckRecord().setScNo(updateDTO.getScNo())).getData();
            if(data!=null){
                data.setSecurityCheckResult(updateDTO.getHandleStatus());
            }
        }*/
        return update;
    }
    // tset
    @ApiOperation(value = "安检结单")
    @PostMapping(value = "/completeOrder")
    R<Boolean> completeOrder(@RequestBody @Validated SecurityCheckRecord securityCheckRecord){
        SecurityCheckResult result =new SecurityCheckResult();
        result.setScNo(securityCheckRecord.getScNo());
        result = securityCheckResultApi.queryOne(result).getData();
        SecurityCheckItems securityCheckItems =new SecurityCheckItems();
        securityCheckItems.setScNo(securityCheckRecord.getScNo());
        securityCheckItems = securityCheckItemsApi.queryOne(securityCheckItems).getData();
        if(result==null||securityCheckItems==null||securityCheckItems.getHandleStatus()==0){
            return R.fail("必须输入安检结果和隐患整改完成才能结单");
        }
        securityCheckRecord.setOrderState(OrderStateEnum.COMPLETE.getCode());
        R<Integer> integerR = securityCheckRecordApi.completeOrder(securityCheckRecord);
        if(integerR.getIsSuccess()){
            return R.success(true);
        }
        return R.fail("安检结果失败");
    }

}
