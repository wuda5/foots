package com.cdqckj.gmis.operateparam.controller;

import com.cdqckj.gmis.operateparam.entity.Preferential;
import com.cdqckj.gmis.operateparam.dto.PreferentialSaveDTO;
import com.cdqckj.gmis.operateparam.dto.PreferentialUpdateDTO;
import com.cdqckj.gmis.operateparam.dto.PreferentialPageDTO;
import com.cdqckj.gmis.operateparam.service.PreferentialService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 优惠活动表
 * </p>
 *
 * @author gmis
 * @date 2020-08-27
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/preferential")
@Api(value = "Preferential", tags = "优惠活动表")
@PreAuth(replace = "preferential:")
public class PreferentialController extends SuperController<PreferentialService, Long, Preferential, PreferentialPageDTO, PreferentialSaveDTO, PreferentialUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<Preferential> preferentialList = list.stream().map((map) -> {
            Preferential preferential = Preferential.builder().build();
            //TODO 请在这里完成转换
            return preferential;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(preferentialList));
    }
}
