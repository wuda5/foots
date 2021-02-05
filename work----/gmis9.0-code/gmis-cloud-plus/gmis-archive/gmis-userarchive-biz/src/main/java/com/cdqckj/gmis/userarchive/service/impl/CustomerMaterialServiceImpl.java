package com.cdqckj.gmis.userarchive.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.userarchive.dao.CustomerMaterialMapper;
import com.cdqckj.gmis.userarchive.entity.CustomerMaterial;
import com.cdqckj.gmis.userarchive.service.CustomerMaterialService;
import com.cdqckj.gmis.base.service.SuperServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 
 * </p>
 *
 * @author gmis
 * @date 2020-07-31
 */
@Slf4j
@Service
@DS("#thread.tenant")
public class CustomerMaterialServiceImpl extends SuperServiceImpl<CustomerMaterialMapper, CustomerMaterial> implements CustomerMaterialService {
}
