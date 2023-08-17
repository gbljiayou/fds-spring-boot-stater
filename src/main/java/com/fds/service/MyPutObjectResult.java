package com.fds.service;

import com.xiaomi.infra.galaxy.fds.result.PutObjectResult;
import lombok.Builder;
import lombok.Data;

import java.net.URI;

/**
 * 〈功能概述〉<br>
 *
 * @orojectName: fds-spring-boot-stater
 * @author: gbl
 * @date: 2021/6/22 11:11 上午
 */
@Data
@Builder
public class MyPutObjectResult {
    
    /**
     * 上传文件结果
     */
    private PutObjectResult putObjectResult;
    
    /**
     * 文件访问URI
     */
    private URI uri;
    
}
