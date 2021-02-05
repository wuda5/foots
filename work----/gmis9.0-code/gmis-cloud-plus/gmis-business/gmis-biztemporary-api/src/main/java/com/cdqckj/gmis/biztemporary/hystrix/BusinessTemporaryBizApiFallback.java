package com.cdqckj.gmis.biztemporary.hystrix;

import cn.hutool.json.JSONObject;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.biztemporary.BusinessTemporaryBizApi;
import com.cdqckj.gmis.biztemporary.dto.EstablishAccountImportPageDTO;
import com.cdqckj.gmis.biztemporary.dto.TransferAccountDTO;
import com.cdqckj.gmis.biztemporary.entity.GasmeterTransferAccount;
import com.cdqckj.gmis.biztemporary.vo.GasMeterTransferAccountVO;
import com.cdqckj.gmis.common.domain.search.StsSearchParam;
import com.cdqckj.gmis.devicearchive.entity.GasMeter;
import feign.Response;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

/**
 * @author songyz
 */
@Component
public class BusinessTemporaryBizApiFallback implements BusinessTemporaryBizApi {

    @Override
    public R<GasMeter> establishAccount(GasMeter gasMeter, String customerCode, String chargeNo, boolean isAddGasMeter) {
        return R.timeout();
    }

    @Override
    public R<Boolean> establishAccountBeforehand(GasMeter gasMeter, String rechargeAmount) {
        return R.timeout();
    }

    @Override
    public R<Boolean> establishAccountBeforehandBatch(List<GasMeter> gasMeterList, String rechargeAmount) {
        return R.timeout();
    }

    @Override
    public R<JSONObject> importExcelBackJsonObject(MultipartFile file) {
        return R.timeout();
    }

    @Override
    public R<Boolean> exportTemplateFile(String templateCode) {
        return R.timeout();
    }

    @Override
    public Response exportCombobox(PageParams<EstablishAccountImportPageDTO> params) throws Exception {
        HttpServletResponse response = ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
        response.getWriter().write("导出开户模板超时");
        response.getWriter().flush();
        return null;
    }

    @Override
    public Response exportCascadeTemplate(PageParams<EstablishAccountImportPageDTO> params) throws Exception {
        HttpServletResponse response = ((ServletWebRequest) RequestContextHolder.getRequestAttributes()).getResponse();
        response.getWriter().write("导出开户级联模板超时");
        response.getWriter().flush();
        return null;
    }

    @Override
    public R<HashMap<String, Object>> transferAccountCheck(String gasMeterCode, String customerCode) {
        return R.timeout();
    }

    @Override
    public R<GasmeterTransferAccount> transferAccount(TransferAccountDTO transferAccountDTO) {
        return R.timeout();
    }

    @Override
    public R<GasmeterTransferAccount> transferAccountCallBack(Long businessNo) {
        return R.timeout();
    }

    @Override
    public R<Boolean> transferAccountCallChargeBack(Long businessNo) {
        return R.timeout();
    }

    /**
     * 业务关注点查询过户数据列表
     *
     * @param gasMeterCode 表具编号
     * @param customerCode 用户编号
     * @return 数据列表
     */
    @Override
    public R<List<GasMeterTransferAccountVO>> queryFocusInfo(String gasMeterCode, String customerCode) {
        return R.timeout();
    }

    @Override
    public R<Long> stsTransferNum(StsSearchParam stsSearchParam) {
        return R.timeout();
    }
}
