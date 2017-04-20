package com.pc.concurrent;

import com.pc.utils.HttpClientUtils;

import java.util.List;

/**
 * Created by Switch on 2017/4/20.
 */
public class DownloadTask implements Runnable {
    private static final String WEBSITE_17_CAI = "http://www.17cai.com";
    private static final String DIR_PATH = "G:/齐采网/";


    private List<List<String>> table;
    private int first;
    private int last;

    public DownloadTask(List<List<String>> table, int first, int last) {
        this.table = table;
        this.first = first;
        this.last = last;
    }

    @Override
    public void run() {
        for (int i = first; i < last; i++) {
            List<String> row = table.get(i);
            String productId = row.get(0);
            String originalImageURL = row.get(4);
            // 不是完整的Http地址
            if (!originalImageURL.toLowerCase().startsWith("http")) {
                originalImageURL = WEBSITE_17_CAI + originalImageURL;
            }
            // System.out.println("Product Id：" + productId + " Image URL：" + originalImageURL);
            String dirPath = DIR_PATH + productId + "/";
            String fileName = originalImageURL.substring(originalImageURL.lastIndexOf("/") + 1);
            System.out.println("Dir Path：" + dirPath + " FileName：" + fileName);
            new HttpClientUtils().downloadPicture(originalImageURL, dirPath, fileName);
        }
    }
}
