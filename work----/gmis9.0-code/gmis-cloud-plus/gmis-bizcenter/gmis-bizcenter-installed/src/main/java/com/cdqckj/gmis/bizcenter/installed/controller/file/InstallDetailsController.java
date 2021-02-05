package com.cdqckj.gmis.bizcenter.installed.controller.file;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import com.cdqckj.gmis.archive.GasMeterBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.utils.ExcelCascadeSelectorDTO;
import com.cdqckj.gmis.common.utils.ExportUtil;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.installed.InstallDetailBizApi;
import com.cdqckj.gmis.installed.InstallRecordApi;
import com.cdqckj.gmis.installed.dto.InstallDetailPageDTO;
import com.cdqckj.gmis.installed.dto.InstallDetailSaveDTO;
import com.cdqckj.gmis.installed.entity.InstallDetail;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.operateparam.CommunityBizApi;
import com.cdqckj.gmis.operateparam.StreetBizApi;
import com.cdqckj.gmis.operateparam.entity.Street;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.BizAssert;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Validated
@RestController
@RequestMapping("/install/details")
@Api(value = "/install/details", tags = "报装表具安装结果信息")
public class InstallDetailsController {
    @Autowired
    private InstallDetailBizApi installDetailBizApi;

    @Autowired
    private InstallRecordApi installRecordApi;

    //    @Autowired
//    private UserApi userApi;
//    @Autowired
//    private InstallProcessRecordApi installProcessRecordApi;
//
//    @Autowired
//    private InstallDetailBizApi installDetailBizApi;
//    @Autowired
//    private AreaBizApi areaBizApi;
    @Autowired
    private StreetBizApi streetBizApi;
    @Autowired
    private CommunityBizApi communityBizApi;

//    @Autowired
//    private UseGasTypeBizApi useGasTypeBizApi;

    @Autowired
    private GasMeterBizApi gasMeterBizApi;

//    @Autowired
//    private RegionDictDataComponent regionDictDataComponent;

    @ApiOperation(value = "下载导出 报装安装表具地址结果，下拉框级联模板")
    @PostMapping("/exportInstallDetailTemplate")
    public void exportCascadeTemplate(HttpServletRequest request, HttpServletResponse httpResponse) throws Exception {
//        //用气类型
//        List<String> useGasTypeList = establishAccountSelector.getUseGasTypeList();
//        TreeSet<String> useGasTypeSet = useGasTypeList.stream().collect(Collectors.toCollection(TreeSet::new));
//        ExcelCascadeSelectorDTO useGasTypeCascadeSelector =new ExcelCascadeSelectorDTO("用气类型",'O',0, useGasTypeSet, "useGasType");
        PageParams<InstallDetailPageDTO> params=new PageParams<>();
        //组装一组级联参数 省、市、区、街道、小区
        List<ExcelCascadeSelectorDTO> cascadeSelectors= new ArrayList<>();
        //级联参数
        params.setCascadeSelectors(cascadeSelectors);
        params.setTargetSheetIndex(0);

        // feign文件下载
//        Response response = businessTemporaryBizApi.exportCascadeTemplate(params);
        Response response = installDetailBizApi.exportInstallDetailTemplate(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, "报装安装结果模板");
        ExportUtil.exportExcel(response, httpResponse, fileName);
    }



    @ApiOperation(value = "报装 编号 查询--》安装明细结果list", notes = "报装 编号 查询--》安装明细结果list")
    @GetMapping(value = "/queryInstallDetail")
    public R<List<InstallDetail>> queryInstallDetail(@RequestParam(name = "installNumber") String installNumber) {
        return installDetailBizApi.query(new InstallDetail().setInstallNumber(installNumber));
    }


    @ApiOperation(value = "导入Excel 报装表具地址 安装结果xxx")
    @PostMapping(value = "/importInstallDetail")
    R<Boolean> importExcelBackList(@RequestPart(value = "file") MultipartFile simpleFile) throws Exception{
        // TODO 先校验 excel 表内容大致 基本的合法性
        // excel 解析处理
        List<InstallDetail> detailList = installDetailBizApi.importExcelBackList(simpleFile).getData();

//        // 具体xx，如果表具code有重复，是随机去重，还是直接异常？，
        if (detailList.stream().map(InstallDetail::getInstallNumber).distinct().count()!=1){
            BizAssert.fail("只能对应有一个报装编号的处理...");
        }
        // 检查是否存在报装记录
        InstallRecord install = Optional.ofNullable(installRecordApi.getByInstallNumber(detailList.get(0).getInstallNumber())
                .getData()).orElseThrow(() -> new BizException("请输入正确的报装编号"));

        detailList.forEach(x->{

            selfHandle(x);});
        return R.success();

    }


    private void selfHandle(InstallDetail detail) {
        // ---自己的处理---
        // 保存，详细地址拷贝
        InstallDetailSaveDTO save = BeanPlusUtil.toBean(detail, InstallDetailSaveDTO.class);
        // TODO streetName 查询上级区域信息,就算无对应街区--构造个空对象
        Street st = Optional.ofNullable(streetBizApi.queryOne(new Street().setStreetName(detail.getStreetName())).getData())
                .orElseGet(Street::new);
//            communityBizApi.query()
//            BizAssert.notNull(st,"无对应街区xx");
        // TODO 查询表具档案及相关厂家，气表型号，等信息
        String gasCode = detail.getGasMeterCode();
        GasMeter mt = gasMeterBizApi.findGasMeterByCode(gasCode).getData();
        BizAssert.notNull(mt,"档案无对应的气表编号");
        // TODO 调压箱编号？用气类型

        save.setGasMeterName("")
                .setGasMeterVersionId(mt.getGasMeterVersionId())
//                .setGasMeterVersionName(mt.getGasMeterVersionName())
//                    .setGasMeterModelId(mt.getGasMeterModelId())
//                .setGasMeterModelName(mt.getGasMeterModelName())
                .setUseGasTypeCode(mt.getUseGasTypeCode()).setUseGasTypeName(mt.getUseGasTypeName())//？用气类型？？？？
                .setGasMeterFactoryId(mt.getGasMeterFactoryId())
//                .setGasMeterFactoryName(mt.getGasMeterFactoryName())

                .setProvinceCode(st.getProvinceCode()).setProvinceName(st.getProvinceName())
                .setCityCode(st.getCityCode()).setCityName(st.getCityName())
                .setAreaCode(st.getAreaCode()).setAreaName(st.getAreaName())
                .setStreetId(st.getId()).setStreetName(detail.getStreetName());

        installDetailBizApi.save(save);

    }



}
