package com.cdqckj.gmis.bizcenter.customer.archives.service;

import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.bizcenter.customer.archives.vo.ImgVO;
import com.cdqckj.gmis.bizcenter.customer.archives.vo.UserMaterialVO;

import java.util.List;

/**
 * @author hp
 */
public interface CustomerMaterialService extends SuperCenterService {

    /**
     * 上传用户资料
     *
     * @param materialVO
     * @return
     */
    List<ImgVO> uploadMaterial(UserMaterialVO materialVO);

    /**
     * 查询用户的资料档案
     * @param query
     * @return
     */
    List<ImgVO> query(UserMaterialVO query);
}
