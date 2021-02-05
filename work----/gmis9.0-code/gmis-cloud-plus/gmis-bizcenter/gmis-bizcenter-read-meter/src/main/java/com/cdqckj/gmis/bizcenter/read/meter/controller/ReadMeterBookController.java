package com.cdqckj.gmis.bizcenter.read.meter.controller;

import cn.afterturn.easypoi.entity.vo.NormalExcelConstants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdqckj.gmis.archive.CustomerBizApi;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.base.request.PageParams;
import com.cdqckj.gmis.bizcenter.read.meter.service.ReadMeterBookBizService;
import com.cdqckj.gmis.common.utils.ExportUtil;
import com.cdqckj.gmis.readmeter.GasMeterBookRecordApi;
import com.cdqckj.gmis.readmeter.ReadMeterBookApi;
import com.cdqckj.gmis.readmeter.ReadMeterDataApi;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordPageDTO;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordSaveDTO;
import com.cdqckj.gmis.readmeter.dto.GasMeterBookRecordUpdateDTO;
import com.cdqckj.gmis.readmeter.dto.ReadMeterBookPageDTO;
import com.cdqckj.gmis.readmeter.entity.GasMeterBookRecord;
import com.cdqckj.gmis.readmeter.entity.ReadMeterBook;
import com.cdqckj.gmis.readmeter.vo.GasMeterBookRecordVo;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotEmpty;
import java.io.IOException;
import java.util.List;


/**
 * <p>
 * 抄表册前端控制器
 * </p>
 * @author gmis
 * @date 2020-07-01
 */
@Slf4j
@Validated
@RestController
@RequestMapping("/readmeter/register")
@Api(value = "register", tags = "抄表册")
//@PreAuth(replace = "readMeterBook:")
public class ReadMeterBookController {

    @Autowired
    public ReadMeterBookApi readMeterBookApi;
    @Autowired
    public ReadMeterDataApi dataApi;
    @Autowired
    public GasMeterBookRecordApi gasMeterBookRecordApi;
    @Autowired
    public CustomerBizApi customerBizApi;
    @Autowired
    public ReadMeterBookBizService readMeterBookService;

    @ApiOperation(value = "新增抄表册信息")
    @PostMapping("/readMeterBook/add")
    public R<Boolean> saveReadMeterBook(@RequestBody @Validated ReadMeterBook book){
        return readMeterBookApi.saveBook(book);
    }

    @ApiOperation(value = "更新抄表册信息")
    @PutMapping("/readMeterBook/update")
    public R<Boolean> updateReadMeterBook(@RequestBody ReadMeterBook book){
        return readMeterBookApi.updateBookDetail(book);
    }

    @ApiOperation(value = "(单个删除)删除抄表册信息")
    @PostMapping("/readMeterBook/delete")
    public R<Boolean> deleteReadMeterBook(@RequestBody @NotEmpty(message = "请至少选择一条数据") List<Long> ids){
        return readMeterBookService.deleteReadMeterBook(ids);
    }

    @ApiOperation(value = "分页查询抄表册信息")
    @PostMapping("/readMeterBook/page")
    public R<Page<ReadMeterBook>> pageReadMeterBook(@RequestBody PageParams<ReadMeterBookPageDTO> params){
        return readMeterBookApi.page(params);
    }

    @ApiOperation(value = "导出抄表册")
    @PostMapping("/readMeterBook/export")
    public void export(@RequestBody @Validated PageParams<ReadMeterBookPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        // feign文件下载
        Response response = readMeterBookApi.export(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, "临时文件");
        ExportUtil.exportExcel(response,httpResponse,fileName);
    }

    @ApiOperation(value = "导出抄表册(模板)")
    @PostMapping("/readMeterBook/exportCombobox")
    public void exportCombobox(@RequestBody @Validated PageParams<ReadMeterBookPageDTO> params, HttpServletRequest request, HttpServletResponse httpResponse) throws IOException {
        // feign文件下载
        Response response = readMeterBookApi.exportCombobox(params);
        String fileName = params.getMap().getOrDefault(NormalExcelConstants.FILE_NAME, "临时文件");
        ExportUtil.exportExcel(response,httpResponse,fileName);
    }

