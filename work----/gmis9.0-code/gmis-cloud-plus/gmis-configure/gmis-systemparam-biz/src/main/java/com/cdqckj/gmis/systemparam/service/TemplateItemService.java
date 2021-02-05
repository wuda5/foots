package com.cdqckj.gmis.systemparam.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.systemparam.dto.TemplateItemPageDTO;
import com.cdqckj.gmis.systemparam.entity.TemplateItem;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 模板项目表（Item）
 * </p>
 *
 * @author gmis
 * @date 2020-07-01
 */
public interface TemplateItemService extends SuperService<TemplateItem> {
    R<TemplateItem> share(@RequestBody TemplateItem saveDTO);

    R<Boolean> adminExamine(@RequestBody List<TemplateItem> list);

    R<Boolean> publish(@RequestBody List<TemplateItem> list);

    R<IPage<TemplateItem>> pageAdminTemplate(@RequestBody PageParams<TemplateItemPageDTO> params);
}
