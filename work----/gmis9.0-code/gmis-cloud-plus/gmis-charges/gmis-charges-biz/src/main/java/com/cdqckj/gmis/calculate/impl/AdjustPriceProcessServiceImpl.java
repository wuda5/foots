package com.cdqckj.gmis.calculate.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.base.service.SuperServiceImpl;
import com.cdqckj.gmis.calculate.AdjustPriceProcessService;
import com.cdqckj.gmis.charges.dao.AdjustPriceProcessMapper;
import com.cdqckj.gmis.charges.entity.AdjustPriceProcess;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-11-09
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class AdjustPriceProcessServiceImpl extends SuperServiceImpl<AdjustPriceProcessMapper, AdjustPriceProcess> implements AdjustPriceProcessService {
}
