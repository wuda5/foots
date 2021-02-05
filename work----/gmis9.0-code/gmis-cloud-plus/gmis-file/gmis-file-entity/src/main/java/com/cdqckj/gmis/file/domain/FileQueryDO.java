package com.cdqckj.gmis.file.domain;


import com.cdqckj.gmis.file.entity.SystemFile;
import lombok.Data;

/**
 * 文件查询 DO
 *
 * @author gmis
 * @date 2019/05/07
 */
@Data
public class FileQueryDO extends SystemFile {
    private SystemFile parent;

}
