package com.cdqckj.gmis.biztemporary.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.biztemporary.dto.GasMeterInfoVO;
import com.cdqckj.gmis.biztemporary.entity.GasmeterTransferAccount;
import com.cdqckj.gmis.biztemporary.vo.GasMeterTransferAccountVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.userarchive.entity.Customer;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 柜台综合：过户业务表，业务状态未完成之前不会更改主表数据
 * </p>
 *
 * @author gmis
 * @date 2020-11-06
 */
public interface GasmeterTransferAccountService extends SuperService<GasmeterTransferAccount> {

    /**
     * 根据实体获取一条数据
     * @author hc
     * @param gasCode 气表编号
     * @return
     */
    GasmeterTransferAccount getOneByGasCode(String gasCode);

    /**
     * 过户数据处理：处理业务单与表具客户关联关系等
     * @author hc
     * @param transferAccount 业务单数据
     * @return
     */
    GasmeterTransferAccount transferAccountDataDeal(GasmeterTransferAccount transferAccount, GasMeterInfoVO gasMeter, Customer customer);

    /**
     * 身份证号重复性校验
     * @return
     * @param idCard
     */
    R<Boolean> checkAdd(String idCard);

    /**
     * 过户业务状态校验
     * @author hc
     * @date 2020/11/07
     * @param gasMeterCode
     * @param customerCode
     * @return
     */
    GasmeterTransferAccount transferAccountCheck(String gasMeterCode,String customerCode);

    /**
     * 校验是否欠费
     * @author hc
     * @date 2020/11/07
     * @param customerCode
     * @param gasMeterCode
     * @return
     */
    Boolean checkCharge(String customerCode, String gasMeterCode);

    /**
     * 业务关注点查询过户记录列表
     * @param customerCode 客户编号
     * @param gasMeterCode 表具编号
     * @return 过户记录列表
     */
    List<GasMeterTransferAccountVO> queryFocusInfo(String customerCode, String gasMeterCode);

    /**
    * @Author: lijiangguo
    * @Date: 2021/1/16 16:07
    * @remark 过户的数量
    */
    Long stsTransferNum(StsSearchParam stsSearchParam);
}
