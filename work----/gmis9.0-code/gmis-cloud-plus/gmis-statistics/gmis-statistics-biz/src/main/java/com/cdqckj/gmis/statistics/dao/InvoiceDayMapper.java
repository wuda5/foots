package com.cdqckj.gmis.statistics.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.InvoiceDay;

import com.cdqckj.gmis.statistics.vo.InvoiceDayKindVo;
import com.cdqckj.gmis.common.domain.sts.InvoiceDayStsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-02
 */
@Repository
public interface InvoiceDayMapper extends SuperMapper<InvoiceDay> {

    /**
     * @auth lijianguo
     * @date 2020/11/3 13:18
     * @remark 查询记录
     */
    InvoiceDay getTheDayRecord(@Param("searchStr") String searchStr);

    /**
     * @auth lijianguo
     * @date 2020/11/11 15:13
     * @remark 类型
     */
    List<InvoiceDayKindVo> stsKindNow(@Param("uId") Long uId, @Param("kind") Integer kind, @Param("stsDay") LocalDateTime stsDay);

    /**
     * @auth lijianguo
     * @date 2020/11/20 15:54
     * @remark 发票的类型
     */
    List<InvoiceDayKindVo> panelInvoiceKind(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("searchSql") String searchSql);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/18 14:58
    * @remark 统计
    */
    List<InvoiceDayStsVo> getInvoiceTypeGroupByKind(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("searchSql") String searchSql, @Param("typeCode") String typeCode);
}
