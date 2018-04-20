package com.example.jun.imagecollecter.imagelist;

import java.util.List;

/**
 * Created by jun on 2018-04-10.
 */

public interface ImageListContract {
    interface View{
        void updateUI();
        void showMsg(String msg);
        void startProgress();
        void stopProgress();

    }

    interface Presenter{
        void init();
        void stop();
    }

    interface Model{
        void set(List<PictureData> stringList);
        List<PictureData> get();

    }

}
