package com.cdqckj.gmis.file.strategy;


import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.file.dto.chunk.FileChunksMergeDTO;
import com.cdqckj.gmis.file.entity.SystemFile;

/**
 * 文件分片处理策略类
 *
 * @author gmis
 * @date 2019/06/19
 */
public interface FileChunkStrategy {

    /**
     * 根据md5检测文件
     *
     * @param md5
     * @param folderId
     * @param accountId
     * @return
     */
    SystemFile md5Check(String md5, Long folderId, Long accountId);

    /**
     * 合并文件
     *
     * @param merge
     * @return
     */
    R<SystemFile> chunksMerge(FileChunksMergeDTO merge);
}
