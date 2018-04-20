package com.example.jun.imagecollecter;


import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by jun on 2018-04-06.
 */

public class Tester {
    @Test
    public void doTest(){
        Map<Integer, String> mLruMap = Collections
                .synchronizedMap(new LinkedHashMap<Integer, String>(1024));
        mLruMap.put(1, "hello1");
        mLruMap.put(2, "hello2");
        mLruMap.put(3, "hello3");
        mLruMap.put(4, "hello4");
        String fileName = mLruMap.get(2);
        mLruMap.remove(2);
        mLruMap.put(2, fileName);
        String str ="";
        for(int i : mLruMap.keySet()){
            str+=i+", ";
        }
        System.out.println(str);
    }
}
