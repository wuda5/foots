package com.cdqckj.gmis.operationcenter.app.installed.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.installed.InstallDetailBizApi;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.installed.dto.InstallDetailCommonDTO;
import com.cdqckj.gmis.installed.dto.InstallDetailSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallDetailUpdateDTO;
import com.cdqckj.gmis.installed.entity.InstallAccept;
import com.cdqckj.gmis.installed.entity.InstallDetail;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.installed.vo.WorkOrderOperCommonVo;
import com.cdqckj.gmis.operateparam.StreetBizApi;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.operation.OrderRecordApi;
import com.cdqckj.gmis.operation.dto.OrderRecordPageDTO;
import com.cdqckj.gmis.operation.dto.OrderRecordUpdateDTO;
import com.cdqckj.gmis.operation.entity.OrderRecord;
import com.cdqckj.gmis.operationcenter.app.installed.dto.InstallDetailJobFileDto;
import com.cdqckj.gmis.operationcenter.app.installed.dto.QuerySelfOrderDto;
import com.cdqckj.gmis.operationcenter.app.installed.dto.WorkOrderAppointVo;
import com.cdqckj.gmis.operationcenter.app.installed.service.AppInstallOrderJobFileService;
import com.cdqckj.gmis.operationcenter.app.installed.vo.OrderInstallVo;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * <p>
 * app报装 前端控制器
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/appInstall/order")
@Api(value = "data", tags = "app报装")
public class AppInstallRecordController {

    @Autowired
    private InstallRecordApi installRecordApi;

    @Autowired
    private OrderRecordApi orderRecordApi;
    @Autowired
    private StreetBizApi streetBizApi;
    @Autowired
    private GasMeterBizApi gasMeterBizApi;
    @Autowired
    private InstallDetailBizApi installDetailBizApi;

    @Autowired
    private AppInstallOrderJobFileService appInstallOrderJobFileService;


//    /**
//     * 踏勘，施工，验收--> 拒单
//     */
//    @ApiOperation(value = " 踏勘，施工，验收  拒单", notes = " 踏勘，施工，验收  拒单")
//    @PostMapping("/rejectOrder")
//    public R<Boolean> rejectOrder(@RequestBody @Validated WorkOrderOperCommonVo data) {
//
////        InstallOrderType installOrderType = Optional.ofNullable(InstallOrderType.get(data.getWorkOrderType())).orElseThrow(() -> new BizException("参数传入的报装工单类型非法"));
////        OrderRecord order = Optional.ofNullable(orderRecordService.getById(data.getWorkOrderId())).orElseThrow(() -> new BizException("无此工单"));
////        // 设置该 xx 工单的状态为已接单，接收人，
//        OrderRecord orderRecord = orderRecordApi.getById(data.getWorkOrderId()).getData();
//
//        R<Boolean> r = orderRecordApi.refuseOrder(orderRecord.setRejectDesc(data.getRemark()));
//
//        return r;
//    }
//
//    /**
//     * 设置预约时间
//     */
//    @ApiOperation(value = "设置预约时间", notes = " 设置预约时间")
//    @PostMapping("/setAppointTime")
//    public R<Boolean> rejectOrder(@RequestBody @Validated WorkOrderAppointVo data) {
//        //        // 设置该 xx 工单的状态为已接单，接收人，
//        OrderRecord orderRecord = orderRecordApi.getById(data.getWorkOrderId()).getData();
//        R<OrderRecord> update = orderRecordApi.update(BeanPlusUtil.toBean(orderRecord, OrderRecordUpdateDTO.class)
//                .setBookOperationTime(data.getBookOperationTime()));
//
//        return R.success(true);
//    }

    @ApiOperation(value = "报装 编号 查询--》安装明细结果", notes = "报装 编号 查询--》安装明细结果")
    @GetMapping(value = "/queryInstallDetail")
    public R<InstallDetail> queryInstallDetail(@RequestParam(name = "installNumber") String installNumber) {
        return installDetailBizApi.queryOne(new InstallDetail().setInstallNumber(installNumber));
    }



    // 录入 施工资料的 安装明细结果资料（表具信息）--特殊
    @ApiOperation(value = "录入 施工资料的 安装明细结果资料（表具信息", notes = "录入 施工资料的 安装明细结果资料（表具信息")
    @PostMapping(value = "/saveInstallDetail")
    public R<Boolean> installDetail(@RequestBody @Validated InstallDetailCommonDTO data) {
        // 1.修改保存 安装结果信息
        this.saveDetail(data);
        return R.success(true);
    }
    private void saveDetail(InstallDetailCommonDTO detailCommonDTO) {
        // 保存
        InstallDetailSaveDTO save = BeanPlusUtil.toBean(detailCommonDTO, InstallDetailSaveDTO.class);
        // TODO streetId 查询上级区域信息
        Street st = streetBizApi.get(detailCommonDTO.getStreetId()).getData();
        // TODO 查询表具档案及相关厂家，气表型号，等信息
        String gasCode = detailCommonDTO.getGasMeterCode();
        GasMeter mt = gasMeterBizApi.findGasMeterByCode(gasCode).getData();
        BizAssert.notNull(mt,"档案无对应的气表编号");
        // 用气类型是前端输入的，气表档案暂时没有的，后面再结单时，需要同步过去的（还要同步 地址，区域信息，用气类型，调压箱，表具基本信息不需要xx）
        //
        save.setGasMeterName("")
                .setGasMeterVersionId(mt.getGasMeterVersionId())
//                .setGasMeterVersionName(mt.getGasMeterVersionName())
//                    .setGasMeterModelId(mt.getGasMeterModelId())
//                .setGasMeterModelName(mt.getGasMeterModelName())
//                .setUseGasTypeCode(mt.getUseGasTypeCode()).setUseGasTypeName(mt.getUseGasTypeName())
                .setGasMeterFactoryId(mt.getGasMeterFactoryId())
//                .setGasMeterFactoryName(mt.getGasMeterFactoryName())

                .setProvinceCode(st.getProvinceCode()).setProvinceName(st.getProvinceName())
                .setCityCode(st.getCityCode()).setCityName(st.getCityName())
                .setAreaCode(st.getAreaCode()).setAreaName(st.getAreaName());

        if (null == detailCommonDTO.getId()) {

            installDetailBizApi.save(save);
        } else {
            // 修改
            InstallDetailUpdateDTO up = BeanPlusUtil.toBean(save, InstallDetailUpdateDTO.class);
            installDetailBizApi.update(up.setId(detailCommonDTO.getId()));
        }
    }

}
