package com.cdqckj.gmis.installed.controller;

import com.cdqckj.gmis.installed.entity.InstallChargeDetail;
import com.cdqckj.gmis.installed.dto.InstallChargeDetailSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallChargeDetailUpdateDTO;
import com.cdqckj.gmis.installed.dto.InstallChargeDetailPageDTO;
import com.cdqckj.gmis.installed.service.InstallChargeDetailService;
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
 * 报装费用清单
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/installChargeDetail")
@Api(value = "InstallChargeDetail", tags = "报装费用清单")
@PreAuth(replace = "installChargeDetail:")
public class InstallChargeDetailController extends SuperController<InstallChargeDetailService, Long, InstallChargeDetail, InstallChargeDetailPageDTO, InstallChargeDetailSaveDTO, InstallChargeDetailUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<InstallChargeDetail> installChargeDetailList = list.stream().map((map) -> {
            InstallChargeDetail installChargeDetail = InstallChargeDetail.builder().build();
            //TODO 请在这里完成转换
            return installChargeDetail;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(installChargeDetailList));
    }
}
