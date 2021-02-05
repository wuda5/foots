package com.cdqckj.gmis.bizcenter.installed.controller.file;

import com.cdqckj.gmis.authority.entity.auth.User;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.installed.InstallDesignApi;
import com.cdqckj.gmis.installed.InstallProcessRecordApi;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.installed.dto.InstallDesignSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallDesignUpdateDTO;
import com.cdqckj.gmis.installed.dto.InstallProcessRecordSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallRecordUpdateDTO;
import com.cdqckj.gmis.installed.entity.InstallAccept;
import com.cdqckj.gmis.installed.entity.InstallDesign;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.installed.enumeration.InstallStatus;
import com.cdqckj.gmis.oauth.api.UserApi;
import com.cdqckj.gmis.operation.OrderJobFileBizApi;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.entity.OrderJobFile;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operation.enumeration.InstallOrderType;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/install/design")
@Api(value = "/install/design", tags = "报装设计信息")
public class InstallDesignController {

    @Autowired
    private InstallDesignApi installDesignApi;
    @Autowired
    private InstallRecordApi installRecordApi;
    @Autowired
    private OrderRecordApi orderRecordApi;
    @Autowired
    private OrderJobFileBizApi orderJobFileBizApi;
    @Autowired
    private UserApi userApi;
    @Autowired
    private InstallProcessRecordApi installProcessRecordApi;
//    @PostMapping("/desigin")
//    public R<InstallDesign> addDesign(@RequestParam(value = "designUrl") String designUrl,@RequestParam(value = "id") Long id,@RequestBody InstallDesignSaveDTO installDesignSaveDTO){
//        InstallRecord installRecord = installRecordApi.getById(id).getData();
//        installDesignSaveDTO.setInstallNumber(installRecord.getInstallNumber());
//        installDesignSaveDTO.setSketchUser(installRecord.getStepOnUserId());
//        List<OrderRecord> query = orderRecordApi.query(new OrderRecord().setBusinessOrderNumber(installRecord.getInstallNumber()).setWorkOrderType(InstallOrderType.SURVEY.getCode())).getData();
//        log.info("看sql");
//        OrderRecord orderRecord = query.get(0);
//        String sketchUrl=null;
//        List<OrderJobFile> data = orderJobFileBizApi.query(new OrderJobFile().setJobId(orderRecord.getId())).getData();
//        for (OrderJobFile orderJobFile : data) {
//            sketchUrl=orderJobFile.getMaterialUrl()+",";
//        }
//        sketchUrl=sketchUrl.substring(0,sketchUrl.length()-1);
//        installDesignSaveDTO.setSketchUrl(sketchUrl);
//        installDesignSaveDTO.setDesignUrl(designUrl);
//        R<InstallDesign> save = installDesignApi.save(installDesignSaveDTO);
//        if(save.getIsSuccess()){
//            installRecordApi.update(new InstallRecordUpdateDTO().setId(id).setDataStatus(InstallStatus.WAITE_DESIGN.getCode()));
//            return save;
//        }
//        return R.fail("保存报装资料失败");
//    }
    @PostMapping("/addDesign")
    public R<InstallDesign> saveInstallDesign( @RequestBody InstallDesignUpdateDTO installDesignUpdateDTO){
//    public R<InstallDesign> saveInstallDesign(@RequestParam(value = "designUrl") String designUrl, @RequestBody InstallDesignUpdateDTO installDesignUpdateDTO){
        InstallRecord installRecord = installRecordApi.getByInstallNumber(installDesignUpdateDTO.getInstallNumber()).getData();

        installRecord.setDesignUserId(installDesignUpdateDTO.getDesignUser());
        //设计人姓名 查出来通过用户id 查出来
//        User user = userApi.getById(installDesignUpdateDTO.getDesignUser()).getData();
        installRecord.setDesignUserName(installDesignUpdateDTO.getDesignUserName());
        //@todo  新增
        if (null == installDesignUpdateDTO.getId()) {
            R<InstallDesign> save = installDesignApi.save(BeanPlusUtil.toBean(installDesignUpdateDTO, InstallDesignSaveDTO.class));
            if (save.getIsSuccess()) {
                updateInstallRecord(installRecord);
                return save;
            }
            return R.fail("新增失败");
        }else {
            /*@TODO  修改 */
            R<InstallDesign> update = installDesignApi.update(installDesignUpdateDTO);
            if (update.getIsSuccess()) {
                updateInstallRecord(installRecord);
                return update;
            }
        }
        return R.fail("修改失败");
    }

    @ApiOperation(value = "报装 编号 查询--》设计结果", notes = "报装 编号 查询--设计结果")
    @GetMapping(value = "/queryDesign")
    public R<InstallDesign> queryDesign(@RequestParam(name = "installNumber") String installNumber) {
        return installDesignApi.queryOne(new InstallDesign().setInstallNumber(installNumber));
    }

    private void updateInstallRecord( InstallRecord installRecord){
        //设计时间代传
        installRecord.setDataStatus(InstallStatus.WAITE_DESIGN.getCode());
        InstallRecordUpdateDTO updateDTO=new InstallRecordUpdateDTO();
        BeanPlusUtil.toBean(installRecord,updateDTO.getClass());
        R<InstallRecord> update = installRecordApi.update(updateDTO);
        if(update.getIsSuccess()){
            InstallProcessRecordSaveDTO installProcessRecordSaveDTO=new InstallProcessRecordSaveDTO();
            installProcessRecordSaveDTO.setInstallNumber(installRecord.getInstallNumber());
            installProcessRecordSaveDTO.setProcessUserId(installRecord.getDesignUserId());
            installProcessRecordSaveDTO.setProcessUserName(installRecord.getDesignUserName());
            installProcessRecordSaveDTO.setProcessState(installRecord.getDataStatus());
            installProcessRecordApi.save(installProcessRecordSaveDTO);
        }
    }
}
