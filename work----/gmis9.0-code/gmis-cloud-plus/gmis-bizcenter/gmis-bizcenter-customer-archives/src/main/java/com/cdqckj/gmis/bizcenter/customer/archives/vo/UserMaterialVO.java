package com.cdqckj.gmis.bizcenter.customer.archives.vo;

import lombok.Data;

import java.util.List;

/**
 * @author hp
 */
@Data
public class UserMaterialVO {

    private String customerCode;
    private String gasMeterCode;
    private String meterNumber;
    private List<ImgVO> imgList;

}
