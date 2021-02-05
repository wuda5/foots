package com.cdqckj.gmis.securityed.controller;

import com.cdqckj.gmis.common.utils.StringUtil;
import com.cdqckj.gmis.securityed.dto.SecurityCheckItemsSaveDTO;
import com.cdqckj.gmis.securityed.entity.SecurityCheckItems;
import com.cdqckj.gmis.securityed.entity.SecurityCheckResult;
import com.cdqckj.gmis.securityed.dto.SecurityCheckResultSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckResultUpdateDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckResultPageDTO;
import com.cdqckj.gmis.securityed.service.SecurityCheckItemsService;
import com.cdqckj.gmis.securityed.service.SecurityCheckResultService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.securityed.vo.AppCkResultVo;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 安检结果
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/securityCheckResult")
@Api(value = "SecurityCheckResult", tags = "安检结果")
@PreAuth(replace = "securityCheckResult:")
public class SecurityCheckResultController extends SuperController<SecurityCheckResultService, Long, SecurityCheckResult, SecurityCheckResultPageDTO, SecurityCheckResultSaveDTO, SecurityCheckResultUpdateDTO> {


    @Autowired
    private SecurityCheckItemsController securityCheckItemsColler;
    private SecurityCheckItemsService securityCheckItemsService;

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<SecurityCheckResult> securityCheckResultList = list.stream().map((map) -> {
            SecurityCheckResult securityCheckResult = SecurityCheckResult.builder().build();
            //TODO 请在这里完成转换
            return securityCheckResult;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(securityCheckResultList));
    }

    /**
     * 录入安检结果（含安全隐患列表信息）
     * @param params
     * @return*/

    @ApiOperation(value = "录入或编辑安检结果（含安全隐患列表信息）")
    @PostMapping(value = "/saveCheckResult")
    R<SecurityCheckResult> saveCheckResult(@RequestBody AppCkResultVo params) {

        SecurityCheckResult data=new SecurityCheckResult();
        // 一：.只要有安检
        SecurityCheckResultUpdateDTO scResultDto = params.getScResultDto();
        if (null!=scResultDto.getId()){
            // 修改
            data = super.update(scResultDto).getData();

        }else {
            // 新增
            // 1. 保存安检结果result, (必须传安检 计划的业务编号 做关联)
            data = super.save(BeanPlusUtil.toBean(scResultDto,SecurityCheckResultSaveDTO.class)).getData();
        }

        // 二. 安检项相关，先删除再 新增...(如果没有选择的话，就不用做删除--也可看做没改安检项)
        List<SecurityCheckItemsSaveDTO> scItemsSaveDtoS = params.getScItemsSaveDtoS();
        if (CollectionUtils.isNotEmpty(scItemsSaveDtoS)) {
            // a.先删除(小心使用被全删除)
            if (StringUtil.isNotEmpty(params.getScResultDto().getScNo())){
              securityCheckItemsColler.deleteReal(new SecurityCheckItems().setScNo(params.getScResultDto().getScNo()));
            }

            SecurityCheckResult finalData = data;
            scItemsSaveDtoS.forEach(sc -> {
                sc.setScResultId(finalData.getId())
                        .setScNo(finalData.getScNo())
//                                        .setHandleUserId(BaseContextHandler.getUserId())
//                                        .setHandleTime(LocalDateTime.now())
                        .setHandleStatus(0);// 隐患处理状态，0 未处理， 1 已处理（在输入对应的隐患整改信息时，完成修改为 1 已处理 ）
            });
            // 2. b.保存隐患列表
            securityCheckItemsColler.saveList(scItemsSaveDtoS);
//            securityCheckItemsService.sa(scItemsSaveDtoS);
        }
        return R.success(data);
    }
}
