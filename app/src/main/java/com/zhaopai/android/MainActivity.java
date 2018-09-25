package com.zhaopai.android;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.zhaopai.android.UI.IndexView.IndexFragment;
import com.zhaopai.android.UI.IndexView.MeFragment;
import com.zhaopai.android.UI.IndexView.MediaFragment;
import com.zhaopai.android.UI.IndexView.NewsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends AppCompatActivity
        implements
        MediaFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener{

    @BindView(R.id.container)
    FrameLayout container;
    @BindView(R.id.line)
    View line;

    @BindView(R.id.tab_index_img)
    ImageView taBindViewexImg;
    @BindView(R.id.tab_index_title)
    TextView taBindViewexTitle;
    @BindView(R.id.tab_index)
    LinearLayout taBindViewex;

    @BindView(R.id.tab_room_img)
    ImageView tabRecommendImg;
    @BindView(R.id.tab_room_title)
    TextView tabRecommendTitle;
    static LinearLayout tabRecommend;

    @BindView(R.id.tab_message_img)
    ImageView tabRoomImg;
    @BindView(R.id.tab_message_title)
    TextView tabRoomTitle;
    @BindView(R.id.tab_message)
    LinearLayout tabRoom;

    @BindView(R.id.tab_mine_img)
    ImageView tabMineImg;
    @BindView(R.id.tab_mine_title)
    TextView tabMineTitle;
    @BindView(R.id.tab_mine)
    LinearLayout tabMine;

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TAB_INDEX_TAG = "TAB_INDEX_TAG";
    private static final String TAB_RECOMMEND_TAG = "TAB_RECOMMEND_TAG";
    private static final String TAB_ROOM_TAG = "TAB_ROOM_TAG";
    private static final String TAB_MINE_TAG = "TAB_MINE_TAG";

    private FragmentManager mFragmentManager;
    private IndexFragment mIndexFragment;
    private MediaFragment mMediaFragment;
    private NewsFragment mNewsFragment;
    private MeFragment mMeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        tabRecommend = (LinearLayout) findViewById(R.id.tab_room);

        changeColor(MainActivity.this, Color.WHITE);
//        initStatusBar();
//        StatusBarUtil.setColor(MainActivity.this, Color.WHITE);
//        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, null);

        //默认第一项选中
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mIndexFragment = (IndexFragment) mFragmentManager.findFragmentByTag(TAB_INDEX_TAG);
            mMediaFragment = (MediaFragment) mFragmentManager.findFragmentByTag(TAB_RECOMMEND_TAG);
            mNewsFragment = (NewsFragment) mFragmentManager.findFragmentByTag(TAB_ROOM_TAG);
            mMeFragment = (MeFragment) mFragmentManager.findFragmentByTag(TAB_MINE_TAG);
        }
        setTabSelection(taBindViewex);

    }


    public static void changeColor(Activity paramActivity, int paramInt1) {
        if (Build.VERSION.SDK_INT >= 21) {
            paramActivity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            paramActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            paramActivity.getWindow().setStatusBarColor(paramInt1);
//            paramActivity.getWindow().setTitleColor(Color.BLACK);
            paramActivity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }



    @OnClick({R.id.tab_index, R.id.tab_room, R.id.tab_message, R.id.tab_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_index:
                setTabSelection(taBindViewex);
                break;
            case R.id.tab_room:
                setTabSelection(tabRecommend);
                break;
            case R.id.tab_message:
                setTabSelection(tabRoom);
                break;
            case R.id.tab_mine:
                setTabSelection(tabMine);
                break;
        }
    }

    private void setTabSelection(View view) {
        int id = view.getId();

        int activeColorRecourse = getResources().getColor(R.color.black);
        int inactiveColorRecourse = getResources().getColor(R.color.app_color);

        initSelection(inactiveColorRecourse);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        // 开启一个Fragment事务

        switch (id) {
            case R.id.tab_index:
                // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
                hideFragments(fragmentTransaction);

                taBindViewexImg.setImageResource(R.mipmap.tab_home_s);
                taBindViewexTitle.setTextColor(activeColorRecourse);
                if (mIndexFragment == null) {
                    // 如果Fragment为空，则创建一个并添加到界面上
                    mIndexFragment = new IndexFragment();
                    fragmentTransaction.add(R.id.container, mIndexFragment, TAB_INDEX_TAG);
                } else {
                    // 如果Fragment不为空，则直接将它显示出来
                    fragmentTransaction.show(mIndexFragment);
                }
                break;

            case R.id.tab_room:
                hideFragments(fragmentTransaction);

                tabRecommendImg.setImageResource(R.mipmap.tab_media_s);
                tabRecommendTitle.setTextColor(activeColorRecourse);
                if (mMediaFragment == null) {
                    mMediaFragment = new MediaFragment();
                    fragmentTransaction.add(R.id.container, mMediaFragment, TAB_RECOMMEND_TAG);
                } else {
                    fragmentTransaction.show(mMediaFragment);
                }
                break;

            case R.id.tab_message:
                hideFragments(fragmentTransaction);

                tabRoomImg.setImageResource(R.mipmap.tab_info_s);
                tabRoomTitle.setTextColor(activeColorRecourse);
                if (mNewsFragment == null) {
                    mNewsFragment = new NewsFragment();
                    fragmentTransaction.add(R.id.container, mNewsFragment, TAB_ROOM_TAG);
                } else {
                    fragmentTransaction.show(mNewsFragment);
                }
                break;

            case R.id.tab_mine:
                hideFragments(fragmentTransaction);

                tabMineImg.setImageResource(R.mipmap.tab_me_s);
                tabMineTitle.setTextColor(activeColorRecourse);
                if (mMeFragment == null) {
                    mMeFragment = new MeFragment();
                    fragmentTransaction.add(R.id.container, mMeFragment, TAB_MINE_TAG);
                } else {
                    fragmentTransaction.show(mMeFragment);
                }
                break;
        }
        fragmentTransaction.commit();
    }


    /**
     * 每次选中之前先清除上次的选中状态
     */
    public void initSelection(int inactiveResources) {
        taBindViewexImg.setImageResource(R.mipmap.tab_home);
        taBindViewexTitle.setTextColor(inactiveResources);

        tabRecommendImg.setImageResource(R.mipmap.tab_media);
        tabRecommendTitle.setTextColor(inactiveResources);

        tabRoomImg.setImageResource(R.mipmap.tab_info);
        tabRoomTitle.setTextColor(inactiveResources);

        tabMineImg.setImageResource(R.mipmap.tab_me);
        tabMineTitle.setTextColor(inactiveResources);
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (mIndexFragment != null) {
            transaction.hide(mIndexFragment);
        }
        if (mMediaFragment != null) {
            transaction.hide(mMediaFragment);
        }
        if (mNewsFragment != null) {
            transaction.hide(mNewsFragment);
        }
        if (mMeFragment != null) {
            transaction.hide(mMeFragment);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }


    public static void trunMediaView() {
        tabRecommend.performClick();
    }

}
