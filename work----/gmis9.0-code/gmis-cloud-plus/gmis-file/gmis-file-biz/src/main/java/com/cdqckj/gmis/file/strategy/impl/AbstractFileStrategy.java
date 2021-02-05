package com.cdqckj.gmis.file.strategy.impl;

import com.cdqckj.gmis.file.strategy.FileStrategy;
import com.cdqckj.gmis.file.utils.FileDataTypeUtil;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.file.domain.FileDeleteDO;
import com.cdqckj.gmis.file.entity.SystemFile;
import com.cdqckj.gmis.file.enumeration.IconType;
import com.cdqckj.gmis.file.properties.FileServerProperties;
import com.cdqckj.gmis.utils.DateUtils;
import com.qcloud.cos.model.COSObjectInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

import static com.cdqckj.gmis.exception.code.ExceptionCode.BASE_VALID_PARAM;


/**
 * 文件抽象策略 处理类
 *
 * @author gmis
 * @date 2019/06/17
 */
@Slf4j
public abstract class AbstractFileStrategy implements FileStrategy {

    private static final String FILE_SPLIT = ".";
    @Autowired
    protected FileServerProperties fileProperties;

    /**
     * 上传文件
     *
     * @param multipartFile
     * @return
     */
    @Override
    public SystemFile upload(MultipartFile multipartFile, String bizType) {
        try {
            if (!multipartFile.getOriginalFilename().contains(FILE_SPLIT)) {
                throw BizException.wrap(BASE_VALID_PARAM.build("缺少后缀名","Missing suffix"));
            }

            SystemFile file = SystemFile.builder()
                    .isDelete(false).submittedFileName(multipartFile.getOriginalFilename())
                    .contextType(multipartFile.getContentType())
                    .dataType(FileDataTypeUtil.getDataType(multipartFile.getContentType()))
                    .size(multipartFile.getSize())
                    .ext(FilenameUtils.getExtension(multipartFile.getOriginalFilename()))
                    .build();
            file.setIcon(IconType.getIcon(file.getExt()).getIcon());
            setDate(file);
            uploadFile(file, multipartFile, bizType);
            return file;
        } catch (Exception e) {
            log.error("e={}", e);
            throw BizException.wrap(BASE_VALID_PARAM.build("文件上传失败","File upload failed"));
        }
    }

    @Override
    public abstract void downloadById(String key, String downName);

    /**
     * 具体类型执行上传操作
     *
     * @param file
     * @param multipartFile
     * @throws Exception
     */
    protected abstract void uploadFile(SystemFile file, MultipartFile multipartFile, String bizType) throws Exception;

    private void setDate(SystemFile file) {
        LocalDateTime now = LocalDateTime.now();
        file.setCreateMonth(DateUtils.formatAsYearMonthEn(now))
                .setCreateWeek(DateUtils.formatAsYearWeekEn(now))
                .setCreateDay(DateUtils.formatAsDateEn(now));
    }

    @Override
    public boolean delete(List<FileDeleteDO> list) {
        if (list.isEmpty()) {
            return true;
        }
        boolean flag = false;
        for (FileDeleteDO file : list) {
            try {
                delete(list, file);
                flag = true;
            } catch (Exception e) {
                log.error("删除文件失败", e);
            }
        }
        return flag;
    }

    /**
     * 具体执行删除方法， 无需处理异常
     *
     * @param list
     * @param file
     * @author gmis
     * @date 2019-05-07
     */
    protected abstract void delete(List<FileDeleteDO> list, FileDeleteDO file);

    @Override
    public abstract COSObjectInputStream download(String key);

}