    @ApiOperation(value = "新增抄表册关联用户(条件新增)")
    @PostMapping("/gasMeterBookRecord/addByQuery")
    public R<Boolean> addByQuery(@RequestBody PageParams<GasMeterBookRecordPageDTO> pageDTO){
        return readMeterBookService.addByQuery(pageDTO.getModel());
    }

    @ApiOperation(value = "新增抄表册关联用户(选中新增)")
    @PostMapping("/gasMeterBookRecord/addList")
    public R<Boolean> saveBookRecord(@RequestBody @NotEmpty(message = "请至少选择一条数据") List<GasMeterBookRecordSaveDTO> list){
        // 为了方便测试----因为前端暂时没传抄表人的参数，手动获取到赋值
        Long readMeterBookId = list.get(0).getReadMeterBook();
        ReadMeterBook book = readMeterBookApi.getById(readMeterBookId).getData();
        Long readMeterUser = book.getReadMeterUser();
        String readMeterUserName = book.getReadMeterUserName();
        //初始化number的值应该是原来总户数加1
        Integer initNUm = gasMeterBookRecordApi.getMaxNumber(readMeterBookId).getData();
        initNUm ++;
        for(int i=0;i<list.size();i++){
            list.get(i).setNumber(initNUm)
                    .setReadMeterUser(readMeterUser)
                    .setReadMeterUserName(readMeterUserName).setGasMeterType("GENERAL_GASMETER");
            initNUm++;
        }
        if(list.size()>0){
            return readMeterBookService.saveBookRecord(list);
        }
        return R.success();
    }

    @ApiOperation(value = "删除抄表册关联用户")
    @PostMapping("/gasMeterBookRecord/delete")
    public R<Boolean> deleteBookRecord(@RequestBody @NotEmpty(message = "请至少选择一条数据") List<Long> ids){
        return readMeterBookService.deleteBookRecord(ids);
    }

    @ApiOperation(value = "抄表册关联用户批量修改")
    @PostMapping("/gasMeterBookRecord/update")
    public R<Boolean> updateBookRecord(@RequestBody @NotEmpty(message = "请至少选择一条数据") List<GasMeterBookRecordUpdateDTO> list){
        return gasMeterBookRecordApi.updateBatch(list);
    }

    @ApiOperation(value = "分页查询抄表册关联客户(0-关联，1-未关联)")
    @PostMapping("/gasMeterBookRecord/page")
    public R<Page<GasMeterBookRecordVo>> pageBookRecord(@RequestBody PageParams<GasMeterBookRecordPageDTO> params){
        return readMeterBookService.pageBookRecord(params);
    }

    @ApiOperation(value = "根据id批量获取抄表册关联客户")
    @PostMapping("/gasMeterBookRecord/queryList")
    public R<List<GasMeterBookRecord>> listBookRecord(@RequestBody @NotEmpty(message = "请至少选择一条数据") List<Long> ids){
        return gasMeterBookRecordApi.queryByBookId(ids);
    };

    /**
     * 根据抄表册导出名单
     * @param book
     * @param httpResponse
     * @throws IOException
     */
    @ApiOperation(value = "导出抄表册名单")
    @PostMapping("/readMeterBook/exportReadMeterBook")
    public void exportReadMeterBook(@RequestBody ReadMeterBook book, HttpServletResponse httpResponse) throws IOException {
        // feign文件下载
        Response response = dataApi.exportReadMeterByBook(book.getId());
        ExportUtil.exportExcel(response,httpResponse,"抄表名单");
    }

    /*@ApiOperation(value = "更新抄表册关联小区")
    @PostMapping("/gasMeterBookRecord/update")
    public R<Boolean> updateBookRecord(@RequestBody GasMeterBookRecordSaveDTO gasMeterBookRecordSaveDTO){
        return saveRecord(gasMeterBookRecordSaveDTO, false);
    }*/
}
