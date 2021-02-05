package com.cdqckj.gmis.readmeter.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.base.service.SuperService;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookPageDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookSaveDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookUpdateDTO;
import com.cdqckj.gmis.readmeter.entity.ReadMeterBook;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * <p>
 * 业务接口
 * 抄表册
 * </p>
 *
 * @author gmis
 * @date 2020-07-13
 */
public interface ReadMeterBookService extends SuperService<ReadMeterBook> {
    R<Boolean> saveBook(@RequestBody ReadMeterBook book);

    R<Boolean> updateBookDetail(@RequestBody ReadMeterBook book);

    R<IPage<ReadMeterBook>> page(@RequestBody @Validated PageParams<ReadMeterBookPageDTO> params);
}
