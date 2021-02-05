package com.cdqckj.gmis.file.storage;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import com.cdqckj.gmis.base.MessageConstants;
import com.cdqckj.gmis.base.R;
import com.cdqckj.gmis.context.BaseContextHandler;
import com.cdqckj.gmis.exception.BizException;
import com.cdqckj.gmis.file.domain.FileDeleteDO;
import com.cdqckj.gmis.file.dto.chunk.FileChunksMergeDTO;
import com.cdqckj.gmis.file.entity.SystemFile;
import com.cdqckj.gmis.file.properties.FileServerProperties;
import com.cdqckj.gmis.file.strategy.impl.AbstractFileChunkStrategy;
import com.cdqckj.gmis.file.strategy.impl.AbstractFileStrategy;
import com.cdqckj.gmis.file.utils.FileDataTypeUtil;
import com.cdqckj.gmis.lang.L18nEnum;
import com.cdqckj.gmis.lang.L18nMenuContainer;
import com.cdqckj.gmis.utils.StrPool;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.*;
import com.qcloud.cos.region.Region;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URL;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.cdqckj.gmis.utils.DateUtils.DEFAULT_MONTH_FORMAT_SLASH;

/**
 * 腾讯cos
 *
 * @author gmis
 * @date 2019/08/09
 */
@EnableConfigurationProperties(FileServerProperties.class)
@Configuration
@Slf4j
@ConditionalOnProperty(name = "gmis.file.type", havingValue = "TENCENT_COS")
public class TencentCosAutoConfigure {

    @Service
    public static class TencentServiceImpl extends AbstractFileStrategy {

        public TencentServiceImpl(FileServerProperties fileProperties) {
            this.fileProperties = fileProperties;
        }

        public TencentServiceImpl() {

        }

        private  FileServerProperties.Tencent tencent;
        private  String secretId;
        private  String secretKey;
        private  String reginName;
        private  String bucketName;
        private  String path;
        private  String storagePath;

        public void init(){
            if(null==tencent){
                this.tencent = fileProperties.getTencent();
                this.secretKey = tencent.getSecretKey();
                this.secretId = tencent.getSecretId();
                this.reginName = tencent.getReginName();
                this.bucketName = tencent.getBucketName();
                this.path = tencent.getPath();
                this.storagePath = fileProperties.getStoragePath();
            }
        }

        public COSClient getCOSClient(){
            init();
            // 1 初始化用户身份信息(secretId, secretKey)
            COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
            // 2 设置bucket的区域, COS地域的简称请参照 https://cloud.tencent.com/document/product/436/6224
            ClientConfig clientConfig = new ClientConfig(new Region(reginName));
            // 3 生成cos客户端
            return new COSClient(cred, clientConfig);
        }

        @Override
        public void downloadById(String key, String downName) {
            init();
            String downPath = storagePath;
            downPath += "/"+downName;
            File downFile = new File(downPath); //自定义下载文件路径或直接填key
            COSClient cosclient = getCOSClient();
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            ObjectMetadata downObjectMeta = cosclient.getObject(getObjectRequest, downFile);
        }

        @Override
        protected void uploadFile(SystemFile file, MultipartFile multipartFile, String bizType) throws Exception {
            if(multipartFile != null){
                String tenant = BaseContextHandler.getTenant();
                String oldFileName = multipartFile.getOriginalFilename();
                String eName = oldFileName.substring(oldFileName.lastIndexOf("."));
                String newFileName = UUID.randomUUID()+eName;
                COSClient cosclient = getCOSClient();
                File localFile = new File(newFileName);
                try {
                    //localFile = File.createTempFile("temp",null);
                    FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), localFile);
                    multipartFile.transferTo(localFile);
                    // 指定要上传到 COS 上的路径
                    String relativePath = Paths.get(tenant, bizType).toString().replaceAll("\\\\",StrPool.SLASH);
                    //LocalDate.now().format(DateTimeFormatter.ofPattern(DEFAULT_MONTH_FORMAT_SLASH))
                    newFileName = relativePath+StrPool.SLASH+newFileName;
                    String key = StrPool.SLASH+newFileName;
                    PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, localFile);
                    PutObjectResult putObjectResult = cosclient.putObject(putObjectRequest);
                    /*Date expiration = new Date(System.currentTimeMillis() + 5 * 60 * 10000);
                    URL url = cosclient.generatePresignedUrl(bucketName, key, expiration);*/
                    String etag = putObjectResult.getETag();
                    //localFile.deleteOnExit();
                    FileUtils.forceDelete(localFile);

