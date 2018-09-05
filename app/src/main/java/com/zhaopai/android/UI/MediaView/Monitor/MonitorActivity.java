package com.zhaopai.android.UI.MediaView.Monitor;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;

import com.zhaopai.android.Base.IndexChildAdapter;
import com.zhaopai.android.R;
import com.zhaopai.android.UI.MediaView.FindMedia.ArtificialFindMediaFragment;
import com.zhaopai.android.UI.MediaView.FindMedia.SelfFindMediaFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MonitorActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tablayout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        //为ViewPager设置高度
        ViewGroup.LayoutParams params = viewPager.getLayoutParams();
//            params.height = getActivity().getWindowManager().getDefaultDisplay().getHeight();//800

        viewPager.setLayoutParams(params);

        setUpViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED); //tabLayout
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpViewPager(ViewPager viewPager) {
        //如果是在fragment中使用viewpager, 记得要用getChildFragmentManager, 否则你会发现fragment异常的生命周期.
        IndexChildAdapter mIndexChildAdapter = new IndexChildAdapter(this.getSupportFragmentManager());//.getSupportFragmentManager());//.getChildFragmentManager()

        mIndexChildAdapter.addFragment(MonitorFragment.newInstance(), "我的投放");
        mIndexChildAdapter.addFragment(MonitorFragment.newInstance(), "我的媒体");

        viewPager.setOffscreenPageLimit(2);//缓存view 的个数
        viewPager.setAdapter(mIndexChildAdapter);
    }

}
