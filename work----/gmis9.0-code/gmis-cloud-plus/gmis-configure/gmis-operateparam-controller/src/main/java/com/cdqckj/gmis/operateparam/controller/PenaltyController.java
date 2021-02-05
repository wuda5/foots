package com.cdqckj.gmis.operateparam.controller;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.controller.SuperController;
import com.cdqckj.gmis.base.entity.SuperEntity;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.database.mybatis.conditions.query.LbqWrapper;
import com.cdqckj.gmis.operateparam.dto.PenaltyPageDTO;
import com.cdqckj.gmis.operateparam.dto.PenaltySaveDTO;
import com.cdqckj.gmis.operateparam.dto.PenaltyUpdateDTO;
import com.cdqckj.gmis.operateparam.entity.Penalty;
import com.cdqckj.gmis.operateparam.service.PenaltyService;
import com.cdqckj.gmis.security.annotation.PreAuth;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cdqckj.gmis.base.MessageConstants.PENLTY_SCHEME_EXIST;



/**
 * <p>
 * 前端控制器
 *
 * </p>
 *
 * @author gmis
 * @date 2020-06-29
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/penalty")
@Api(value = "Penalty", tags = "滞纳金配置")
@PreAuth(replace = "penalty:")
public class PenaltyController extends SuperController<PenaltyService, Long, Penalty, PenaltyPageDTO, PenaltySaveDTO, PenaltyUpdateDTO> {

    /**
     * Excel导入后的操作
     *
     * @param list
     */
    @Override
    public R<Boolean> handlerImport(List<Map<String, Object>> list){
        List<Penalty> penaltyList = list.stream().map((map) -> {
            Penalty penalty = Penalty.builder().build();
            //TODO 请在这里完成转换
            return penalty;
        }).collect(Collectors.toList());

        return R.success(baseService.saveBatch(penaltyList));
    }

    @Override
    public  R<Penalty> save(@RequestBody @Validated PenaltySaveDTO saveDTO){
        Penalty  penalty= baseService.findPenaltyName(saveDTO.getExecuteName());
        PenaltyUpdateDTO penaltyUpdateDTO=new PenaltyUpdateDTO();
        penaltyUpdateDTO.setUseGasTypeId(saveDTO.getUseGasTypeId());
        if(penalty ==null){
            return super.save(saveDTO);
        }
        return R.fail(getLangMessage(PENLTY_SCHEME_EXIST));
    }

    @Override
    public R<Penalty> update(@RequestBody @Validated(SuperEntity.Update.class) PenaltyUpdateDTO updateDTO){
        Penalty  penalty= baseService.findPenaltyName(updateDTO.getExecuteName());
        if(penalty==null){
            return super.update(updateDTO);
        }else{
            if(penalty.getId().equals(updateDTO.getId())){
                return super.update(updateDTO);
            }else{
                return R.fail(getLangMessage(PENLTY_SCHEME_EXIST));
            }
        }
    }

    @ApiOperation(value = "获取最新一条数据")
    @GetMapping(value = "/queryRecentRecord")
    public Penalty queryRecentRecord(){
        return baseService.queryRecentRecord();
    }

    @Override
    public void query(PageParams<PenaltyPageDTO> params, IPage<Penalty> page, Long defSize) {
        handlerQueryParams(params);

        if (defSize != null) {
            page.setSize(defSize);
        }
        LbqWrapper<Penalty> lbqWrapper =new LbqWrapper();
        lbqWrapper.eq(Penalty::getDataStatus,params.getModel().getDataStatus());
        lbqWrapper.like(Penalty::getExecuteName,params.getModel().getExecuteName());
        getBaseService().page(page, lbqWrapper);
        // 处理结果
        handlerResult(page);
    }

    @PostMapping(value = "/check")
    R<String> check(@RequestBody PenaltyUpdateDTO updateDTO){
        return R.success(baseService.check(updateDTO));
    }

    @ApiOperation(value = "根据用气类型id获取滞纳金")
    @GetMapping(value = "/queryByUseGasId")
    public R<Penalty> queryByUseGasId(@RequestParam("useGasId") Long useGasId) {
        return success(baseService.queryByUseGasId(useGasId));
    }
}
