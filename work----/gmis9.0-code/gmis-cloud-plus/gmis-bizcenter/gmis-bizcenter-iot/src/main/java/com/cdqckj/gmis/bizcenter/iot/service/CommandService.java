package com.cdqckj.gmis.bizcenter.iot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.IotR;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.iot.qc.vo.CommandEchoVO;
import com.cdqckj.gmis.iot.qc.vo.CommandVO;
import com.cdqckj.gmis.iot.qc.vo.RetryVO;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CommandService extends SuperCenterService {
    /**
     * 查询指令列表
     * @param params
     * @return
     */
    R<Page<CommandEchoVO>> queryCommand(CommandVO params);

    /**
     * 指令重试
     * @param retryList
     * @return
     */
    IotR retry(List<RetryVO> retryList) throws Exception;

    /**
     * 撤销指令
     * @param retryList
     * @return
     * @throws Exception
     */
    IotR cancel(List<RetryVO> retryList);

    /**
     * 获取指令执行状态
     * @param retryVO
     * @return
     */
    IotR businessStage(RetryVO retryVO);
}
