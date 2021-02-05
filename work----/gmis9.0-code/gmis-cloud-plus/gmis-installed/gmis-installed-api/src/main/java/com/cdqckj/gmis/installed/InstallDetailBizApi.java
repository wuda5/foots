package com.cdqckj.gmis.installed;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;

import com.cdqckj.gmis.installed.dto.GasInstallFileSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallDetailPageDTO;
import com.cdqckj.gmis.installed.dto.InstallDetailSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallDetailUpdateDTO;
import com.cdqckj.gmis.installed.entity.GasInstallFile;
import com.cdqckj.gmis.installed.entity.InstallDetail;
import com.cdqckj.gmis.installed.entity.InstallProcessRecord;
import com.cdqckj.gmis.log.annotation.SysLog;
import feign.Response;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 65427
 */
@FeignClient(name = "${gmis.feign.installed-server:gmis-installed-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/installDetail", qualifier = "installDetailBizApi"
)
public interface InstallDetailBizApi {

    /**
     * 导入安装明细
     */
//    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    R<Boolean> importExcel(@RequestPart(value = "file") MultipartFile file) throws Exception;


    /**
     * /exportCascadeTemplate 这个是poiController 的
     * @param params
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "下拉框级联导入 报装表具地址安装结果 模板")
    @PostMapping(value = "/exportCascadeTemplate")
    Response exportInstallDetailTemplate(@RequestBody @Validated PageParams<InstallDetailPageDTO> params) throws Exception;

    @ApiOperation(value = "导入Excel并返回实体集合对象(为了在聚会服务中处理),注意加上consumes = MediaType.MULTIPART_FORM_DATA_VALUE 才ok ")
    @PostMapping(value = "/importBackList",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
     R<List<InstallDetail>> importExcelBackList(@RequestPart(value = "file") MultipartFile simpleFile) throws Exception;


    /**
     * 分页查询
     * 通过报装编号查询或者其他条件
     * @param params
     * @return
     */
    @PostMapping(value = "/page")
    R<Page<InstallDetail>> page(@RequestBody @Validated PageParams<InstallDetailPageDTO> params);

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    R<Boolean> delete(@RequestParam("ids[]") List<Long> ids);

    /**
     * 保存
     *
     * @param params
     * @return
     */
    @PostMapping
    R<InstallDetail> save(@RequestBody @Validated InstallDetailSaveDTO params);

    /**
     * 批量查询
     *
     * @param data 批量查询
     * @return 查询结果
     */
    @ApiOperation(value = "批量查询", notes = "批量查询")
    @PostMapping("/query")
    public R<List<InstallDetail>> query(@RequestBody InstallDetail data);

    @PostMapping("/queryOne")
    R<InstallDetail> queryOne(@RequestBody InstallDetail query);

    @PostMapping("/saveList")
    R<List<InstallDetail>> saveList(@RequestBody List<InstallDetailSaveDTO> saveDTO);

    /**
     * 修改
     *
     * @param params
     * @return
     */
    @PutMapping
    R<InstallDetail> update(@RequestBody @Validated InstallDetailUpdateDTO params);

    /**
     * 组合框导入开户模板
     * @param params
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "组合框导入开户模板")
    @PostMapping(value = "/exportCombobox")
    public Response exportCombobox(@RequestBody @Validated PageParams<InstallDetailPageDTO> params) throws Exception;

}
