package com.cdqckj.gmis.bizcenter.sys.config.service;

import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.service.SuperCenterService;
import com.cdqckj.gmis.systemparam.dto.TollItemSaveDTO;
import com.cdqckj.gmis.systemparam.dto.TollItemUpdateDTO;
import com.cdqckj.gmis.systemparam.entity.TollItem;
import org.springframework.web.bind.annotation.RequestBody;

public interface TollItemService extends SuperCenterService {
    R<TollItem> saveTollItem(@RequestBody TollItemSaveDTO tollItemSaveDTO);
    R<TollItem> updateTollItem(@RequestBody TollItemUpdateDTO tollItemUpdateDTO);
}
