package com.cdqckj.gmis.file.storage;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.cdqckj.gmis.file.dao.AttachmentMapper;
import com.cdqckj.gmis.file.strategy.impl.AbstractFileChunkStrategy;
import com.cdqckj.gmis.file.strategy.impl.AbstractFileStrategy;
import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.service.AppendFileStorageClient;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.file.domain.FileDeleteDO;
import com.cdqckj.gmis.file.dto.chunk.FileChunksMergeDTO;
import com.cdqckj.gmis.file.entity.SystemFile;
import com.cdqckj.gmis.file.properties.FileServerProperties;
import com.qcloud.cos.model.COSObjectInputStream;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * FastDFS配置
 *
 * @author gmis
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Configuration
@Slf4j
@ConditionalOnProperty(name = "gmis.file.type", havingValue = "FAST_DFS")
public class FastDfsAutoConfigure {
    @Service
    @DS("#thread.tenant")
    public class FastDfsServiceImpl extends AbstractFileStrategy {
        @Autowired
        private FastFileStorageClient storageClient;
        @Autowired
        private AttachmentMapper attachmentMapper;

        @Override
        public void downloadById(String key, String downName) {

        }

        @Override
        protected void uploadFile(SystemFile file, MultipartFile multipartFile, String bizType) throws Exception {
            StorePath storePath = storageClient.uploadFile(multipartFile.getInputStream(), multipartFile.getSize(), file.getExt(), null);
            file.setUrl(fileProperties.getUriPrefix() + storePath.getFullPath());
            file.setGroup(storePath.getGroup());
            file.setPath(storePath.getPath());
        }

        @Override
        protected void delete(List<FileDeleteDO> list, FileDeleteDO file) {
            if (file.getFile()) {
                List<Long> ids = list.stream().mapToLong(FileDeleteDO::getId).boxed().collect(Collectors.toList());
                Integer count = attachmentMapper.countByGroup(ids, file.getGroup(), file.getPath());
                if (count > 0) {
                    return;
                }
            }
            storageClient.deleteFile(file.getGroup(), file.getPath());
        }

        @Override
        public COSObjectInputStream download(String key) {
            return null;
        }

    }

    @Service
    public class FastDfsChunkServiceImpl extends AbstractFileChunkStrategy {
        @Autowired
        protected AppendFileStorageClient storageClient;

        @Override
        protected void copyFile(SystemFile file) {
            // 由于大文件下载然后在上传会内存溢出， 所以 FastDFS 不复制，删除时通过业务手段
//            DownloadByteArray callback = new DownloadByteArray();
//            byte[] content = storageClient.downloadFile(file.getGroup(), file.getPath(), callback);
//            InputStream in = new ByteArrayInputStream(content);
//            StorePath storePath = storageClient.uploadFile(file.getGroup(), in, file.getSize(), file.getExt());
//            file.setUrl(fileProperties.getUriPrefix() + storePath.getFullPath());
//            file.setGroup(storePath.getGroup());
//            file.setPath(storePath.getPath());
        }

        @Override
        protected R<SystemFile> merge(List<java.io.File> files, String path, String fileName, FileChunksMergeDTO info) throws IOException {
            StorePath storePath = null;

            long start = System.currentTimeMillis();
            for (int i = 0; i < files.size(); i++) {
                java.io.File file = files.get(i);

                FileInputStream in = FileUtils.openInputStream(file);
                if (i == 0) {
                    storePath = storageClient.uploadAppenderFile(null, in,
                            file.length(), info.getExt());
                } else {
                    storageClient.appendFile(storePath.getGroup(), storePath.getPath(),
                            in, file.length());
                }
            }
            if (storePath == null) {
                return R.fail("上传失败","Upload failed");
            }

            long end = System.currentTimeMillis();
            log.info("上传耗时={}", (end - start));
            String url = new StringBuilder(fileProperties.getUriPrefix())
                    .append(storePath.getFullPath())
                    .toString();
            SystemFile filePo = SystemFile.builder()
                    .url(url)
                    .group(storePath.getGroup())
                    .path(storePath.getPath())
                    .build();
            return R.success(filePo);
        }
    }
}
