package com.cdqckj.gmis.readmeter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotPageDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterDataIot;
import com.cdqckj.gmis.readmeter.enumeration.ProcessEnum;
import com.cdqckj.gmis.readmeter.vo.ReadMeterDataIotVo;
import com.cdqckj.gmis.readmeter.vo.SettlementArrearsVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 抄表数据
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
public interface ReadMeterDataIotService extends SuperService<ReadMeterDataIot> {
    R<List<ReadMeterDataIot>> getMeterDataByCode(List<String> meterCodelist, List<Integer> yearlist, List<Integer> monthlist, ProcessEnum process);

    R<List<ReadMeterDataIot>> getHistory(List<String> meterCodelist, Date time);

    Boolean check(LocalDate sDate, String meterId,String customerCode,String gasMeterCode);

    /**
     * 获取场景数据
     * @param sDate
     * @param meterId
     * @param customerCode
     * @param gasMeterCode
     * @return
     */
    List<ReadMeterDataIot> getCQData(LocalDate sDate, String meterId,String customerCode,String gasMeterCode);

    /**
     * 获取已结算的场景数据
     * @return
     */
    List<ReadMeterDataIot> getSettlementCQData(String gasMeterCode,String customerCode);
    /**
     * 根据表编号查询
     * @param gasMeterNumber
     * @return
     */
    ReadMeterDataIot getDataByMeterNo(String gasMeterNumber);

    /**
     * 获取可结算数据
     * @return 可结算数据
     */
    List<ReadMeterDataIot> getUnSettlementData();

    /**
     * 获取上一次抄表数据
     * @param gasMeterNumber 表身号
     * @param dataTime 冻结时间
     * @return 上一次抄表数据
     */
    ReadMeterDataIot getPreviousData(String gasMeterCode, String gasMeterNumber,String customerCode, LocalDateTime dataTime);

    /**
     * 物联网表结算
     * @return
     */
    List<ReadMeterDataIot> settlement();

    /**
     *  分页查询列表
     *
     * @param params 分页参数
     * @return 分页列表数据
     */
    Page<ReadMeterDataIotVo> pageList(PageParams<ReadMeterDataIotPageDTO> params);
    /**
     * 物联网后付费获取结算数据
     * @param arrearsVO
     * @return
     */
    List<ReadMeterDataIot> getSettlementArrears(SettlementArrearsVO arrearsVO);

    /**
     * 获取未生成后付费账单的抄表记录
     * @param arrearsDate
     * @return
     */
    List<ReadMeterDataIot> getArrearsRecord(LocalDate arrearsDate);
    /**
     * 获取
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    List<ReadMeterDataIot> findSettleData(String gasMeterCode, String customerCode);

    /**
     * 退费数据修改
     * @param idsList
     * @return
     */
    Boolean updateDataRefundIot(List<Long> idsList);

    /**
     *  获取最新抄表数据--不区分dataType
     * @param gasMeterNumber 表身号
     * @param gasMeterCode 表身编号
     * @param customerCode 客户编号
     * @param dataTime 冻结时间
     * @return 最新抄表数据
     */
    ReadMeterDataIot getLatestData(String gasMeterNumber, String gasMeterCode,String customerCode, LocalDateTime dataTime);

    /**
     * 根据欠费编号更新抄表数据
     * @param idsList
     * @return
     */
    int updateByGasOweCode(List<Long> idsList);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/29 15:48
    * @remark 统计物联网标的气量
     * @return
    */
    BigDecimal stsInternetGasMeterGas(StsSearchParam stsSearchParam, String type);
}
