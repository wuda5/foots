package com.cdqckj.gmis.operationcenter.readmeter.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.common.utils.BigDecimalUtils;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.operationcenter.readmeter.dto.*;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.ReadMeterLatestRecordApi;
import com.cdqckj.gmis.readmeter.dto.*;
import com.cdqckj.gmis.readmeter.dtoex.OneReadDataInputDTO;
import com.cdqckj.gmis.readmeter.dtoex.OneReadDataUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterData;
import com.cdqckj.gmis.readmeter.entity.ReadMeterLatestRecord;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.enumeration.ReadMeterStatusEnum;
import com.cdqckj.gmis.utils.BeanPlusUtil;
import com.cdqckj.gmis.utils.DateUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;


/**
 * <p>
 * 抄表导入前端控制器
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/appReadmeter/readMeterData")
@Api(value = "data", tags = "抄表数据")
public class AppReadMeterDataController {

    @Autowired
    public ReadMeterDataApi readMeterDataApi;
    @Autowired
    public ReadMeterLatestRecordApi readMeterLatestApi;
    @Autowired
    public GasMeterBookRecordApi gasMeterBookRecordApi;

//    @ApiOperation(value = "抄表数据审核")
//    @PostMapping("/examine")
//    public R<List<ReadMeterData>> examine(@RequestBody @Validated AppReadMeterDataExamineDTO dto) {
//
//        List<ReadMeterData> p = new ArrayList<>();
//        dto.getReadMeterDataIds().stream().distinct().forEach(x -> {
//            ReadMeterData readMeterData = new ReadMeterData().setProcessStatus(dto.getProcessStatus());
//            readMeterData.setId(x);
//            p.add(readMeterData);
//        });
//        R<List<ReadMeterData>> listR = readMeterDataApi.examine(p);
//        // TODO 调用计费接口
//        return listR;
//    }


    //    @ApiOperation(value = "分页查询抄表员的某一个抄表计划抄表客户数据列表")
//    @PostMapping("/page")
//    public R<Page<ReadMeterData>> test(@RequestBody @Validated PageParams<AppReadMeterDataPageDTO> params){
//
//        PageParams<ReadMeterDataPageDTO> pa = new PageParams<>();
//        pa.setCurrent(params.getCurrent());
//        pa.setSize(params.getSize());
//
//        AppReadMeterDataPageDTO model = params.getModel();
//        pa.setModel(new ReadMeterDataPageDTO()
//                .setCustomerName(model.getCustomerName())
//                .setDataStatus(model.getReadMeterStatus())
//                .setPlanId(model.getReadMeterPlanId())
//                .setReadMeterUserId(model.getReadMeterUserId())
//        );
//        return readMeterDataApi.page(pa);
//    }
    @ApiOperation(value = "分页 查询抄表员的管的某一个 客户的<表>的 <抄表数据历史！！>列表--通过表的唯一标志查")
    @PostMapping("/page")
    public R<Page<ReadMeterData>> test(@RequestBody @Validated AppReadDataPageDTO params) {

        PageParams<ReadMeterDataPageDTO> pa = new PageParams<>();
        pa.setCurrent(params.getCurrent());
        pa.setSize(params.getSize());

        pa.setModel(new ReadMeterDataPageDTO()
                .setDataStatus(ReadMeterStatusEnum.READ.getCode())// 避免查询到初始化的其实没有抄表的数据
                .setGasMeterCode(params.getGasMeterCode()));
        // 排序？
        return readMeterDataApi.page(pa);
//        return readMeterDataApi.pageReadMeterData(pa);
    }
    @ApiOperation(value = "查询特定表的指定月份 抄表数据信息")
    @PostMapping("/queryOneByDate")
    public R<ReadMeterData> queryOneByDate(@RequestBody  OneReadDataInputDTO dto) {
        ReadMeterData resutl = readMeterDataApi.queryOne(new ReadMeterData()
                .setGasMeterCode(dto.getGasMeterCode())
                .setReadMeterYear(dto.getReadMeterYear())
                .setReadMeterMonth(dto.getReadMeterMonth())
                .setDataStatus(ReadMeterStatusEnum.READ.getCode()))// 已抄表
                .getData();
        return R.success(resutl);
    }
    @ApiOperation(value = "抄表员app录入单个客户表具的<纠正> 抄表数据信息")
    @PostMapping("/inputUpdateData")
    public R<ReadMeterData> inputReadMeterData(@RequestBody @Validated OneReadDataUpdateDTO dto){
       return readMeterDataApi.input(BeanPlusUtil.toBean(dto,ReadMeterData.class));
    }

    @ApiOperation(value = "抄表员app录入单个客户表具的 抄表数据信息")
    @PostMapping("/inputData")
    public R<ReadMeterData> inputReadMeterData(@RequestBody @Validated OneReadDataInputDTO dto) {
        // check()
        ReadMeterData red = readMeterDataApi.checkInitOneMeterData(dto).getData();
        if (!Objects.equals(red.getProcessStatus(), ProcessEnum.TO_BE_REVIEWED)
                && !Objects.equals(red.getProcessStatus(), ProcessEnum.REVIEW_REJECTED)) {
            // 肯定是修改才会可能有其他的状态
            throw new BizException("此月份的抄表数据此前录入已审核，其当前是 非待提审和驳回状态 不能修改录入 抄表数据");
        }

        LocalDate inputDate = DateUtils.getDate8(dto.getReadMeterYear(), dto.getReadMeterMonth());

        // 根据气表编号 判断此表在此抄表月份 中，查询此表的 对应的最新月份抄表数值 b ，1.如果传入的 年月 <(早于) 系统现在a的 年月，不能抄表
        // 如果都没有查询到数据，则已当前传入的给它初始化个xxx--??
        // 2. 此时传入的抄表指数 x 必须大于 系统现在a 的最新指数， 否则 提升失败
        ReadMeterLatestRecord latestRecord = readMeterLatestApi.queryOne(new ReadMeterLatestRecord()
                .setGasMeterCode(dto.getGasMeterCode())
        ).getData();
        if (Objects.isNull(latestRecord)){
            throw new BizException("此表还没有初始的 最新抄表记录 ，请先从抄表册移除该表具用户后重新添加到抄表册后，再录入抄表数据");
        }
        if (inputDate.isBefore(DateUtils.getDate8(latestRecord.getYear(), latestRecord.getMonth()))){
            throw new BizException("选择录入的年月早于系统最新的录入时间，不能录入以往老数据造成止数错乱");
        }
        if (BigDecimalUtils.lessThan(dto.getCurrentTotalGas(),latestRecord.getCurrentTotalGas())){
            throw new BizException("选择录入的年月的抄表数据指数不能小于之前系统存在的数据 ");
        }

        // 再设值抄表数据(本期，上期，用量)相关, 它的上期止数来源 当前系统的最新抄表值 latestRecord.getCurrentTotalGas()

        /** 录入数据，走跟新()，先上面就确定 给初始化好了对象 red ,基本数据 **/
        ReadMeterDataUpdateDTO inputUpdate = BeanPlusUtil.toBean(red, ReadMeterDataUpdateDTO.class);
        inputUpdate.setDataStatus(ReadMeterStatusEnum.READ.getCode());// 设置0为录入完成状态
        inputUpdate.setCurrentTotalGas(dto.getCurrentTotalGas())
                .setLastTotalGas(latestRecord.getCurrentTotalGas())
             .setMonthUseGas(dto.getCurrentTotalGas().subtract(latestRecord.getCurrentTotalGas()));
        //TODO 时间相关的设置，还差一个设置上期抄表时间！！！
        inputUpdate.setReadTime(inputDate);
        inputUpdate.setRecordTime(LocalDate.now())// 录入抄表时间？是否app 也传入
                .setLastReadTime(latestRecord.getCurrentReadTime());

        // 最后还需要同步修改掉 系统中这只表具的的最新数据
        readMeterLatestApi.update(BeanPlusUtil.toBean(latestRecord, ReadMeterLatestRecordUpdateDTO.class)
                .setCurrentTotalGas(dto.getCurrentTotalGas())
                .setYear(dto.getReadMeterYear())
                .setMonth(dto.getReadMeterMonth())
                .setReadTime(DateUtils.getDate8(dto.getReadMeterYear(), dto.getReadMeterMonth()))
                .setCurrentReadTime(LocalDate.now())); //TODO 还差一个设置最新的抄表时间（作为录入抄表数据时的上期抄表时间 显示）！！！

        // 封装data
        return readMeterDataApi.update(inputUpdate);
    }


