package com.fundoohr.bridgeit.fundoohr.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fundoohr.bridgeit.fundoohr.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by bridgeit on 6/1/17.
 * * Purpose:
 * 1.This Class Is The Adapter Of MVVM Design Pattern.
 * 2.To Retrieve The Necessary Model data from view .
 * 3.Here We Can Take The Necessary Data  And set to the  View.
 */
public class GrideCalendarAdapter extends BaseAdapter {
    private final Context mContext;
    private final List<String> mList;
    private final String[] months = {"January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"};
    private final int[] mDaysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 29};
    private int mDaysInMonth;
    private TextView mGridcell;
    private TextView mTextMark;
    private int mYear;
    private int currentDayOfMonth;

    public GrideCalendarAdapter(Context context, int month, int year) {
        super();
        this.mContext = context;
        this.mYear = year;
        this.mList = new ArrayList<String>();
        // Print Month
        printMonth(month, year);
    }

    private void printMonth(int mm, int yy) {
        int trailingSpaces = 0;
        int currentMonth = mm - 1;
        int prevMonth = 0;

        mDaysInMonth = getNumberOfDaysOfMonth(currentMonth);


        // Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth,1);

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        Log.i("day of week", ""+currentWeekDay);
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1){
            ++mDaysInMonth;
        }

        // Trailing Month days
        //25-WHITE-December-2016
        for (int i = 0; i < trailingSpaces; i++){
            Log.i("for.....", ""+i);
            //mList.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-"+ getMonthAsString(prevMonth) + "-" + prevYear);
            mList.add(" "+"-"+" "+"-"+" "+"-"+" ");
        }

        // Current Month Days
        for (int i = 1; i <= mDaysInMonth; i++){
            if (i == getCurrentDayOfMonth())
                mList.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);

            else
                mList.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
            // Log.i("current", mList.toString());
        }

        // Leading Month days
        for (int i = 0; i < mList.size() % 7; i++){
            //mList.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
            mList.add(" "+"-"+" "+"-"+" "+"-"+" ");
        }
    }



    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null){
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_grid_calendar, parent, false);
        }

        // Get a reference to the Day mGridcell
        mGridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
        mTextMark = (TextView)row.findViewById(R.id.text_mark);
        //mGridcell.setOnClickListener(this);

        // ACCOUNT FOR SPACING
        // 25-WHITE-December-2016
        String[] day_color = mList.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];

        // Set the Day GridCell
        mGridcell.setText(theday);
        if (!theday.equals(" ")) {
            mTextMark.setText("0/100");
        }
        mGridcell.setTag(theday + "-" + themonth + "-" + theyear);
        /*mGridcell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("onclick", "mGridcell");
            }
        });*/
        return row;
    }

    private String getMonthAsString(int i){
        return months[i];
    }

    private int getNumberOfDaysOfMonth(int i){
        if(i == 1){
            int a = leapYear(mYear);
            if(a == 29) {
                Log.i("if loop",""+a);
                return mDaysOfMonth[12];
            }else
                return mDaysOfMonth[i];

        }
        else
            return mDaysOfMonth[i];
    }

    public int getCurrentDayOfMonth(){
        return currentDayOfMonth;
    }

    //leap year
    public int leapYear(int year)
    {
        if(year%4 == 0 && year%100 != 0 || year%400 == 0)
        {
            System.out.println(year+" is a leap year");
         //   Log.i("leap year",""+mDaysOfMonth[12]);
            return mDaysOfMonth[12];

        }else
            System.out.println(year+" is not a leap year");
       // Log.i("not a leap year",""+mDaysOfMonth[1]);
        return mDaysOfMonth[1];
    }
}
