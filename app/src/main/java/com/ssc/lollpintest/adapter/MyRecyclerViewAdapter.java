package com.ssc.lollpintest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ssc.lollpintest.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/10.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewHolder> {

    public final ArrayList<CharSequence> mDatas;
    public List<Integer> mHeights;
    public static int TYPE = 0;
    public static int SCROLL_STATE_DOWN = 0;
    public static int SCROLL_STATE_UP = 1;
    private  int scrollState;

    Context context;
    private final LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public MyRecyclerViewAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
        mDatas = new ArrayList<>();
        mHeights = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            mDatas.add(i+"");
        }
        for (int i = 0; i < mDatas.size(); i++) {
            mHeights.add((int) (Math.random() * 300) + 200);
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public MyRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflate = mLayoutInflater.inflate(R.layout.item_main, parent, false);
        MyRecyclerViewHolder myRecyclerViewHolder = new MyRecyclerViewHolder(inflate);

        return myRecyclerViewHolder;
    }


    @Override
    public void onViewAttachedToWindow(MyRecyclerViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        Animation animation;
        if (SCROLL_STATE_DOWN==scrollState){
            animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_load_dowm);
        }else{
            animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), R.anim.anim_load_up);
        }
        holder.itemView.startAnimation(animation);
    }

    @Override
    public void onViewDetachedFromWindow(MyRecyclerViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public void onBindViewHolder(final MyRecyclerViewHolder holder, final int position) {
        if (mOnItemClickListener!=null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.itemView,  holder.getLayoutPosition());
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickListener.onItemLongClick(holder.itemView,  holder.getLayoutPosition());
                    return true;
                }
            });
        }

        updateData(holder,  holder.getLayoutPosition());
    }

    private void updateData(MyRecyclerViewHolder holder, int position) {
        if (TYPE==1){
            ViewGroup.LayoutParams mLayoutParams = holder.mTextView.getLayoutParams();
            mLayoutParams.height = mHeights.get(position);
            holder.mTextView.setLayoutParams(mLayoutParams);
        }
        holder.mTextView.setText(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public int getScrollState() {
        return scrollState;
    }

    public void setScrollState(int scrollState) {
        this.scrollState = scrollState;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }


    public void addItem(int position,String value){
        if (mDatas!=null){
            mDatas.add(position, value);
            notifyItemInserted(position);
        }
    }
    public void removeItem(int position){
        if (mDatas!=null){
            mDatas.remove(position);
            notifyItemRemoved(position);
        }
    }

}
