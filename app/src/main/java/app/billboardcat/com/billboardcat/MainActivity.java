package app.billboardcat.com.billboardcat;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.billboardcat.com.billboardcat.UI.IndexView.IndexFragment;
import app.billboardcat.com.billboardcat.UI.IndexView.MeFragment;
import app.billboardcat.com.billboardcat.UI.IndexView.MediaFragment;
import app.billboardcat.com.billboardcat.UI.IndexView.NewsFragment;
import app.billboardcat.com.billboardcat.UI.SendView.SendMediaActivity;
import app.billboardcat.com.billboardcat.UI.SendView.SendRequireActivity;
import app.billboardcat.com.billboardcat.Widget.popmenu.PopMenu;
import app.billboardcat.com.billboardcat.Widget.popmenu.PopMenuItem;
import app.billboardcat.com.billboardcat.Widget.popmenu.PopMenuItemListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
        implements IndexFragment.OnFragmentInteractionListener,
        MediaFragment.OnFragmentInteractionListener,
        NewsFragment.OnFragmentInteractionListener,
        MeFragment.OnFragmentInteractionListener{

    @Bind(R.id.container)
    FrameLayout container;
    @Bind(R.id.line)
    View line;

    @Bind(R.id.tab_index_img)
    ImageView tabIndexImg;
    @Bind(R.id.tab_index_title)
    TextView tabIndexTitle;
    @Bind(R.id.tab_index)
    LinearLayout tabIndex;

    @Bind(R.id.tab_room_img)
    ImageView tabRecommendImg;
    @Bind(R.id.tab_room_title)
    TextView tabRecommendTitle;
    @Bind(R.id.tab_room)
    LinearLayout tabRecommend;

    @Bind(R.id.tab_plus_img)
    ImageView tabPlusImg;
    @Bind(R.id.tab_plus)
    LinearLayout tabPlus;

    @Bind(R.id.tab_message_img)
    ImageView tabRoomImg;
    @Bind(R.id.tab_message_title)
    TextView tabRoomTitle;
    @Bind(R.id.tab_message)
    LinearLayout tabRoom;

    @Bind(R.id.tab_mine_img)
    ImageView tabMineImg;
    @Bind(R.id.tab_mine_title)
    TextView tabMineTitle;
    @Bind(R.id.tab_mine)
    LinearLayout tabMine;

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TAB_INDEX_TAG = "TAB_INDEX_TAG";
    private static final String TAB_RECOMMEND_TAG = "TAB_RECOMMEND_TAG";
    private static final String TAB_ROOM_TAG = "TAB_ROOM_TAG";
    private static final String TAB_MINE_TAG = "TAB_MINE_TAG";

    private PopMenu mPopMenu;

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

        initStatusBar();

        //默认第一项选中
        mFragmentManager = getSupportFragmentManager();
        if (savedInstanceState != null) {
            mIndexFragment = (IndexFragment) mFragmentManager.findFragmentByTag(TAB_INDEX_TAG);
            mMediaFragment = (MediaFragment) mFragmentManager.findFragmentByTag(TAB_RECOMMEND_TAG);
            mNewsFragment = (NewsFragment) mFragmentManager.findFragmentByTag(TAB_ROOM_TAG);
            mMeFragment = (MeFragment) mFragmentManager.findFragmentByTag(TAB_MINE_TAG);
        }
        setTabSelection(tabIndex);

    }

    //版本 大于等于19  才会生效
    private void initStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @OnClick({R.id.tab_index, R.id.tab_room, R.id.tab_plus, R.id.tab_message, R.id.tab_mine})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_index:
                setTabSelection(tabIndex);
                break;
            case R.id.tab_room:
                setTabSelection(tabRecommend);
                break;
            case R.id.tab_plus:
                setTabSelection(tabPlus);
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
        int inactiveColorRecourse = getResources().getColor(R.color.grey);

        initSelection(inactiveColorRecourse);
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        // 开启一个Fragment事务

        switch (id) {
            case R.id.tab_index:
                // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
                hideFragments(fragmentTransaction);

                tabIndexImg.setImageResource(R.mipmap.tab1_b);
                tabIndexTitle.setTextColor(activeColorRecourse);
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

                tabRecommendImg.setImageResource(R.mipmap.tab2_b);
                tabRecommendTitle.setTextColor(activeColorRecourse);
                if (mMediaFragment == null) {
                    mMediaFragment = new MediaFragment();
                    fragmentTransaction.add(R.id.container, mMediaFragment, TAB_RECOMMEND_TAG);
                } else {
                    fragmentTransaction.show(mMediaFragment);
                }
                break;

            case R.id.tab_plus:
                initPopMenu(true);
                if (mPopMenu.isShowing()) return;
                mPopMenu.show();
                break;

            case R.id.tab_message:
                hideFragments(fragmentTransaction);

                tabRoomImg.setImageResource(R.mipmap.tab4_b);
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

                tabMineImg.setImageResource(R.mipmap.tab5_b);
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
     * 仿微博弹出菜单
     *
     * @param isProfessor 是否是大咖
     */
    private void initPopMenu(boolean isProfessor) {
        if (isProfessor) {
            mPopMenu = new PopMenu.Builder().attachToActivity(MainActivity.this)
                    .addMenuItem(new PopMenuItem("发布媒体", getResources().getDrawable(R.mipmap.tab_btn_project_nor)))
                    .addMenuItem(new PopMenuItem("发布需求", getResources().getDrawable(R.mipmap.tab_btn_demand_nor)))
//                    .addMenuItem(new PopMenuItem("Constant", getResources().getDrawable(R.mipmap.tab_btn_demand_nor)))
                    .setOnItemClickListener(new PopMenuItemListener() {
                        @Override
                        public void onItemClick(PopMenu popMenu, int position) {
                            PopMenuItemClick(position);
                        }
                    })
                    .build();
        } else {
            mPopMenu = new PopMenu.Builder().attachToActivity(MainActivity.this)
                    .addMenuItem(new PopMenuItem("发布媒体", getResources().getDrawable(R.mipmap.tab_btn_project_nor)))
                    .addMenuItem(new PopMenuItem("发布需求", getResources().getDrawable(R.mipmap.tab_btn_demand_nor)))
//                    .addMenuItem(new PopMenuItem("Constant", getResources().getDrawable(R.mipmap.tab_btn_demand_nor)))
                    .setOnItemClickListener(new PopMenuItemListener() {
                        @Override
                        public void onItemClick(PopMenu popMenu, int position) {
                            PopMenuItemClick(position);
                        }
                    })
                    .build();
        }
    }

    /**
     * @param position
     */
    private void PopMenuItemClick(int position) {
        Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(MainActivity.this, SendMediaActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(MainActivity.this, SendRequireActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 每次选中之前先清除上次的选中状态
     */
    public void initSelection(int inactiveResources) {
        tabIndexImg.setImageResource(R.mipmap.tab1_g);
        tabIndexTitle.setTextColor(inactiveResources);

        tabRecommendImg.setImageResource(R.mipmap.tab2_g);
        tabRecommendTitle.setTextColor(inactiveResources);

        tabPlusImg.setImageResource(R.mipmap.tab3_g);

        tabRoomImg.setImageResource(R.mipmap.tab4_g);
        tabRoomTitle.setTextColor(inactiveResources);

        tabMineImg.setImageResource(R.mipmap.tab5_g);
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
}
