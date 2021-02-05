package com.cdqckj.gmis.devicearchive.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.devicearchive.dto.GasMeterExVo;
import com.cdqckj.gmis.devicearchive.dto.GasMeterUpdateDTO;
import com.cdqckj.gmis.devicearchive.dto.GasMeterVo;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.vo.FactoryAndVersion;
import com.cdqckj.gmis.devicearchive.vo.PageGasMeter;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 业务接口
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-14
 */
public interface GasMeterService extends SuperService<GasMeter> {

    List<GasMeter> findGasMeterByCustomerCode(@RequestParam("customerCode") String customerCode);

    /* public R<Boolean> updateGasMeterInfo(@RequestBody CustomGasMesterVO customGasMesterVO);*/

    IPage<GasMeter> findGasMeterPage(@RequestBody GasMeterVo params);

    IPage<PageGasMeter> findGasMeterPageEx(@RequestBody GasMeterVo params);

    /**
     *获取调价补差气表分页信息
     * @param params
     * @return
     */
    IPage<PageGasMeter> findAdjustPriceGasMeterPage(@RequestBody GasMeterVo params);

    GasMeter findGasMeterByCode(String gascode);

    PageGasMeter findGasMeterBygasCode(String gasMeterCode);

    R<List<GasMeter>> addGasMeterList(List<GasMeter> gasMeterList);

    GasMeter findGasMeterByGasMeterNumber(String gasMeterNumber);
    GasMeter getMeterByBodyNoFactory(GasMeter gasMeter);

    Integer findGasMeterNumber(FactoryAndVersion factoryAndVersion);

    R<Boolean> updateByCode(GasMeter gasMeter);

    R<GasMeter> ventilation(GasMeter gasMeter);

    Boolean check(GasMeterUpdateDTO gasMeterUpdateDTO);
    Boolean checkByGasCode(GasMeterUpdateDTO gasMeterUpdateDTO);

    /**
     * 根据表号查询气表
     * @param meterNoList
     * @return
     */
    R<List<GasMeter>> queryMeterList(List<String> meterNoList);

    /**
     * 获取表具详情
     * @param gascode
     * @return
     */
    HashMap<String,Object> findGasInfo(@Param("gascode") String gascode);

    /**
     * @auth lijianguo
     * @date 2020/11/13 16:08
     * @remark 查询表具的档案根据编号
     */
    GasMeter findMeterByNumber(String number);

    /**
     * 获取表具详情
     * @author hc
     * @param gasMeterCode
     * @return
     */
    GasMeterExVo findGasMeterInfoByCodeEx(String gasMeterCode);

    /**
     * 通过表身号查询有效状态的表具
     *
     * @param gasMeterNumber 表身号
     * @return 表具信息
     */
    GasMeter findEffectiveMeterByNumber(String gasMeterNumber);

    List<PageGasMeter> findGasMeterListBygasCode(String customerCode);

    /**
     * 拆除表具时清空档案开户时填写的数据
     * @param updateDTO 表具信息
     * @return  清空结果
     */
    Boolean clearMeter(GasMeterUpdateDTO updateDTO);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/12 10:53
    * @remark 查询没有拆除的表具
    */
    GasMeter findOnLineMeterByNumber(String number);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 14:48
    * @remark 发卡的表具
    */
    Long sendCardNum(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 9:57
    * @remark
    */
    List<StsInfoBaseVo<String, Long>> stsGasMeterType(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 10:38
    * @remark 统计厂家的数量
    */
    List<StsInfoBaseVo<String, Long>> stsFactory(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 13:49
    * @remark 表具的状态
     * @return
    */
    List<StsInfoBaseVo<Integer, Long>> stsStatus(StsSearchParam stsSearchParam);

    /**
     * 校验表身号是否存在
     * @param bodyNumber
     * @param fachtoryId
     * @return
     */
    Boolean isExistsBodyNumber(Long fachtoryId, String bodyNumber);
}
