package com.pc.test;

import com.pc.utils.HttpClientUtils;
import org.junit.Test;

/**
 * Created by Switch on 2017/4/19.
 */
public class HttpClientTest {

    @Test
    public void testDownloadPicture() {
        HttpClientUtils httpClientUtils = new HttpClientUtils();
        String url = "http://www.17cai.com/images/products/additional/original/00002_View_1.jpg";
        String dirPath = "E:/齐采网/";
        String filePath = "00002_View_1.jpg";
        httpClientUtils.downloadPicture(url,dirPath,filePath);
    }
}
