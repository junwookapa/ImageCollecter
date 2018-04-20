package com.example.jun.imagecollecter.imagelist;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jun.imagecollecter.Common;
import com.example.jun.imagecollecter.R;

/**
 * Created by jun on 2018-04-10.
 */

public class PictureData {
    private String url;
    private String filName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFilName() {
        return filName;
    }

    public void setFilName(String filName) {
        this.filName = filName;
    }

    public static PictureData fromURL(String url){
        PictureData pictureData = new PictureData();
        pictureData.setUrl(url);
        pictureData.setFilName(trimSeparator(url));
        return pictureData;
    }

    public static String trimSeparator(String str){
        int index = str.lastIndexOf('/');
        return index > 0 ? str.substring(index+ 1) : str;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView mImageView;
        private TextView mTextView;


        public ViewHolder(View itemView) {
            super(itemView);
            this.mImageView = itemView.findViewById(R.id.imageView);
            this.mTextView = itemView.findViewById(R.id.textView);
        }

        public void setImage(Bitmap bitmap){
            mImageView.setImageBitmap(bitmap);
        }
        public void setImageExist(boolean isVisible){
            if(isVisible){
                mImageView.setVisibility(View.VISIBLE);
                mTextView.setVisibility(View.INVISIBLE);
            }else{
                mImageView.setVisibility(View.INVISIBLE);
                mTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
