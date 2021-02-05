package com.cdqckj.gmis.installed;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.hystrix.HystrixTimeoutFallbackFactory;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.installed.dto.GasInstallFileUpdateDTO;
import com.cdqckj.gmis.installed.dto.InstallRecordPageDTO;
import com.cdqckj.gmis.installed.dto.InstallRecordSaveDTO;
import com.cdqckj.gmis.installed.dto.InstallRecordUpdateDTO;
import com.cdqckj.gmis.installed.entity.GasInstallFile;
import com.cdqckj.gmis.installed.entity.InstallRecord;
import com.cdqckj.gmis.installed.vo.FlowOperCommonVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "${gmis.feign.installed-server:gmis-installed-server}", fallbackFactory = HystrixTimeoutFallbackFactory.class
        , path = "/installRecord", qualifier = "installRecordApi")
public interface InstallRecordApi {

    @PostMapping(value = "/getById")
    R<InstallRecord> getById(@RequestParam("id") Long id);

    @ApiOperation(value = "根据报装编号查询报装记录", notes = "根据报装编号查询报装记录")
    @PostMapping("/getByInstallNumber")
    R<InstallRecord> getByInstallNumber(@RequestBody @Validated String installNumber) ;

    @PostMapping(value = "/queryList")
    R<List<InstallRecord>> queryList(@RequestParam("ids[]") List<Long> ids);

    @PostMapping(value = "/queryByNums")
    R<List<InstallRecord>> querybyNums(@RequestBody List<String> nums);


    @PostMapping("/query")
    R<List<InstallRecord>> query(@RequestBody InstallRecord query);

    /** 1.新增报装记录
     * */
    @PostMapping
    R<InstallRecord> save(@RequestBody InstallRecordSaveDTO dto);

    @PutMapping
    R<InstallRecord> update(@RequestBody InstallRecordUpdateDTO updateDTO);


//    /**
//     * 2.报装-受理资料完成审核
//     */
//    @ApiOperation(value = "报装-受理资料完成审核", notes = "报装-受理资料完成审核")
//    @PostMapping("/dataAudit")
//    R<Boolean> dataAudit(@RequestBody @Validated FlowOperCommonVo data) ;
//
//    /**
//     * 99.完结，报装填报结单
//     */
//    @ApiOperation(value = "完结，报装填报结单", notes = "完结，报装填报结单")
//    @PostMapping("/complete")
//    R<Boolean> complete(@RequestBody @Validated FlowOperCommonVo data);

    @ApiOperation(value = "报装-几大步骤状态修改", notes = "报装-几大步骤状态修改")
    @PostMapping("/examin")
    R<InstallRecord> examin(@RequestBody @Validated FlowOperCommonVo data);

    @PostMapping(value = "/page")
    R<Page<InstallRecord>> page(@RequestBody @Validated PageParams<InstallRecordPageDTO> params);
}
