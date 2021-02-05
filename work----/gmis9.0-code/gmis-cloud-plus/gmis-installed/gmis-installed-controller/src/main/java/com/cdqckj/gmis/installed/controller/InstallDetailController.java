package com.cdqckj.gmis.installed.controller;

import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.installed.entity.InstallDetail;
import com.cdqckj.gmis.installed.dto.InstallDetailSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallDetailUpdateDTO;
import com.cdqckj.gmis.installed.dto.InstallDetailPageDTO;
import com.cdqckj.gmis.installed.service.InstallDetailService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cdqckj.gmis.security.annotation.PreAuth;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 施工安装明细信息
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/installDetail")
@Api(value = "InstallDetail", tags = "施工安装明细信息")
@PreAuth(replace = "installDetail:")
public class InstallDetailController extends SuperController<InstallDetailService, Long, InstallDetail, InstallDetailPageDTO, InstallDetailSaveDTO, InstallDetailUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<InstallDetail> installDetailList = list.stream().map((map) -> {
            InstallDetail installDetail = InstallDetail.builder().build();
            //TODO 请在这里完成转换
//            selfHandle(installDetail);
            return installDetail;
        }).collect(Collectors.toList());



        return R.success(baseService.saveBatch(installDetailList));
//        return R.success(baseService.saveBatch(installDetailList));
    }

    // /exportCascadeTemplate

}
