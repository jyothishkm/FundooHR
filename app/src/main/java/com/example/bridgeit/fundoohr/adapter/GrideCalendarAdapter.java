package com.example.bridgeit.fundoohr.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.bridgeit.fundoohr.R;
import com.example.bridgeit.fundoohr.view.Login;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by bridgeit on 13/12/16.
 */

public class GrideCalendarAdapter extends BaseAdapter {
    private final Context _context;
    private final List<String> list;
    private final String[] months = {"January", "February", "March", "April", "May", "June",
                                            "July", "August", "September", "October", "November", "December"};
    private final int[] daysOfMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31, 29};
    private int daysInMonth;
    private TextView gridcell;
    private TextView textMark;
    private int mYear;
    private int currentDayOfMonth;

    public GrideCalendarAdapter(Context context, int month, int year) {
        super();
        this._context = context;
        this.mYear = year;
        this.list = new ArrayList<String>();
        // Print Month
        printMonth(month, year);
        Log.i("...... year",""+year);
    }

    private void printMonth(int mm, int yy) {
        int trailingSpaces = 0;
        int currentMonth = mm - 1;
        int prevMonth = 0;

        daysInMonth = getNumberOfDaysOfMonth(currentMonth);


        // Gregorian Calendar : MINUS 1, set to FIRST OF MONTH
        GregorianCalendar cal = new GregorianCalendar(yy, currentMonth,1);

        if (currentMonth == 11) {
            prevMonth = currentMonth - 1;
        } else if (currentMonth == 0) {
            prevMonth = 11;
        } else {
            prevMonth = currentMonth - 1;
        }

        int currentWeekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
        trailingSpaces = currentWeekDay;

        if (cal.isLeapYear(cal.get(Calendar.YEAR)) && mm == 1){
            ++daysInMonth;
        }

        // Trailing Month days
        //25-WHITE-December-2016
        for (int i = 0; i < trailingSpaces; i++){
            //list.add(String.valueOf((daysInPrevMonth - trailingSpaces + DAY_OFFSET) + i) + "-GREY" + "-"+ getMonthAsString(prevMonth) + "-" + prevYear);
            list.add(" "+"-"+" "+"-"+" "+"-"+" ");
        }

        // Current Month Days
        for (int i = 1; i <= daysInMonth; i++){
            if (i == getCurrentDayOfMonth())
                list.add(String.valueOf(i) + "-BLUE" + "-" + getMonthAsString(currentMonth) + "-" + yy);

            else
                list.add(String.valueOf(i) + "-WHITE" + "-" + getMonthAsString(currentMonth) + "-" + yy);
           // Log.i("current", list.toString());
        }

        // Leading Month days
        for (int i = 0; i < list.size() % 7; i++){
            //list.add(String.valueOf(i + 1) + "-GREY" + "-" + getMonthAsString(nextMonth) + "-" + nextYear);
            list.add(" "+"-"+" "+"-"+" "+"-"+" ");
        }
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null){
            LayoutInflater inflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.custom_grid_calendar, parent, false);
        }

        // Get a reference to the Day gridcell
        gridcell = (TextView) row.findViewById(R.id.calendar_day_gridcell);
        textMark = (TextView)row.findViewById(R.id.text_mark);
        //gridcell.setOnClickListener(this);

        // ACCOUNT FOR SPACING
        // 25-WHITE-December-2016
        String[] day_color = list.get(position).split("-");
        String theday = day_color[0];
        String themonth = day_color[2];
        String theyear = day_color[3];

        // Set the Day GridCell
        gridcell.setText(theday);
        if (!theday.equals(" ")) {
            textMark.setText("0/100");
        }
        gridcell.setTag(theday + "-" + themonth + "-" + theyear);
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
                return daysOfMonth[12];
            }else
                return daysOfMonth[i];

        }
        else
        return daysOfMonth[i];
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
            Log.i("leap year",""+daysOfMonth[12]);
            return daysOfMonth[12];

        }else
            System.out.println(year+" is not a leap year");
        Log.i("not a leap year",""+daysOfMonth[1]);
        return daysOfMonth[1];
    }
}
