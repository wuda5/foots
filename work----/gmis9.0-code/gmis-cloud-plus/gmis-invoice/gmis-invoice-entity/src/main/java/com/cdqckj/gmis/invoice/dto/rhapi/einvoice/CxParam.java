package com.cdqckj.gmis.invoice.dto.rhapi.einvoice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 瑞宏电子发票：发票查询请求参数
 *
 * @author ASUS
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CxParam {

    String serialNo;
    String postTime;
    List<Criteria> criteria;

}
