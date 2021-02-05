package com.cdqckj.gmis.common.utils;

import com.cdqckj.gmis.common.domain.code.BizCodeBaseUtil;
import com.cdqckj.gmis.common.domain.code.CodeInfo;
import com.cdqckj.gmis.common.domain.code.CodeTypeEnum;
import com.cdqckj.gmis.exception.BizException;
import lombok.extern.log4j.Log4j2;

/**
 * @author: lijianguo
 * @time: 2020/12/30 09:09
 * @remark: 删除表身号的code
 */
@Log4j2
public class BizCacheCodeRemove {

    /**
    * @Author: lijiangguo
    * @Date: 2020/12/30 9:10
    * @remark 删除这个code
    */
    public static void removeGasMeterNumber(Long factoryId, String oriCode){
        if(factoryId == null){
            throw BizException.wrap("厂家Id不能为空");
        }
        String tableName = "da_gas_meter";
        String colName = "gas_meter_number";
        CodeInfo codeInfo = new CodeInfo(CodeTypeEnum.RANDOM_TYPE_ONE, tableName,colName, String.valueOf(factoryId));
        BizCodeBaseUtil.removeCacheCode(codeInfo, oriCode);
    }
}
