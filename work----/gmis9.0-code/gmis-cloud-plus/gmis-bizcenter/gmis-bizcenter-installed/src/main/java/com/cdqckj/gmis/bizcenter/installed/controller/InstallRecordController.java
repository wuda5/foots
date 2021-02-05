package com.cdqckj.gmis.bizcenter.installed.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.installed.InstallAcceptApi;
import com.cdqckj.gmis.installed.InstallDetailBizApi;
import com.cdqckj.gmis.installed.InstallProcessRecordApi;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.installed.dto.*;
import com.cdqckj.gmis.installed.entity.InstallAccept;
import com.cdqckj.gmis.installed.entity.InstallDetail;
import com.cdqckj.gmis.installed.entity.InstallProcessRecord;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.installed.vo.FlowOperCommonVo;
import com.cdqckj.gmis.log.annotation.SysLog;
import com.cdqckj.gmis.operateparam.StreetBizApi;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/install/record")
@Api(value = "record", tags = "报装计划")
public class InstallRecordController {
    @Autowired
    private InstallRecordApi installRecordApi;
    @Autowired
    private InstallProcessRecordApi installProcessRecordApi;
    @Autowired
    private OrderRecordApi orderRecordApi;
    @Autowired
    private InstallDetailBizApi installDetailBizApi;
    @Autowired
    private InstallAcceptApi installAcceptApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private StreetBizApi streetBizApi;

    @ApiOperation(value = "保存报装记录信息", notes = "保存报装记录信息")
    @PostMapping(value = "/save")
    public R<Boolean> save(@RequestBody InstallRecordUpdateDTO data) {
        if (null == data.getId()) {
            // 保存
            installRecordApi.save(BeanPlusUtil.toBean(data, InstallRecordSaveDTO.class));
        } else {
            // 修改
            installRecordApi.update(data);
        }

        return R.success(true);
    }

//    @ApiOperation(value = "修改报装记录信息", notes = "保存报装记录信息")
//    @PostMapping(value = "/update")
//    public R<Boolean> update(@RequestBody InstallRecordUpdateDTO dto) {
//        installRecordApi.update(dto);
//        return R.success(true);
//    }

    /**
     * 分页查询报装记录信息
     *
     * @param params
     * @return
     */
    @ApiOperation(value = "分页查询报装记录信息", notes = "分页查询报装记录信息")
    @PostMapping(value = "/page")
    @SysLog(value = "'分页列表查询:第' + #params?.current + '页, 显示' + #params?.size + '行'", response = false)
    public R<Page<InstallRecord>> page(@RequestBody @Validated PageParams<InstallRecordPageDTO> params) {
        return installRecordApi.page(params);
    }

    @ApiOperation(value = "报装-状态修改:1.受理审核 2.勘察 3.启动设计 4.施工 5.验收 6.结单报装", notes = "报装-几大步骤状态修改:1.报装受理审核 2.启动设计 3.验收通过 4.结单报装")
    @PostMapping("/examin")
    public R<InstallRecord> examin(@RequestBody @Validated FlowOperCommonVo data) {
        return installRecordApi.examin(data);
    }

    /***************************------------------------------------------***********************/
    // 右侧根据报装 编号 查询的其所有的流程记录
    @ApiOperation(value = "报装 编号 查询的其所有的流程记录", notes = "报装 编号 查询的其所有的流程记录")
    @GetMapping(value = "/queryProcess")
    public R<List<InstallProcessRecord>> queryProcess(@RequestParam(name = "installNumber") String installNumber) {

        return installProcessRecordApi.query(new InstallProcessRecord().setInstallNumber(installNumber));
    }

    @ApiOperation(value = "报装 编号 查询--》安装明细结果", notes = "报装 编号 查询--》安装明细结果")
    @GetMapping(value = "/queryInstallDetail")
    public R<InstallDetail> queryInstallDetail(@RequestParam(name = "installNumber") String installNumber) {
        return installDetailBizApi.queryOne(new InstallDetail().setInstallNumber(installNumber));
    }
    // 录入 施工资料的 安装明细结果资料（表具信息）--特殊，需要更改报装记录状态 为： 待验收
    @ApiOperation(value = "录入 施工资料的 安装明细结果资料（表具信息", notes = "录入 施工资料的 安装明细结果资料（表具信息")
    @PostMapping(value = "/saveInstallDetail")
    public R<Boolean> installDetail(@RequestBody @Validated InstallDetailCommonDTO data) {



        if (null == data.getId()) {
            // 保存
            InstallDetailSaveDTO save = BeanPlusUtil.toBean(data, InstallDetailSaveDTO.class);
            // TODO streetId 查询上级区域信息
            Street st = streetBizApi.get(data.getStreetId()).getData();
            // TODO 查询表具档案及相关厂家，气表型号，等信息
            String gasCode = data.getGasMeterCode();
            GasMeter mt = gasMeterBizApi.findGasMeterByCode(gasCode).getData();
            BizAssert.notNull(mt,"档案无对应的气表编号");
            save.setGasMeterName("")
                    .setGasMeterVersionId(mt.getGasMeterVersionId())
//                    .setGasMeterVersionName(mt.getGasMeterVersionName())
//                    .setGasMeterModelId(mt.getGasMeterModelId())
                  /*  .setGasMeterModelName(mt.getGasMeterModelName())*/
                    .setUseGasTypeCode(mt.getUseGasTypeCode()).setUseGasTypeName(mt.getUseGasTypeName())
                    .setGasMeterFactoryId(mt.getGasMeterFactoryId())
                  /*  .setGasMeterFactoryName(mt.getGasMeterFactoryName())*/

            .setProvinceCode(st.getProvinceCode()).setProvinceName(st.getProvinceName())
            .setCityCode(st.getCityCode()).setCityName(st.getCityName())
            .setAreaCode(st.getAreaCode()).setAreaName(st.getAreaName());

            installDetailBizApi.save(save);
        } else {
            // 修改
            InstallDetailUpdateDTO up = BeanPlusUtil.toBean(data, InstallDetailUpdateDTO.class);
            installDetailBizApi.update(up);
        }


        return R.success(true);
    }

    @ApiOperation(value = "录入 验收结果资料信息", notes = "录入 验收结果资料信息")
    @PostMapping(value = "/saveInstallAccept")
    public R<Boolean> installAccept(@RequestBody @Validated InstallAcceptUpdateDTO data) {
        data.setAcceptTime(LocalDateTime.now());
        if (null == data.getId()) {
            // 保存
            installAcceptApi.save(BeanPlusUtil.toBean(data, InstallAcceptSaveDTO.class));
        } else {
            // 修改
            installAcceptApi.update(data);
        }

        return R.success(true);
    }
    @ApiOperation(value = "报装 编号 查询--》验收明细结果", notes = "报装 编号 查询--》验收 明细结果")
    @GetMapping(value = "/queryAccept")
    public R<InstallAccept> queryAccept(@RequestParam(name = "installNumber") String installNumber) {
        return installAcceptApi.queryOne(new InstallAccept().setInstallNumber(installNumber));
    }
}
