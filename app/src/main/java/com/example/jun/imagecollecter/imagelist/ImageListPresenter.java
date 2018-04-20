package com.example.jun.imagecollecter.imagelist;

import android.content.Context;

import com.example.jun.imagecollecter.R;
import com.example.jun.imagecollecter.http.Crawler;
import com.example.jun.imagecollecter.parallelio.ThreadPool;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.FlowableSubscriber;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Created by jun on 2018-04-10.
 */

public class ImageListPresenter implements ImageListContract.Presenter{
    private Context mContext;
    private ImageListContract.View mView;
    private ImageListContract.Model mModel;

    public ImageListPresenter(Context context, ImageListContract.View view, ImageListContract.Model model){
        mContext = context;
        mView = view;
        mModel = model;
    }

    @Override
    public void init() {
        Crawler.getFlowable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FlowableSubscriber<ArrayList<String>>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        s.request(Long.MAX_VALUE);
                        mView.startProgress();
                    }

                    @Override
                    public void onNext(ArrayList<String> strings) {
                        List<PictureData> pictureDataList = new ArrayList<>();
                        for(String str : strings){
                            pictureDataList.add(PictureData.fromURL(str));
                        }
                        mModel.set(pictureDataList);

                    }

                    @Override
                    public void onError(Throwable t) {
                        mView.stopProgress();
                        mView.showMsg(mContext.getString(R.string.error_msg, t.getMessage()));
                    }

                    @Override
                    public void onComplete() {
                        mView.updateUI();
                        mView.stopProgress();
                    }
                });
    }

    @Override
    public void stop() {
        ThreadPool.getInstance().stop();
    }
}
