package com.pc.test;

import com.pc.utils.POIUtils;
import org.junit.Test;

import java.util.List;

/**
 * Created by Switch on 2017/4/19.
 */
public class POITest {

    @Test
    public void testIO() {
        System.out.println(System.getProperty("user.dir"));
    }

    @Test
    public void testPOIRead() {
        try {
            List<List<String>> lists = POIUtils.readXLSXToList(System.getProperty("user.dir") + "/src/main/resources/主图信息.xlsx");
            for (List<String> list : lists) {
                for (String s : list) {
                    System.out.print(s + " ");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