                    String urlPath = path +newFileName;
                    file.setUrl(StrUtil.replace(urlPath, "\\\\", StrPool.SLASH));
                    file.setFilename(eName);
                    file.setRelativePath(urlPath);
                    file.setPath(newFileName);
                } catch (IOException e) {
                    log.error("文件上传报错"+e.getMessage());
                    throw new BizException(-1, "文件上传报错","File upload error");
                }finally {
                    // 关闭客户端(关闭后台线程)
                    cosclient.shutdown();
                }
            }
        }

        @Override
        protected void delete(List<FileDeleteDO> list, FileDeleteDO file) {
            if(file.getFile()){
                COSClient cosclient = getCOSClient();
                cosclient.deleteObject(bucketName, file.getPath());
            }
        }

        @Override
        public COSObjectInputStream download(String key){
            COSClient cosclient = getCOSClient();
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);//根据桶和key获取文件请求
            System.out.println("文件请求："+getObjectRequest);
            COSObject cosObject = cosclient.getObject(getObjectRequest);
            COSObjectInputStream cosObjectInput = cosObject.getObjectContent();
            System.out.println("输出流："+cosObjectInput);
            return cosObjectInput;
        }

        public ObjectMetadata downloadDto(String key,String path){
            File downFile = new File(path); //自定义下载文件路径或直接填key
            COSClient cosclient = getCOSClient();
            GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, key);
            ObjectMetadata downObjectMeta = cosclient.getObject(getObjectRequest, downFile);
            return downObjectMeta;
        }


    }



    @Service
    public static class TencentChunkServiceImpl extends AbstractFileChunkStrategy {
        /**
         * 为上传的文件生成随机名称
         *
         * @param originalName 文件的原始名称，主要用来获取文件的后缀名
         * @return
         */
        private String randomFileName(String originalName) {
            String[] ext = StrUtil.split(originalName, ".");
            return UUID.randomUUID().toString() + StrPool.DOT + ext[ext.length - 1];
        }

        @Override
        protected void copyFile(SystemFile file) {
            String inputFile = Paths.get(fileProperties.getStoragePath(), file.getRelativePath(),
                    file.getFilename()).toString();

            String filename = randomFileName(file.getFilename());
            String outputFile = Paths.get(fileProperties.getStoragePath(), file.getRelativePath(), filename).toString();

            try {
                FileUtil.copy(inputFile, outputFile, true);
            } catch (IORuntimeException e) {
                log.error("复制文件异常", e);
                throw new BizException((String) L18nMenuContainer
                        .getContainerByType((Integer) L18nMenuContainer
                                .LANG_MAP.get(L18nEnum.LANG.getL18nDesc()+ BaseContextHandler.getTenant()))
                        .get(MessageConstants.COPY_FILE_ERROR));
            }

            file.setFilename(filename);
            String url = file.getUrl();
            String newUrl = StrUtil.subPre(url, StrUtil.lastIndexOfIgnoreCase(url, StrPool.SLASH) + 1);
            file.setUrl(newUrl + filename);
        }

        @Override
        protected R<SystemFile> merge(List<java.io.File> files, String path, String fileName, FileChunksMergeDTO info) throws IOException {
            //创建合并后的文件
            log.info("path={},fileName={}", path, fileName);
            java.io.File outputFile = new java.io.File(Paths.get(path, fileName).toString());
            if (!outputFile.exists()) {
                boolean newFile = outputFile.createNewFile();
                if (!newFile) {
                    return R.fail((String) L18nMenuContainer
                            .getContainerByType((Integer) L18nMenuContainer
                                    .LANG_MAP.get(L18nEnum.LANG.getL18nDesc()+BaseContextHandler.getTenant()))
                            .get(MessageConstants.CREATE_FILE_FAILED));
                }
                try (FileChannel outChannel = new FileOutputStream(outputFile).getChannel()) {
                    //同步nio 方式对分片进行合并, 有效的避免文件过大导致内存溢出
                    for (java.io.File file : files) {
                        try (FileChannel inChannel = new FileInputStream(file).getChannel()) {
                            inChannel.transferTo(0, inChannel.size(), outChannel);
                        } catch (FileNotFoundException ex) {
                            log.error("文件转换失败", ex);
                            return R.fail((String) L18nMenuContainer
                                    .getContainerByType((Integer) L18nMenuContainer
                                            .LANG_MAP.get(L18nEnum.LANG.getL18nDesc()+BaseContextHandler.getTenant()))
                                    .get(MessageConstants.CONVERSION_FILE_FAILED));
                        }
                        //删除分片
                        if (!file.delete()) {
                            log.error("分片[" + info.getName() + "=>" + file.getName() + "]删除失败");
                        }
                    }
                } catch (FileNotFoundException e) {
                    log.error("文件输出失败", e);
                    return R.fail((String) L18nMenuContainer
                            .getContainerByType((Integer) L18nMenuContainer
                                    .LANG_MAP.get(L18nEnum.LANG.getL18nDesc()+BaseContextHandler.getTenant()))
                            .get(MessageConstants.OUT_FILE_FAILED));
                }

            } else {
                log.warn("文件[{}], fileName={}已经存在", info.getName(), fileName);
            }

            String relativePath = FileDataTypeUtil.getRelativePath(Paths.get(fileProperties.getStoragePath()).toString(), outputFile.getAbsolutePath());
            log.info("relativePath={}, getStoragePath={}, getAbsolutePath={}", relativePath, fileProperties.getStoragePath(), outputFile.getAbsolutePath());
            String url = new StringBuilder(fileProperties.getUriPrefix())
                    .append(relativePath)
                    .append(StrPool.SLASH)
                    .append(fileName)
                    .toString();
            SystemFile filePo = SystemFile.builder()
                    .relativePath(relativePath)
                    .url(StringUtils.replace(url, "\\\\", StrPool.SLASH))
                    .build();
            return R.success(filePo);
        }
    }
}
