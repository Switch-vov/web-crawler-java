package com.pc.test;

import com.pc.utils.HttpClientUtils;
import com.pc.utils.POIUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Switch on 2017/4/19.
 */
public class DownloadPicture {
    private static final String MAIN_PICTURE_FILEPATH = System.getProperty("user.dir") + "/src/main/resources/主图信息.xlsx";
    private static final String DETAIL_PICTURE_FILEPATH = System.getProperty("user.dir") + "/src/main/resources/详情页信息.xlsx";
    private static final String WEBSITE_17_CAI = "http://www.17cai.com";
    private static final String DIR_PATH = "E:/齐采网/";
    private static final String DIR_PATH_F = "F:/齐采网/";

    @Test
    public void downloadMainPicture() {
        try {
            // 获取excel中的信息
            List<List<String>> table = POIUtils.readXLSXToList(MAIN_PICTURE_FILEPATH);

            for (int i = 1; i < table.size(); i++) {
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

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void downloadDetailPicture() {
        try {
            // 获取excel中的信息
            List<List<String>> table = POIUtils.readXLSXToList(DETAIL_PICTURE_FILEPATH);

            for (int i = 1; i < table.size(); i++) {
                List<String> row = table.get(i);
                String productId = row.get(0);
                if (productId.contains(".")) {
                    productId = productId.substring(0, productId.indexOf("."));
                }
                if (row.size() > 1) {
                    String description = row.get(1);
                    downloadDetailPicture(productId, description);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void downloadDetailPicture(String productId, String description) {
        String[] split = description.split("[',\"]");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (s.toLowerCase().contains("images") || s.toLowerCase().contains("http")) {
                list.add(s);
            }
        }
        System.out.println(productId + "：");
        for (String imageURL : list) {
            // 不是完整的Http地址
            if (!imageURL.toLowerCase().startsWith("http")) {
                imageURL = WEBSITE_17_CAI + imageURL;
            }
            System.out.println(imageURL);
            String dirPath = DIR_PATH_F + productId + "/";
            String fileName = imageURL.substring(imageURL.lastIndexOf("/") + 1);
            System.out.println("Dir Path：" + dirPath + " FileName：" + fileName);
            new HttpClientUtils().downloadPicture(imageURL,dirPath,fileName);
        }
    }
}
