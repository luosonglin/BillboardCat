package com.zhaopai.android.UI.MediaView.FindMedia;

import android.app.Dialog;
import android.os.Bundle;
import android.app.Activity;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.zhaopai.android.Base.IndexChildAdapter;
import com.zhaopai.android.Network.Entity.FindMedia;
import com.zhaopai.android.Network.HttpData.HttpData;
import com.zhaopai.android.R;
import com.zhaopai.android.Util.DateUtils;
import com.zhaopai.android.Util.ToastUtils;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class FindMediaActivity extends FragmentActivity {

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ImageView searchLiveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_media);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

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

        mIndexChildAdapter.addFragment(ArtificialFindMediaFragment.newInstance(), "媒体地图");
        mIndexChildAdapter.addFragment(SelfFindMediaFragment.newInstance(), "媒介专区");

        viewPager.setOffscreenPageLimit(2);//缓存view 的个数
        viewPager.setAdapter(mIndexChildAdapter);
    }


}
