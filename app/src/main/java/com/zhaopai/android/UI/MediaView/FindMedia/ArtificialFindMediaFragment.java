package com.zhaopai.android.UI.MediaView.FindMedia;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zhaopai.android.R;

public class ArtificialFindMediaFragment extends Fragment {

    public ArtificialFindMediaFragment() {
        // Required empty public constructor
    }
    // TODO: Rename and change types and number of parameters
    public static ArtificialFindMediaFragment newInstance() {
        ArtificialFindMediaFragment fragment = new ArtificialFindMediaFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artificial_find_media, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
