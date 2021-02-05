package com.cdqckj.gmis.bizcenter.read.meter.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.readmeter.dto.ReadMeterDataIotPageDTO;
import com.cdqckj.gmis.readmeter.vo.ReadMeterDataIotVo;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 物联网抄表数据查询
 *
 * @author hp
 */
public interface ReadMeterDataIotBizService {

    /**
     * 分页查询
     *
     * @param params 分页查询参数
     * @return 分页列表
     */
    R<Page<ReadMeterDataIotVo>> page(@RequestBody PageParams<ReadMeterDataIotPageDTO> params);
}
