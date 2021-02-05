package com.cdqckj.gmis.iot.qc.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.iot.qc.entity.IotGasMeterCommand;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import com.cdqckj.gmis.iot.qc.vo.RetryVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


/**
 * <p>
 * 业务接口
 * 物联网气表指令数据
 * </p>
 *
 * @author gmis
 * @date 2020-10-15
 */
public interface IotGasMeterCommandService extends SuperService<IotGasMeterCommand> {
    /**
     * 查询指令列表
     * @param params
     * @return
     */
    Page<CommandEchoVO> queryCommand(CommandVO params);

    /**
     * 指令重试
     * @param retryVO
     * @return
     */
    IotR retry(String domainId, RetryVO retryVO) throws Exception;

    /**
     * 撤销指令
     * @param domainId
     * @param retryVO
     * @return
     * @throws Exception
     */
    IotR cancel(String domainId, RetryVO retryVO) throws Exception;

    /**
     * 获取业务执行状态
     * @param domainId
     * @param transactionNo
     * @return
     * @throws Exception
     */
    IotR businessStage(String domainId,String transactionNo) throws Exception;
}
