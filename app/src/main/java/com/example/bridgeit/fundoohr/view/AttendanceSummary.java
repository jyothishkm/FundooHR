package com.example.bridgeit.fundoohr.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bridgeit.fundoohr.R;
import com.example.bridgeit.fundoohr.adapter.GrideCalendarAdapter;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by bridgeit on 9/12/16.
 */

public class AttendanceSummary extends Fragment {
    String[] monthNames;
    private GridView calendarView;
    private GrideCalendarAdapter adapter;
    private Calendar _calendar;
    private int mDate, m_Month, mYear;
    private ImageView down_arrow;
    private TextView txtdate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.activity_attendance_summary, container, false);
        monthNames = new String[]{"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"};

        //get current month and year
        _calendar = Calendar.getInstance(Locale.getDefault());
        m_Month = _calendar.get(Calendar.MONTH) + 1;
        mYear = _calendar.get(Calendar.YEAR);
        mDate = _calendar.get(Calendar.DATE);

        calendarView = (GridView) view.findViewById(R.id.calendar);
        down_arrow = (ImageView) view.findViewById(R.id.image_down_arrow);
        txtdate = (TextView) view.findViewById(R.id.text_mnth_year);
        txtdate.setText(monthNames[m_Month-1] + "," + mYear);
        down_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        txtdate.setText(monthNames[monthOfYear] + "," + year);
                        //date.setText("" + dayOfMonth + " / " + monthOfYear + " / " + year);
                        adapter = new GrideCalendarAdapter(getContext(),(monthOfYear+1), year);
                        adapter.notifyDataSetChanged();
                        calendarView.setAdapter(adapter);
                    }
                }, mYear, m_Month-1, mDate).show();


            }
        });

        // set adapter

        adapter = new GrideCalendarAdapter(getContext(),m_Month, mYear);
        adapter.notifyDataSetChanged();
        calendarView.setAdapter(adapter);

        return view;
    }




}

