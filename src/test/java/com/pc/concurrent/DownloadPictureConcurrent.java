package com.pc.concurrent;

import com.pc.utils.POIUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Switch on 2017/4/20.
 */
public class DownloadPictureConcurrent {
    private static final String MAIN_PICTURE_FILEPATH = System.getProperty("user.dir") + "/src/main/resources/主图信息.xlsx";
    private static final String DETAIL_PICTURE_FILEPATH = System.getProperty("user.dir") + "/src/main/resources/详情页信息.xlsx";

    public static void main(String[] args) {
        // 获取excel中的信息
        try {
            List<List<String>> table = POIUtils.readXLSXToList(MAIN_PICTURE_FILEPATH);

            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newCachedThreadPool();
            for (int i = 1; i < table.size(); i+= 100) {
                DownloadTask task = new DownloadTask(table, i, i + 100);
                executor.execute(task);
            }
            executor.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
