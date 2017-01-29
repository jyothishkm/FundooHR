package com.fundoohr.bridgeit.fundoohr.view.fragment;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.adapter.GrideCalendarAdapter;

import java.util.Calendar;
import java.util.Locale;
/**
 * Created by bridgeit on 19/12/16.
 *
 * Purpose:
 * It Is The View Of MVVM Design Pattern.
 * It Is The UI Class Which Hold The UI Elements.
 * It Listens To Action Performed In UI class.
 * It Implements And The Observer Pattern To Listen Changes In The View model.
 * It Holds The View model To Update Its State Of The UI.
 */

public class AttendanceSummaryFrag extends Fragment {
    String[] mMonthNames;
    private GridView mCalendarView;
    private GrideCalendarAdapter mAdapter;
    private Calendar mCalendar;
    private int mDate, mMonth, mYear;
    private ImageView mDownArrow;
    private TextView mTxtdate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.activity_attendance_summary, container, false);


        mMonthNames = getResources().getStringArray(R.array.months);

        //get current month and year
        mCalendar = Calendar.getInstance(Locale.getDefault());
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mYear = mCalendar.get(Calendar.YEAR);
        mDate = mCalendar.get(Calendar.DATE);

        mCalendarView = (GridView) view.findViewById(R.id.calendar);
        mDownArrow = (ImageView) view.findViewById(R.id.image_down_arrow);
        mTxtdate = (TextView) view.findViewById(R.id.text_mnth_year);

        //set month and year for calendar textview
        mTxtdate.setText(mMonthNames[mMonth -1] + "," + mYear);


        //popup datepicker function
        mDownArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        mTxtdate.setText(mMonthNames[monthOfYear] + "," + year);
                        //date.setText("" + dayOfMonth + " / " + monthOfYear + " / " + year);

                        //call mAdapter for custom date and month
                        mAdapter = new GrideCalendarAdapter(getActivity(),(monthOfYear+1), year);
                        mAdapter.notifyDataSetChanged();
                        mCalendarView.setAdapter(mAdapter);
                    }
                }, mYear, mMonth -1, mDate).show();


            }
        });

        // call mAdapter class

        mAdapter = new GrideCalendarAdapter(getActivity(), mMonth, mYear);
        mAdapter.notifyDataSetChanged();
        mCalendarView.setAdapter(mAdapter);

        return view;
    }




}

