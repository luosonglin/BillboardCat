package com.zhaopai.android.UI.IndexView;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;


import com.zhaopai.android.R;

public class MeFragment extends Fragment {
    private ScrollView scrollView;
    private ImageView img;
    private ImageView setting;
    private ImageView modifyUserinfo;
    private TextView name;
    private TextView type;
    private LinearLayout myMedia;
    private LinearLayout myLive;
    private LinearLayout myPutIn;
    private LinearLayout myCollect;
    private LinearLayout myOrder;
    private LinearLayout myEnroll;
    private ImageView headIc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_me, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scrollView = (ScrollView) view.findViewById(R.id.scrollView);
        img = (ImageView) view.findViewById(R.id.img);
        setting = (ImageView) view.findViewById(R.id.setting);
        modifyUserinfo = (ImageView) view.findViewById(R.id.modify_userinfo);
        name = (TextView) view.findViewById(R.id.name);
        type = (TextView) view.findViewById(R.id.type);
        myMedia = (LinearLayout) view.findViewById(R.id.my_media);
        myLive = (LinearLayout) view.findViewById(R.id.my_live);
        myPutIn = (LinearLayout) view.findViewById(R.id.my_put_in);
        myCollect = (LinearLayout) view.findViewById(R.id.my_collect);
        myOrder = (LinearLayout) view.findViewById(R.id.my_order);
        myEnroll = (LinearLayout) view.findViewById(R.id.my_enroll);
        headIc = (ImageView) view.findViewById(R.id.head_ic);


    }
}
