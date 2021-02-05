package com.cdqckj.gmis.file.enumeration;

/**
 * 文件 存储类型 枚举
 *
 * @author gmis
 * @date 2019/05/06
 */
public enum FileStorageType {
    /**
     * 本地
     */
    LOCAL,
    /**
     * FastDFS
     */
    FAST_DFS,
    ALI,
    QINIU,
    TENCENT_COS
    ;

    public boolean eq(FileStorageType type) {
        for (FileStorageType t : FileStorageType.values()) {
            return t.equals(type);
        }
        return false;
    }
}