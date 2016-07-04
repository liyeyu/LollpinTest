package com.ssc.lollpintest;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ssc.lollpintest.adapter.MyRecyclerViewAdapter;
import com.ssc.lollpintest.adapter.MyRecyclerViewHolder;
import com.ssc.lollpintest.view.ImageActivity;

/**
 * Created by Administrator on 2016/5/10.
 */
public class MyFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private int mFlag;
    private View mView;
    private TextView mTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private static final int VERTICAL_LIST = 0;
    private static final int HORIZONTAL_LIST = 1;
    private static final int VERTICAL_GRID = 2;
    private static final int HORIZONTAL_GRID = 3;
    private static final int STAGGERED_GRID = 4;
    private static final int SPAN_COUNT = 2;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter mRecyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mFlag = getArguments().getInt("flag");

        mSwipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.id_swiperefreshlayout);
        mRecyclerView = (RecyclerView) mView.findViewById(R.id.id_recyclerview);
        mSwipeRefreshLayout.setColorSchemeColors(Color.parseColor("#3F56B5"), Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        configRecyclerView();
    }

    private void configRecyclerView() {

        switch (mFlag) {
            case VERTICAL_LIST:
                mLayoutManager =
                        new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_LIST:
                mLayoutManager =
                        new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                break;
            case VERTICAL_GRID:
                mLayoutManager =
                        new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.VERTICAL, false);
                break;
            case HORIZONTAL_GRID:
                mLayoutManager =
                        new GridLayoutManager(getActivity(), SPAN_COUNT, GridLayoutManager.HORIZONTAL, false);
                break;
            case STAGGERED_GRID:
                mLayoutManager =
                        new StaggeredGridLayoutManager(SPAN_COUNT, StaggeredGridLayoutManager.VERTICAL);
                break;
        }


        mRecyclerViewAdapter = new MyRecyclerViewAdapter(getActivity());
        mRecyclerViewAdapter.setOnItemClickListener(new MyRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                SnackbarUtil.show(mRecyclerView, "position:" + position, 0);
                Intent intent = new Intent(getActivity(), ImageActivity.class);
                intent.putExtra("text", mRecyclerViewAdapter.mDatas.get(position));
                startActivityForAnim(view, intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                mRecyclerViewAdapter.removeItem(position);
            }
        });
        if (mFlag != STAGGERED_GRID) {
            mRecyclerViewAdapter.TYPE = 1;
        }else{
            mRecyclerViewAdapter.TYPE = 0;
        }

        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mRecyclerViewAdapter.setScrollState(dy>=0?MyRecyclerViewAdapter.SCROLL_STATE_DOWN:MyRecyclerViewAdapter.SCROLL_STATE_UP);
            }
        });

        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecyclerViewAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }

    /**
     * 转场动画
     * @param view
     * @param intent
     */
    public void startActivityForAnim(View view,Intent intent){

         MyRecyclerViewHolder viewHolder = (MyRecyclerViewHolder) mRecyclerView.getChildViewHolder(view);
//        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
//        ActivityOptionsCompat optionsCompat
//                = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(view, BitmapFactory.decodeResource(getResources(),R.drawable.author), 0, 0);
//        ActivityOptionsCompat optionsCompat
//                = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),view,getString(R.string.app_name));

        Pair<View, String> pair1 = Pair.create((View)viewHolder.mImageView, getString(R.string.app_name));
        Pair<View, String> pair2 = Pair.create((View)viewHolder.mTextView, getString(R.string.open));
        ActivityOptionsCompat optionsCompat
                = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(),pair1,pair2);
        ActivityCompat.startActivity(getActivity(), intent, optionsCompat.toBundle());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.frag_main, container, false);
        return mView;
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(false);
                if (mFlag != STAGGERED_GRID) {
                    mRecyclerViewAdapter.TYPE = 1;
                }else {
                    mRecyclerViewAdapter.TYPE = 0;
                }
                int temp = (int) (Math.random() * 10);
                mRecyclerViewAdapter.addItem(0, "new"+temp);
            }
        }, 3000);
    }
}



