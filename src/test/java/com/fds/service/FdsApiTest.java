package com.fds.service;

import com.fds.FdsSpringBootStaterApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.net.URI;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {FdsSpringBootStaterApplication.class, FdsApiTest.class})
public class FdsApiTest {
    
    @Resource
    
    private FdsApi fdsApi;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    
    @Test
    public void testGetObjectURI() throws Exception {
        URI result = fdsApi.getObjectURI("fileName");
        Assert.assertEquals(null, result);
    }
    
    @Test
    public void testPutObjectByFile() throws Exception {
        MyPutObjectResult result = fdsApi.putObjectByFile("bucketName",
                new File(getClass().getResource("/com/xiaomi/fds/service/PleaseReplaceMeWithTestFile.txt").getFile()));
        Assert.assertEquals(new MyPutObjectResult(null, null), result);
    }
    
    @Test
    public void testPutObjectByInputStream() throws Exception {
        File file = new File("/Users/gbl/Desktop/WechatIMG335.png");
        MyPutObjectResult result = fdsApi.putObjectByInputStream("qrcodes-xmstore-trade", file.getName(), new FileInputStream(file));
    }
}