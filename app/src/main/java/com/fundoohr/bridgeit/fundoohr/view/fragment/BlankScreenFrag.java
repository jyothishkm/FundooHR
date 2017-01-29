package com.fundoohr.bridgeit.fundoohr.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fundoohr.bridgeit.fundoohr.R;

/**
 * Created by bridgeit on 15/12/16.
 */

public class BlankScreenFrag extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.blank, container, false);
        return view;
    }
}
