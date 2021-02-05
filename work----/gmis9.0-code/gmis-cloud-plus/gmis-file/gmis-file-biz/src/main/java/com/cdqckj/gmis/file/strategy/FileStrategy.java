package com.cdqckj.gmis.file.strategy;

import com.cdqckj.gmis.file.domain.FileDeleteDO;
import com.cdqckj.gmis.file.entity.SystemFile;
import com.qcloud.cos.model.COSObjectInputStream;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件策略接口
 *
 * @author gmis
 * @date 2019/06/17
 */
public interface FileStrategy {
    /**
     * 文件上传
     *
     * @param file 文件
     * @return 文件对象
     * @author gmis
     * @date 2019-05-06 16:38
     */
    SystemFile upload(MultipartFile file, String bizType);

    /**
     * 删除源文件
     *
     * @param list 列表
     * @return
     * @author gmis
     * @date 2019-05-07 11:41
     */
    boolean delete(List<FileDeleteDO> list);

    void downloadById(String key, String downName);

    COSObjectInputStream download(String key);

}
