package com.ssc.lollpintest.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ssc.lollpintest.R;

/**
 * Created by Administrator on 2016/5/10.
 */
public class MyRecyclerViewHolder extends RecyclerView.ViewHolder {
    public  TextView mTextView;
    public  ImageView mImageView;

    public MyRecyclerViewHolder(View itemView) {
        super(itemView);
        mTextView = (TextView) itemView.findViewById(R.id.id_textview);
        mImageView = (ImageView) itemView.findViewById(R.id.iv_item);
    }

}
