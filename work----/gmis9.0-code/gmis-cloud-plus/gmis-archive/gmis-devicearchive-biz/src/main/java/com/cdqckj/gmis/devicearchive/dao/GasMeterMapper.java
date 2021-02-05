package com.cdqckj.gmis.devicearchive.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.common.domain.sts.StsInfoBaseVo;
import com.cdqckj.gmis.devicearchive.dto.GasMeterExVo;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import com.cdqckj.gmis.devicearchive.vo.FactoryAndVersion;
import com.cdqckj.gmis.devicearchive.vo.PageGasMeter;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 *
 * </p>
 *
 * @author gmis
 * @date 2020-07-14
 */
@Repository
public interface GasMeterMapper extends SuperMapper<GasMeter> {
    Integer findGasMeterNumber(FactoryAndVersion factoryAndVersion);

    /**
     * 获取表具详情
     * @param gascode
     * @return
     */
    HashMap<String,Object> findGasInfo(@Param("gascode") String gascode);


    IPage<PageGasMeter> findGasMeterPageEx(Page<GasMeter> page, @Param(Constants.WRAPPER) Wrapper<GasMeter> wrapper);

    /**
     * 获取调价补差气表分页信息
     * @param page
     * @param wrapper
     * @return
     */
    IPage<PageGasMeter> findAdjustPriceGasMeterPage(Page<GasMeter> page, @Param(Constants.WRAPPER) Wrapper<GasMeter> wrapper);

    /**
     * @auth lijianguo
     * @date 2020/11/13 16:11
     * @remark 查询表具
     */
    GasMeter findMeterByNumber(@Param("number") String number);

    /**
     * 获取表具详情
     * @author hc
     * @param gasMeterCode
     * @return
     */
    GasMeterExVo findGasMeterInfoByCodeEx(@Param("gasMeterCode") String gasMeterCode);

    /**
     * 通过表身号查询有效的表具
     *
     * @param gasMeterNumber 表身号
     * @return 表具信息
     */
    GasMeter findEffectiveMeterByNumber(@Param("gasMeterNumber") String gasMeterNumber);

    PageGasMeter findGasMeterBygasCode(@Param("gasMeterCode")String gasMeterCode);

    List<PageGasMeter> findGasMeterListBygasCode(@Param("customerCode")String customerCode);

    /**
     * 拆除表具时清空档案开户时填写的数据
     * @param id 表具ID
     * @return 更新结果
     */
    Integer clearMeter(@Param("id") Long id);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/12 10:54
    * @remark 查询没有拆除的表具
    */
    GasMeter findOnLineMeterByNumber(@Param("number") String number);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/15 14:49
    * @remark 发卡的统计
    */
    Long sendCardNum(@Param("stsSearchParam") StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 10:12
    * @remark 表具分类的统计
    */
    List<StsInfoBaseVo<String, Long>> stsGasMeterType(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("status") Integer status, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 10:39
    * @remark 统计厂家的数据
    */
    List<StsInfoBaseVo<String, Long>> stsFactory(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("status") Integer status, @Param("dataScope") String dataScope);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 13:51
    * @remark 表具的状态
    */
    List<StsInfoBaseVo<Integer, Long>> stsStatus(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("status") Integer status, @Param("dataScope") String dataScope);
}
