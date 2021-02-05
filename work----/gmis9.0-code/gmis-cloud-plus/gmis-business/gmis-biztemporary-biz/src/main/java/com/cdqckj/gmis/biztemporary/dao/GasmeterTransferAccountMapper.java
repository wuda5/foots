package com.cdqckj.gmis.biztemporary.dao;

import com.cdqckj.gmis.base.mapper.SuperMapper;
import com.cdqckj.gmis.biztemporary.entity.GasmeterTransferAccount;
import com.cdqckj.gmis.biztemporary.vo.GasMeterTransferAccountVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 柜台综合：过户业务表，业务状态未完成之前不会更改主表数据
 * </p>
 *
 * @author gmis
 * @date 2020-11-06
 */
@Repository
public interface GasmeterTransferAccountMapper extends SuperMapper<GasmeterTransferAccount> {

    /**
     * 业务关注点数据查询
     * @param customerCode 用户编号
     * @param gasMeterCode 表具编号
     * @return
     */
    List<GasMeterTransferAccountVO> queryFocusInfo(@Param("customerCode") String customerCode,@Param("gasMeterCode") String gasMeterCode,
                                                   @Param("orgIds") List<Long> orgIds);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 16:08
    * @remark 过户的数据统计
    */
    Long stsTransferNum(@Param("stsSearchParam") StsSearchParam stsSearchParam, @Param("dataScope") String dataScope);
}
