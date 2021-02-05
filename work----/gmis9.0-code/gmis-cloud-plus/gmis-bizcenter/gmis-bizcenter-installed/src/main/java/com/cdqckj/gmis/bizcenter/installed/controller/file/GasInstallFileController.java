package com.cdqckj.gmis.bizcenter.installed.controller.file;

import com.cdqckj.gmis.base.R;

import com.cdqckj.gmis.installed.GasInstallFileBizApi;
import com.cdqckj.gmis.installed.dto.GasInstallFileSaveDTO;
import com.cdqckj.gmis.installed.dto.GasInstallFileUpdateDTO;
import com.cdqckj.gmis.installed.entity.GasInstallFile;
import com.cdqckj.gmis.operation.entity.OrderJobFile;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * <p>
 * 前端控制器
 * 报装材料
 * </p>
 *
 * @author tp
 * @date 2020-07-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/install/installFile")
@Api(value = "GasInstallFile", tags = "报装材料")
//@PreAuth(replace = "gasInstallFile:")
public class GasInstallFileController {

    @Autowired
    public GasInstallFileBizApi gasInstallFileRecordBizApi;

    /**
     * 保存或修改 报装材料信息
     * @param saveDTO
     * @return
     */
    @ApiOperation(value = "保存报装材料信息")
    @PostMapping("/saveList")
    public  R<List<GasInstallFile>> saveList(@RequestBody List<GasInstallFileSaveDTO> saveDTO){
        String installNumber = saveDTO.get(0).getInstallNumber();
        // 先删除原有的xx
        gasInstallFileRecordBizApi.delete(new GasInstallFile().setInstallNumber(installNumber));
        return gasInstallFileRecordBizApi.saveList(saveDTO);
    }



    /**
     * 根据报装编号获取材料信息
     * @return
     */
    @ApiOperation(value = "根据报装编号获取报装材料信息")
    @GetMapping("/findFilesByInstallNumber")
    public  R<List<GasInstallFile>> findFilesByInstallNumber(@RequestParam(name = "installNumber") String installNumber){
        GasInstallFile file =new GasInstallFile();
        file.setInstallNumber(installNumber);
        return gasInstallFileRecordBizApi.query(file);
    }
}
