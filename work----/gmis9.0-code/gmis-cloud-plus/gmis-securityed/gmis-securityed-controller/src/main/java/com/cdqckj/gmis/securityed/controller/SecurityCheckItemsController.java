package com.cdqckj.gmis.securityed.controller;

import com.cdqckj.gmis.securityed.entity.SecurityCheckItems;
import com.cdqckj.gmis.securityed.dto.SecurityCheckItemsSaveDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckItemsUpdateDTO;
import com.cdqckj.gmis.securityed.dto.SecurityCheckItemsPageDTO;
import com.cdqckj.gmis.securityed.service.SecurityCheckItemsService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.securityed.vo.ScItemsOperVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 隐患列表
 * </p>
 *
 * @author gmis
 * @date 2020-11-16
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/securityCheckItems")
@Api(value = "SecurityCheckItems", tags = "隐患列表")
@PreAuth(replace = "securityCheckItems:")
public class SecurityCheckItemsController extends SuperController<SecurityCheckItemsService, Long, SecurityCheckItems, SecurityCheckItemsPageDTO, SecurityCheckItemsSaveDTO, SecurityCheckItemsUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<SecurityCheckItems> securityCheckItemsList = list.stream().map((map) -> {
            SecurityCheckItems securityCheckItems = SecurityCheckItems.builder().build();
            //TODO 请在这里完成转换
            return securityCheckItems;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(securityCheckItemsList));
    }

    /**
     * 根据 安检计划编号 获取对应 安检隐患列表信息
     *
     * @return
     */
    @ApiOperation(value = "根据 安检计划编号scNo 获取对应 安检隐患列表信息")
    @GetMapping("/findChecks")
    public R<List<ScItemsOperVo>> findFilesByInstallNumber(@RequestParam(name = "scNo") String scNo) {
        SecurityCheckItems sc = new SecurityCheckItems();
        sc.setScNo(scNo);
        // SecurityCheckItems 是隐患列表
        List<SecurityCheckItems> items = super.query(sc).getData();

        Map<String, List<SecurityCheckItems>> map = items.stream().collect(Collectors.groupingBy(SecurityCheckItems::getScTermCode));

        List<ScItemsOperVo> re = new ArrayList<>();
        map.forEach((k, v) ->{
            ScItemsOperVo vo = new ScItemsOperVo();

            vo.setScItemList(v);
            vo.setScTermName(v.get(0).getScTermName());
            vo.setScTermCode(k);

            re.add(vo);

        });
        return  R.success(re);
    }
}
