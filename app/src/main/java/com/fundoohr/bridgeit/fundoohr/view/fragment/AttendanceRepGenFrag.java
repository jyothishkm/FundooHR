package com.fundoohr.bridgeit.fundoohr.view.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fundoohr.bridgeit.fundoohr.R;

/**
 * Created by bridgeit on 19/12/16.
 *
 * Purpose:
 * It Is The View Of MVVM Design Pattern.
 * It Is The UI Class Which Hold The UI Elements.
 * It Listens To Action Performed In UI class.
 */

public class AttendanceRepGenFrag extends Fragment {
    ImageView mImageBack;
    TextView mTextDes;
   // ListView mListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.attn_report_gen, container, false);
        //initialize the id
        mImageBack = (ImageView) view.findViewById(R.id.image_back_attep_gen);
        mTextDes = (TextView) view.findViewById(R.id.txt_des_attrep_gen);
       // get the decription from string resourse file
        String desc = getResources().getString(R.string.attenRepGenDEsc);
        //set text description
        mTextDes.setText(desc);
        mTextDes.setTextColor(Color.GRAY);
        mTextDes.setTextSize(15f);

        //back to Attendance report
        mImageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getFragmentManager().beginTransaction()
                        .replace(R.id.frame_dashboard, new AttendanceReportFrag()).addToBackStack("").commit();
            }
        });
        return view;
    }
}
