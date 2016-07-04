package com.ssc.lollpintest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Switch;

import com.ssc.lollpintest.adapter.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private NavigationView mNavigationView;
    private FloatingActionButton mFab;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private TabLayout mTableLayout;
    private ViewPager mViewPager;
    // TabLayout中的tab标题
    private String[] mTitles;
    // 填充到ViewPager中的Fragment
    private List<Fragment> mFragments;
    private MyViewPagerAdapter mAdapter;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        initData();
        configViews();

    }

    private void initData() {

        // Tab的标题采用string-array的方法保存，在res/values/arrays.xml中写
        mTitles = getResources().getStringArray(R.array.tab_titles);

        //初始化填充到ViewPager中的Fragment集合
        mFragments = new ArrayList<>();
        for (int i = 0; i < mTitles.length; i++) {
            Bundle mBundle = new Bundle();
            mBundle.putInt("flag", i);
            MyFragment mFragment = new MyFragment();
            mFragment.setArguments(mBundle);
            mFragments.add(i, mFragment);
        }
    }

    private void configViews() {

        setSupportActionBar(mToolbar);
        mNavigationView.inflateHeaderView(R.layout.header_nav);
        mNavigationView.inflateMenu(R.menu.menu_nav);
        onNavgationViewMenuItemSelected(mNavigationView);

        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToggle.syncState();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),mTitles,mFragments);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(this);

//        mToolbar.setTitleTextColor(Color.WHITE);
        mTableLayout.setTabTextColors(Color.BLACK, Color.WHITE);
        mTableLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        // 将TabLayout和ViewPager进行关联，让两者联动起来
        mTableLayout.setupWithViewPager(mViewPager);
        // 设置Tablayout的Tab显示ViewPager的适配器中的getPageTitle函数获取到的标题
//        mTableLayout.setTabsFromPagerAdapter(mAdapter);
        mCollapsingToolbarLayout.setTitle(mTitles[mViewPager.getCurrentItem()]);
//        mCollapsingToolbarLayout.setExpandedTitleColor(Color.RED);
//        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_user_background);
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                Palette.Swatch swatch = palette.getVibrantSwatch();
                if (swatch!=null){
                    mCollapsingToolbarLayout.setExpandedTitleColor(swatch.getTitleTextColor());
                    mCollapsingToolbarLayout.setCollapsedTitleTextColor(swatch.getBodyTextColor());
                    mCollapsingToolbarLayout.setBackgroundColor(swatch.getRgb());
                }
            }
        });
    }

    /**
     * 设置NavigationView中menu的item被选中后要执行的操作
     *
     * @param mNav
     */
    private void onNavgationViewMenuItemSelected(NavigationView mNav) {
        mNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                String msgString = "";

                switch (menuItem.getItemId()) {
                    case R.id.nav_menu_home:
                        msgString = (String) menuItem.getTitle();
                        break;
                    case R.id.nav_menu_categories:
                        msgString = (String) menuItem.getTitle();
                        break;
                    case R.id.nav_menu_feedback:
                        msgString = (String) menuItem.getTitle();
                        break;
                    case R.id.nav_menu_setting:
                        msgString = (String) menuItem.getTitle();
                        break;
                }

                // Menu item点击后选中，并关闭Drawerlayout
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                // android-support-design兼容包中新添加的一个类似Toast的控件。
                SnackbarUtil.show(mToolbar, msgString, 0);

                return true;
            }
        });
    }

    private void initViews() {
        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        mNavigationView = (NavigationView) findViewById(R.id.id_navigationview);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawerlayout);
        mTableLayout = (TabLayout) findViewById(R.id.id_tablayout);
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.id_collapse);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

//        mToolbar.setTitle(mTitles[position]);
        mCollapsingToolbarLayout.setTitle(mTitles[position]);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
