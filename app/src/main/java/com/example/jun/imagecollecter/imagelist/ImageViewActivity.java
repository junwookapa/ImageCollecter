package com.example.jun.imagecollecter.imagelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jun.imagecollecter.R;

public class ImageViewActivity extends AppCompatActivity implements ImageListContract.View{

    private ImageListContract.Presenter mPresenter;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private Toast mToast;
    private ImageListViewAdapter mIiageListViewAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_list);
        mRecyclerView = findViewById(R.id.recycler_veiw);
        mProgressBar = findViewById(R.id.progress_bar);
        mIiageListViewAdapter = new ImageListViewAdapter(this);
        mRecyclerView.setAdapter(mIiageListViewAdapter);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemViewCacheSize(20);
        mRecyclerView.setDrawingCacheEnabled(true);
        mRecyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        mPresenter = new ImageListPresenter(this, this, mIiageListViewAdapter);
        mPresenter.init();

    }

    @Override
    public void updateUI() {
        mIiageListViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMsg(String msg) {
        if(mToast == null){
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
            mToast.show();
        }
    }

    @Override
    public void startProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.stop();
        if(mToast != null){
            mToast.cancel();
        }
    }
}
