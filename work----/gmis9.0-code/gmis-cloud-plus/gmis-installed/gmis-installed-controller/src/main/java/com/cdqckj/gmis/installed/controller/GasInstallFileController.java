package com.cdqckj.gmis.installed.controller;

import com.cdqckj.gmis.installed.entity.GasInstallFile;
import com.cdqckj.gmis.installed.dto.GasInstallFileSaveDTO;
import com.cdqckj.gmis.installed.dto.GasInstallFileUpdateDTO;
import com.cdqckj.gmis.installed.dto.GasInstallFilePageDTO;
import com.cdqckj.gmis.installed.service.GasInstallFileService;
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
 * 报装资料
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasInstallFile")
@Api(value = "GasInstallFile", tags = "报装资料")
@PreAuth(replace = "gasInstallFile:")
public class GasInstallFileController extends SuperController<GasInstallFileService, Long, GasInstallFile, GasInstallFilePageDTO, GasInstallFileSaveDTO, GasInstallFileUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GasInstallFile> gasInstallFileList = list.stream().map((map) -> {
            GasInstallFile gasInstallFile = GasInstallFile.builder().build();
            //TODO 请在这里完成转换
            return gasInstallFile;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(gasInstallFileList));
    }
}
