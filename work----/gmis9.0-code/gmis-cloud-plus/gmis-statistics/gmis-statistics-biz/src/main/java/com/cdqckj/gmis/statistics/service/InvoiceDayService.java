package com.cdqckj.gmis.statistics.service;

import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.statistics.entity.InvoiceDay;
import com.cdqckj.gmis.statistics.vo.InvoiceDayKindVo;
import com.cdqckj.gmis.common.domain.sts.InvoiceDayStsVo;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 业务接口
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-02
 */
public interface InvoiceDayService extends SuperService<InvoiceDay> {

    /**
     * @auth lijianguo
     * @date 2020/11/3 13:15
     * @remark 查询这个记录是否存在
     */
    InvoiceDay getTheDayRecord(String searchStr);

    /**
     * @auth lijianguo
     * @date 2020/11/11 15:12
     * @remark 统计发票的类型
     */
    List<InvoiceDayKindVo> stsKindNow(Long uId, Integer kind, LocalDateTime stsDay);

    /**
     * @auth lijianguo
     * @date 2020/11/20 15:53
     * @remark 统计输入
     */
    List<InvoiceDayKindVo> panelInvoiceKind(StsSearchParam stsSearchParam);

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/18 14:56
    * @remark 统计这个type下面的类型
    */
    List<InvoiceDayStsVo> invoiceTypeGroupByKind(StsSearchParam stsSearchParam, String typeCode);
}