//    /***
//     * 检测确保 某表具 对应的月份初始化数据
//     * */
//    private ReadMeterData checkInitOneMeterData(OneReadDataInputDTO dto) {
//        // 一：先查询是 此表否已经 初始化此月份数据（如果没有先初始化），录入过此月份下的数据，如录入过，提示已经录入，可以在表的历史抄表记录里面查查看修改--基础服务提供个方法？
//        ReadMeterData resutl = readMeterDataApi
//                .queryOne(new ReadMeterData().setGasMeterCode(dto.getGasMeterCode())
//                        .setReadMeterYear(dto.getReadMeterYear())
//                        .setReadMeterMonth(dto.getReadMeterMonth())).getData();
//
//        if (null == resutl){
//            // 初始化xx
//            // 三.构建抄表数据信息,gasMeterCode 查询抄表册关联客户表具 得到xx
//            GasMeterBookRecord relate = gasMeterBookRecordApi.queryOne(new GasMeterBookRecord().setGasMeterCode(dto.getGasMeterCode())).getData();
//            // 大部分字段命名同，个别不同再单独设置值
//            ReadMeterDataSaveDTO data = BeanPlusUtil.toBean(relate, ReadMeterDataSaveDTO.class);
//            data.setBookId(relate.getReadMeterBook())
//                    .setReadMeterUserId(relate.getReadMeterUser())
//                    .setReadMeterYear(dto.getReadMeterYear()) // 年
//                    .setReadMeterMonth(dto.getReadMeterMonth()) // 月份
//                    .setReadTime(DateUtils.getDate8(dto.getReadMeterYear(),dto.getReadMeterMonth()))// 抄表年月
//                    .setProcessStatus(ProcessEnum.TO_BE_REVIEWED)
//                    .setDataStatus(ReadMeterStatusEnum.WAIT_READ.getCode());// 初始未抄表
//            // 再设值抄表数据(本期，上期，用量)相关, 它的上期止数来源 当前系统的最新抄表值 latestRecord.getCurrentTotalGas()
//             resutl= readMeterDataApi.save(data).getData();
//
//        }
//        return resutl;
//    }



//    @ApiOperation(value = "抄表员app录入抄表数据信息")
//    @PostMapping("/inputData")
//    public R<ReadMeterData> updateReadMeterData(@RequestBody @Validated AppReadMeterDataUpdateDTO dto) {
//        // 一：先查询是 此表否已经录入过此月份下的数据，如录入过，提示已经录入，可以在表的历史抄表记录里面查查看修改
//
//        BizAssert.isTrue(dto.getCurrentTotalGas().subtract(dto.getLastTotalGas()).doubleValue() >= 0, "本期止数必须不小于上期止数");
//        ReadMeterData readMeterDataUpdateDTO = BeanPlusUtil.toBean(dto, ReadMeterData.class);
//        // 判断此表在此抄表月份 中，查询此表的 对应的最新月份抄表数值 b ，如果传入的 年月 < 系统现在a的 年月，不能抄表
//        // 2. 此时传入的抄表指数 x 必须大于 系统现在a 的最新指数， 否则 提升失败
//        return readMeterDataApi.input(readMeterDataUpdateDTO);
//    }
}
