package com.cdqckj.gmis.gasmeter.controller;

import com.cdqckj.gmis.authority.entity.core.Station;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.update.LbuWrapper;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionPageDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionSaveDTO;
import com.cdqckj.gmis.gasmeter.dto.GasMeterVersionUpdateDTO;
import com.cdqckj.gmis.gasmeter.entity.GasMeterVersion;
import com.cdqckj.gmis.gasmeter.service.GasMeterVersionService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 气表版本
 * </p>
 *
 * @author gmis
 * @date 2020-08-24
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/gasMeterVersion")
@Api(value = "GasMeterVersion", tags = "气表版本")
@PreAuth(replace = "gasMeterVersion:")
public class GasMeterVersionController extends SuperController<GasMeterVersionService, Long, GasMeterVersion, GasMeterVersionPageDTO, GasMeterVersionSaveDTO, GasMeterVersionUpdateDTO> {


    @ApiOperation(value = "禁用某些厂商下面的版号,0禁用，1开启")
    @PostMapping("/updateVersionStatusByFactoryIds")
    R<Boolean> updateVersionStatusByFactoryIds(@RequestParam("factoryIds[]") List<Long> factoryIds, @RequestParam("setVersionState") int setVersionState){
        LbuWrapper<GasMeterVersion> wrapper = Wraps.lbU();
        wrapper.set(GasMeterVersion::getVersionState,setVersionState)
                .in(GasMeterVersion::getCompanyId,factoryIds);
        baseService.update(wrapper);
        return R.success();
    }
    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<GasMeterVersion> gasMeterVersionList = list.stream().map((map) -> {
            GasMeterVersion gasMeterVersion = GasMeterVersion.builder().build();
            //TODO 请在这里完成转换
            return gasMeterVersion;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(gasMeterVersionList));
    }

    /**
     * 查询当前状态
     * @param gasMeterVersion
     * @return
     */
    @ApiOperation(value = "查询当前状态")
    @PostMapping("/queryGasMeter")
    R<Boolean> queryGasMeter(@RequestBody GasMeterVersion gasMeterVersion) {
        return success(this.baseService.queryGasMeter(gasMeterVersion));
    }

    /**
     * 查询版号
     * @param gasMeterVersion
     * @return
     */
    @ApiOperation(value = "查询版号")
    @PostMapping("/queryVersion")
    R<GasMeterVersion> queryVersion(@RequestBody GasMeterVersion gasMeterVersion) {
        return success(this.baseService.queryVersion(gasMeterVersion));
    }

    @Override
    public R<List<GasMeterVersion>> query(GasMeterVersion data) {
        return success(this.baseService.queryVersionEx(data));
    }
}
