package com.cdqckj.gmis.devicearchive.controller;

import com.cdqckj.gmis.devicearchive.entity.GasMeterInfoSerial;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSerialSaveDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSerialUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterInfoSerialPageDTO;
import com.cdqckj.gmis.devicearchive.service.GasMeterInfoSerialService;
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
 * 账户流水
 * </p>
 *
 * @author gmis
 * @date 2020-12-21
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasMeterInfoSerial")
@Api(value = "GasMeterInfoSerial", tags = "账户流水")
@PreAuth(replace = "gasMeterInfoSerial:")
public class GasMeterInfoSerialController extends SuperController<GasMeterInfoSerialService, Long, GasMeterInfoSerial, GasMeterInfoSerialPageDTO, GasMeterInfoSerialSaveDTO, GasMeterInfoSerialUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GasMeterInfoSerial> gasMeterInfoSerialList = list.stream().map((map) -> {
            GasMeterInfoSerial gasMeterInfoSerial = GasMeterInfoSerial.builder().build();
            //TODO 请在这里完成转换
            return gasMeterInfoSerial;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(gasMeterInfoSerialList));
    }
}
