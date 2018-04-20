package com.example.jun.imagecollecter.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import com.example.jun.imagecollecter.Common;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by jun on 2018-04-06.
 */

public class Crawler {

    public static ArrayList<String> getURLs() {
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(Common.THUMNAIL_URL)
                    .timeout(Common.TIME_OUT)
                    .get();

            Elements div = doc.select("div.gallery-item-group img[src]");
            ArrayList<String> list = new ArrayList<>();
            for(Element e:div){
                try {
                    String src = e.attr("src");
                    if(null != src){
                        list.add(e.attr("src"));
                    }

                }catch (NullPointerException ee){
                    ee.printStackTrace();
                }
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Flowable<ArrayList<String>> getFlowable(){
        return Flowable.create(new FlowableOnSubscribe<ArrayList<String>>() {
            @Override
            public void subscribe(FlowableEmitter<ArrayList<String>> emitter) throws Exception {
                emitter.onNext(getURLs());
                emitter.onComplete();
            }
        }, BackpressureStrategy.BUFFER).subscribeOn(Schedulers.io());
    }

}
