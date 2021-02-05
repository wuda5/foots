package com.cdqckj.gmis.biztemporary.controller;

import com.cdqckj.gmis.base.DataStatusEnum;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordPageDTO;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordSaveDTO;
import com.cdqckj.gmis.biztemporary.dto.SupplementGasRecordUpdateDTO;
import com.cdqckj.gmis.biztemporary.entity.SupplementGasRecord;
import com.cdqckj.gmis.biztemporary.enums.RepGasStatusEnum;
import com.cdqckj.gmis.biztemporary.service.SupplementGasRecordService;
import com.cdqckj.gmis.biztemporary.vo.SupplementGasRecordVO;
import com.cdqckj.gmis.database.mybatis.conditions.Wraps;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.exception.BizException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;


/**
 * <p>
 * 前端控制器
 * 补气记录
 * </p>
 *
 * @author gmis
 * @date 2020-12-09
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/supplementGasRecord")
@Api(value = "SupplementGasRecord", tags = "补气记录")
//@PreAuth(replace = "supplementGasRecord:")
public class SupplementGasRecordController extends SuperController<SupplementGasRecordService, Long, SupplementGasRecord, SupplementGasRecordPageDTO, SupplementGasRecordSaveDTO, SupplementGasRecordUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list) {
        List<SupplementGasRecord> supplementGasRecordList = list.stream().map((map) -> {
            SupplementGasRecord supplementGasRecord = SupplementGasRecord.builder().build();
            //TODO 请在这里完成转换
            return supplementGasRecord;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(supplementGasRecordList));
    }

    /**
     * 查询未完成的数据
     *
     * @param gasMeterCode 气表编号
     * @return 补气记录
     */
    @ApiOperation("查询状态为未完成的补气记录")
    @GetMapping("/queryUnfinishedRecord")
    public R<SupplementGasRecord> queryUnfinishedRecord(@RequestParam("gasMeterCode") String gasMeterCode) {
        LbqWrapper<SupplementGasRecord> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(SupplementGasRecord::getGasMeterCode, gasMeterCode)
                .ne(SupplementGasRecord::getRepGasStatus, RepGasStatusEnum.REP_GAS_SUCCESS.getCode())
                .eq(SupplementGasRecord::getDataStatus, DataStatusEnum.NORMAL.getValue());
        return R.success(baseService.getOne(lbqWrapper));
    }

    /**
     * 统计补气次数
     *
     * @param gasMeterCode 表具编号
     * @return 补气次数
     */
    @GetMapping("/countCardRep")
    public R<Integer> countCardRep(@RequestParam(value = "gasMeterCode") String gasMeterCode) {
        LbqWrapper<SupplementGasRecord> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(SupplementGasRecord::getGasMeterCode, gasMeterCode)
                .eq(SupplementGasRecord::getDataStatus, DataStatusEnum.NORMAL.getValue());
        return R.success(baseService.count(lbqWrapper));
    }

    /**
     * 补气流程中更新数据状态为待上表
     *
     * @param gasMeterCode 表具编号
     * @return 更新后补气实体对象
     */
    @PostMapping("updateStatusAfterOperate")
    public R<SupplementGasRecord> updateStatusAfterOperate(@RequestBody String gasMeterCode) {
        LbqWrapper<SupplementGasRecord> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(SupplementGasRecord::getGasMeterCode, gasMeterCode)
                .eq(SupplementGasRecord::getRepGasStatus, RepGasStatusEnum.WAIT_OPERATE.getCode())
                .eq(SupplementGasRecord::getDataStatus, DataStatusEnum.NORMAL.getValue());
        SupplementGasRecord one = baseService.getOne(lbqWrapper);
        if (Objects.isNull(one)) {
            throw new BizException("未查询到待操作的补气记录");
        }
        one.setRepGasStatus(RepGasStatusEnum.WAIT_TO_METER.getCode());
        baseService.updateById(one);
        return R.success(one);
    }

    /**
     * 补气操作流程中更新数据状态为SUCCESS
     *
     * @param gasMeterCode 表具编号
     * @return 更新后补气实体对象
     */
    @PostMapping("updateStatusToSuccess")
    public R<SupplementGasRecord> updateStatusToSuccess(@RequestBody String gasMeterCode) {
        LbqWrapper<SupplementGasRecord> lbqWrapper = Wraps.lbQ();
        lbqWrapper.eq(SupplementGasRecord::getGasMeterCode, gasMeterCode)
                .eq(SupplementGasRecord::getRepGasStatus, RepGasStatusEnum.WAIT_TO_METER.getCode())
                .eq(SupplementGasRecord::getDataStatus, DataStatusEnum.NORMAL.getValue());
        SupplementGasRecord one = baseService.getOne(lbqWrapper);
        if (Objects.isNull(one)) {
            throw new BizException("未查询到待上表的补气记录");
        }
        one.setRepGasStatus(RepGasStatusEnum.REP_GAS_SUCCESS.getCode());
        baseService.updateById(one);
        return R.success(one);
    }

    /**
     * 查询业务关注点补气记录列表
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 用户编号
     * @return 数据列表
     */
    @GetMapping("/queryFocusInfo")
    public R<List<SupplementGasRecordVO>> queryFocusInfo(@RequestParam("customerCode") String customerCode,
                                                         @RequestParam(value = "gasMeterCode", required = false) String gasMeterCode) {
        List<SupplementGasRecordVO> supplementGasList = baseService.queryFocusInfo(customerCode, gasMeterCode);
        return R.success(supplementGasList);
    }
}
