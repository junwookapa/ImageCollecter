package com.example.jun.imagecollecter.imagelist;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jun.imagecollecter.Common;
import com.example.jun.imagecollecter.R;
import com.example.jun.imagecollecter.cache.Cacher;
import com.example.jun.imagecollecter.parallelio.ParallelIOUtil;
import com.example.jun.imagecollecter.parallelio.ThreadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by jun on 2018-04-10.
 */

public class ImageListViewAdapter extends RecyclerView.Adapter<PictureData.ViewHolder>
    implements ImageListContract.Model{

    private ImageListContract.Presenter mPresenter;
    private List<PictureData> mPictureData = new ArrayList<>();
    private Context mContext;
    private Cacher mCacher;

    public ImageListViewAdapter(Context context) {
        this.mContext = context;
        mCacher = new Cacher(context);
    }

    @Override
    public PictureData.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grid_element, parent, false);
        return new PictureData.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final PictureData.ViewHolder holder, int position) {
        final PictureData pictureData = mPictureData.get(position);
        if(mCacher.isExist(pictureData.getFilName())) {
            holder.setImage(mCacher.get(pictureData.getFilName()));
            holder.setImageExist(true);
        } else {
            holder.setImageExist(false);
            ThreadPool.getInstance().execute(new Runnable() {
                @Override
                public void run() {
                    final Bitmap bitmap = ParallelIOUtil.downloadBitmap(Common.BASE_URL+pictureData.getUrl());
                    mCacher.cache(pictureData.getFilName(), bitmap);
                    ((Activity)mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            holder.setImage(bitmap);
                            holder.setImageExist(true);
                        }
                    });

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.mPictureData.size();
    }

    @Override
    public void set(List<PictureData> pictureDataList) {
        this.mPictureData.addAll(pictureDataList);
    }

    @Override
    public List<PictureData> get() {
        return mPictureData;
    }


}
