package com.fds.service;

import com.fds.config.FdsPropeties;
import com.xiaomi.infra.galaxy.fds.client.GalaxyFDS;
import com.xiaomi.infra.galaxy.fds.client.exception.GalaxyFDSClientException;
import com.xiaomi.infra.galaxy.fds.result.PutObjectResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Map;

/**
 * FDS使用帮助类
 *
 * @orojectName: fds-spring-boot-stater
 * @author: gbl
 * @date: 2021/6/22 11:02 上午
 */
@Component
public class FdsApi {
    
    private static final Logger log = LoggerFactory.getLogger(FdsApi.class);
    
    @Autowired
    private FdsPropeties fdsPropeties;
    
    @Autowired
    private GalaxyFDS fdsClient;
    
    /**
     * getUrl,获取文件地址
     */
    public final URI getObjectURI(String fileName) {
        try {
            String bucketName = fdsPropeties.getBucketName();
            URI url = fdsClient.generateDownloadObjectUri(bucketName, fileName);
            return url;
        } catch (GalaxyFDSClientException e) {
            return null;
        }
    }
    
    /**
     * 上传文件
     */
    public final MyPutObjectResult putObjectByFile(String suffix, File file) {
        try {
            String bucketName = fdsPropeties.getBucketName() + ":" + suffix;
            String fileName = file.getName();
            PutObjectResult putObjectResult = fdsClient.putObject(bucketName, fileName, file);
            fdsClient.setPublic(bucketName, putObjectResult.getObjectName());
            URI uri = fdsClient.generateDownloadObjectUri(bucketName, putObjectResult.getObjectName());
            MyPutObjectResult myPutObjectResult = MyPutObjectResult.builder().putObjectResult(putObjectResult).uri(uri)
                    .build();
            return myPutObjectResult;
        } catch (GalaxyFDSClientException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 上传文件
     */
    public final MyPutObjectResult putObjectByInputStream(String bucketName, String objectName,
            InputStream inputStream) {
        try {
            log.info("FDS上传文件[putObjectByInputStream],bucketName={},objectName={}", bucketName, objectName);
            //1:bucket上传文件
            PutObjectResult putObjectResult = fdsClient.putObject(bucketName, objectName, inputStream, null);
            //2:设置所有用户可访问该文件
            fdsClient.setPublic(bucketName, putObjectResult.getObjectName());
            //3:生成文件访问url
            URI uri = fdsClient.generateDownloadObjectUri(bucketName, putObjectResult.getObjectName());
            log.info("putObjectByInputStream success");
            return MyPutObjectResult.builder().putObjectResult(putObjectResult).uri(uri).build();
        } catch (GalaxyFDSClientException e) {
            log.error("FDS上传文件异常:", e);
            return null;
        } catch (Exception e) {
            log.error("FDS上传文件其他异常:", e);
            return null;
        }
    }
    
    /**
     * 删除文件
     *
     * @param objectNameList
     * @return
     */
    public final List<Map<String, Object>> deleteObjects(List<String> objectNameList) throws GalaxyFDSClientException {
        return fdsClient.deleteObjects(fdsPropeties.getBucketName(), objectNameList);
    }
}
