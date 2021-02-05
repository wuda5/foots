package com.cdqckj.gmis.installed.controller;

import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.common.domain.code.CodeNotLost;
import com.cdqckj.gmis.common.enums.BizFCode;
import com.cdqckj.gmis.common.utils.BizCodeNewUtil;
import com.cdqckj.gmis.common.utils.BizCodeUtil;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.installed.dto.InstallRecordPageDTO;
import com.cdqckj.gmis.installed.dto.InstallRecordSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallRecordUpdateDTO;
import com.cdqckj.gmis.installed.entity.InstallDetail;
import com.cdqckj.gmis.installed.entity.InstallProcessRecord;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.installed.enumeration.InstallStatus;
import com.cdqckj.gmis.installed.service.InstallDetailService;
import com.cdqckj.gmis.installed.service.InstallProcessRecordService;
import com.cdqckj.gmis.installed.service.InstallRecordService;
import com.cdqckj.gmis.installed.vo.FlowOperCommonVo;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;


/**
 * <p>
 * 前端控制器
 * 报装记录
 * </p>
 *
 * @author tp
 * @date 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/installRecord")
@Api(value = "installRecord", tags = "报装记录")
public class InstallRecordController extends SuperController<InstallRecordService, Long, InstallRecord, InstallRecordPageDTO, InstallRecordSaveDTO, InstallRecordUpdateDTO> {


    @Autowired
    InstallProcessRecordService processRecordService;
//    @Autowired
//    OrderRecordService orderRecordService;
    @Autowired
    GasMeterBizApi gasMeterBizApi;
    @Autowired
    InstallDetailService installDetailService;

    @PostMapping(value = "/queryByNums")
    public     R<List<InstallRecord>> querybyNums(@RequestBody List<String> nums){
        List<InstallRecord> list = this.baseService.list(Wraps.<InstallRecord>lbQ().in(InstallRecord::getInstallNumber, nums));
        return success(list);
    }

    @ApiOperation(value = "根据报装编号查询报装记录", notes = "根据报装编号查询报装记录")
    @PostMapping("/getByInstallNumber")
    public R<InstallRecord> getByInstallNumber(@RequestBody @Validated String installNumber) {
        LbqWrapper<InstallRecord> eq = Wraps.<InstallRecord>lbQ().eq(InstallRecord::getInstallNumber, installNumber);
        InstallRecord one = this.baseService.getOne(eq);
        return success(one);
    }
    /**
     * 1.报装数据保存
     **/
    @Override
    @CodeNotLost
    public R<InstallRecord> handlerSave(InstallRecordSaveDTO model) {
        // .. TO Do 校验 报装人信息
        InstallRecord install = BeanPlusUtil.toBean(model, InstallRecord.class);

        // 构造报装编号
        //String installNum = BizCodeUtil.genTaskFlowCode(BizFCode.BZ, BizCodeUtil.TASK_INSTALL);
        String installNum = BizCodeNewUtil.getInstallRecordCode();
        install.setInstallNumber(installNum)
                .setDataStatus(InstallStatus.WAITE_HANDLE.getCode());// 报装受理，待受理

        // 1.新增报装记录，此时不增加流程记录
        this.baseService.save(install);// 自动会设置相关公司人
        log.info("-->存储报装受理 记录 成功");
        return success(install);
    }


    /**
     * .报装-修改报装状态：分为4种情况：（下面的校验判断），传入所想要修改的状态，代码判断之前的状态是否ok
     * <p>
     * 1.受理资料完成审核 --> 待受理 HANDLE                                    --》？同时需要增加条  已受理状态的 流程记录
     *
     * 2.启动踏勘--》传入 2待踏勘 WAITE_SURVEY，（先是派勘察工单结单后，才启动此）  --** 录入踏勘工单资料时候，已增加 流程记录--**
     * 3.启动设计 -->状态后：3 WAITE_DESIGN -->启动设计 传入 待设计（状态变化前必为:待踏勘 ）
     *  -- 去掉了代收费状态
     * 5.启动施工--》传入 5待施工，（先是派 施工工单结单后，才启动此）              --** 录入施工工单资料时候，已增加 流程记录--**
     *
     * 6.启动验收--》传入 6待验收，（先是派 验收工单结单后 即验收，才启动此）        --** 录入验收工单资料时候，已增加 流程记录--**
     * 8.结单-->传入 结单，没有？代结单这个状态了                              --》？同时需要增加条  结单人状态的 流程记录
     * 9.终止-->传入 9终止                                                --》？同时需要增加条  终止人状态的 流程记录
     *
     */
    @ApiOperation(value = "报装-状态修改", notes = "报装-状态修改")
    @PostMapping("/examin")
    public R<InstallRecord> examin(@RequestBody @Validated FlowOperCommonVo data) {
        InstallRecord record = this.baseService.getById(data.getInstallRecordId());
        BizAssert.notNull(record, "无对应报装记录");

        InstallStatus installStatus = InstallStatus.getByCode(data.getInstallStatus());// 新状态
        Integer oldDataStatus = record.getDataStatus();// 原状态
        record.setDataStatus(data.getInstallStatus());// 设置好新状态
        switch (installStatus) {
            case HANDLE:
                // 1.受理资料完成审核 --> 状态后：HANDLE 1--> 资料审核 传入 已受理（状态变化前必为: 待受理）,设置受理人 --》同时需要增加条  已受理状态的 流程记录
//                BizAssert.isTrue(Objects.equals(oldDataStatus,InstallStatus.WAITE_HANDLE.getCode()),"" +
//                        "报装状态不是处于待受理状态，非法资料完成审核");
                record.setAcceptTime(LocalDateTime.now())
                        .setAcceptUserName(BaseContextHandler.getName())
                        .setAcceptUser(BaseContextHandler.getUserId());
                this.saveInstallProcessRecord(record.getInstallNumber(), InstallStatus.HANDLE, "受理资料完成审核");
                break;
//            case WAITE_DESIGN:
//                // 2.启动设计 -->状态后：WAITE_DESIGN 3-->启动设计 传入 待设计（状态变化前必为:待踏勘 ）
////                BizAssert.isTrue(Objects.equals(oldDataStatus,InstallStatus.WAITE_SURVEY.getCode()),"" +
////                        "报装状态不是处于待踏勘状态，非法启动设计");
//                break;
            case COMPLETE:
                //4.结单-->状态变化前必为:待结单 ，状态后：结单 8     -->验收通过 传入 结单--》同时需要增加条  结单人状态的 流程记录
//                BizAssert.isTrue(Objects.equals(oldDataStatus,InstallStatus.WAITE_COMPLETE.getCode()),
//                        "报装状态不是处于待结单状态，非法结单");
                record.setEndJobUserName(BaseContextHandler.getName())
                        .setEndJobTime(LocalDateTime.now())
                        .setEndJobUserId(BaseContextHandler.getUserId());
                // TODO 还需要更新同步表具信息，先查询施工产生的 施工结果录入结果(--> 可能是对应多个表具的安装结果list ),再由表code 查询meter，后xx
                List<InstallDetail> details = installDetailService.list((Wraps.<InstallDetail>lbQ().eq(InstallDetail::getInstallNumber, record.getInstallNumber())));
                BizAssert.isTrue(CollectionUtils.isNotEmpty(details),"请先确认已经录入施工安装结果信息..否则无法跟新表具信息完结");

                details.forEach(detail->{
                    GasMeter meter = gasMeterBizApi.findGasMeterByCode(detail.getGasMeterCode()).getData();
                    BizAssert.notNull(meter,"无此表具信息...");
                    meter.setGasMeterAddress(detail.getAddressDetail())// 设置详细地址
                            .setMoreGasMeterAddress(""+detail.getAddressDetail())
                            .setInstallNumber(record.getInstallNumber()) // 设置报装编号
                            .setInstallTime(detail.getCreateTime())
                            .setProvinceCode(detail.getProvinceCode())
                            .setCityCode(detail.getCityCode())
                            .setAreaCode(detail.getAreaCode());
                    // TODO 设置用气类型相关？ 调压箱编号？？
                    gasMeterBizApi.updateByCode(meter);
                });


                this.saveInstallProcessRecord(record.getInstallNumber(), InstallStatus.COMPLETE, "完成报装结单");
                break;
            case TERMINATION:
                //4.终止-->状态变化前必为:终止9   -->验收通过 传入 结单--》同时需要增加条  终止人 状态的 流程记录

                record.setStopUserName(BaseContextHandler.getName())
                        .setStopTime(LocalDateTime.now())
                        .setStopUserId(BaseContextHandler.getUserId())
                        .setStopReason(data.getStopReason());

                this.saveInstallProcessRecord(record.getInstallNumber(), InstallStatus.COMPLETE, "完成报装终止");
                break;
            default:
//               throw new BizException("暂不接受此报装状态修改");
        }
        // 修改xxx
        this.baseService.updateById(record);
        return success(record);
    }

    /**
     * 2.报装-受理资料完成审核
     */
    @ApiOperation(value = "报装-受理资料完成审核", notes = "报装-受理资料完成审核")
    @PostMapping("/dataAudit")
    public R<Boolean> audit(@RequestBody @Validated FlowOperCommonVo data) {
        InstallRecord record = this.baseService.getById(data.getInstallRecordId());
        BizAssert.notNull(record, "无对应报装记录");

        // 2. 设置修改此报装记录状态为: 待踏勘，踏勘人等信息，
        record.setDataStatus(InstallStatus.WAITE_SURVEY.getCode())
                .setStepOnUserId(BaseContextHandler.getUserId())
                .setStepOnTime(LocalDateTime.now())
                .setStepOnUserName(BaseContextHandler.getName());
        this.baseService.updateById(record);

        // 3. 注意：新增条流程记录-->受理人相关流程xx，的状态是设置为： 待受理
        this.saveInstallProcessRecord(record.getInstallNumber(), InstallStatus.WAITE_HANDLE, "新增受理人记录，待查勘");

        return success(true);
    }


    /**
     * 99.完结，报装填报结单
     */
    @ApiOperation(value = "完结，报装填报结单", notes = "完结，报装填报结单")
    @PostMapping("/complete")
    public R<Boolean> complete(@RequestBody @Validated FlowOperCommonVo data) {
        InstallRecord record = this.baseService.getById(data.getInstallRecordId());
        BizAssert.notNull(record, "无对应报装记录");
        //1. 校验此报装受理记录 所处是否为正确可以派单状态（待结单，即录入验收资料通过后）
//        BizAssert.equals(record.getDataStatus(), InstallStatus.WAITE_COMPLETE.getCode(), "此报装受理记录不在待结单状态，无法结单");

        // 2. 设置修改此报装记录状态为 完结，结单人等信息
        record.setDataStatus(InstallStatus.COMPLETE.getCode())
                .setEndJobUserId(BaseContextHandler.getUserId())
                .setEndJobTime(LocalDateTime.now())
                .setEndJobUserName(BaseContextHandler.getName());

        this.baseService.updateById(record);

        // 3. 新增条流程记录-->结单人相关流程xx
        this.saveInstallProcessRecord(record.getInstallNumber(), InstallStatus.COMPLETE, "报装记录结单");

        return success(true);
    }


    /**
     * 流程操作步骤保存
     *
     * @param installNumber
     * @param installStatus
     * @param remark
     */
    private void saveInstallProcessRecord(String installNumber, InstallStatus installStatus, String remark) {

        InstallProcessRecord process = new InstallProcessRecord();
        // 自动会设置相关公司人
        process.setInstallNumber(installNumber)
                .setProcessUserId(BaseContextHandler.getUserId())
                .setProcessUserName(BaseContextHandler.getName())
                .setProcessTime(LocalDateTime.now())
                .setProcessState(installStatus.getCode())
                .setProcessDesc(remark);

        processRecordService.save(process);

    }
}
