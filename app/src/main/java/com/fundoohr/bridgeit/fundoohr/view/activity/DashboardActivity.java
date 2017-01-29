package com.fundoohr.bridgeit.fundoohr.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.model.SalaryPayslipModel;
import com.fundoohr.bridgeit.fundoohr.utility.ProgressDialogDisplayUtility;
import com.fundoohr.bridgeit.fundoohr.view.fragment.AttendanceSummaryFrag;
import com.fundoohr.bridgeit.fundoohr.view.fragment.BlankScreenFrag;
import com.fundoohr.bridgeit.fundoohr.view.fragment.ReportsFrag;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
/**
 * Created by bridgeit on 19/12/16.
 *
 * Purpose:
 * It Is The View Of MVVM Design Pattern.
 * It Is The UI Class Which Hold The UI Elements.
 * It Listens To Action Performed In UI class.
 * It is The Activity Which Need To Be Included In Manifest.xml File.
 *
 */
public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private Toolbar mToolbar;
    private CardView mCardReports;
    private TextView mTxtCardAttendDate, mTxtAppbarDate;
    private Calendar mCalendar;
    private int mDate, mMonth, mYear;
    private String[]  monthNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        //get the all months name in string resourse file
        monthNames = getResources().getStringArray(R.array.months);

        mCardReports = (CardView)findViewById(R.id.card_reports);
        //get current month and year
        mCalendar = Calendar.getInstance(Locale.getDefault());
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mYear = mCalendar.get(Calendar.YEAR);
        mDate = mCalendar.get(Calendar.DATE);


        //card textview date

        mTxtCardAttendDate = (TextView) findViewById(R.id.txt_card_attend_date);
        mTxtCardAttendDate.setText((mDate-1) +" " + monthNames[mMonth -1] + " " + mYear);

        // AppBar textview date
        mTxtAppbarDate = (TextView) findViewById(R.id.txt_appbar_date);
        mTxtAppbarDate.setText(mDate + " " + monthNames[mMonth -1] + " " + mYear);



        //set toolbar title on clicking of navigation drawer
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.text_apprence));
        setSupportActionBar(mToolbar);

        mCardReports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView mTextViewTitle;
                mTextViewTitle = (TextView) findViewById(R.id.txt_reports);
                //get the title from string resourse file
                String title = getResources().getString(R.string.reportTitle);
                mTextViewTitle.setText(title);
                mToolbar.setTitle(title);
                getReportInstance();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //clear the SharedPreferences data previously saved salary details
        SharedPreferences preferences1 = getSharedPreferences("empData",MODE_PRIVATE);
        SharedPreferences.Editor editor1 = preferences1.edit();
        editor1.putString("jsonData", "");
        editor1.apply();

        //clear the SharedPreferences data previously saved Attendance report details
        SharedPreferences preferences2 = getSharedPreferences("attendanceRepData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.putString("jsonDataAtten", "");
        editor2.apply();

        //clear the SharedPreferences data previously saved Invoice report details
        SharedPreferences preferences3 = getSharedPreferences("invoiceRepData",MODE_PRIVATE);
        SharedPreferences.Editor editor3 = preferences3.edit();
        editor3.putString("jsonDataInvoice", "");
        editor3.apply();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        /*Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
        startActivity(intent);*/
        mToolbar.setTitle(getTitle());
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //call Attendance class
    public void getAttendanceInstance() {
        getFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.frame_dashboard, new AttendanceSummaryFrag()).commit();
    }

    //call ReportsFrag class
    public void getReportInstance() {
        getFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.frame_dashboard, new ReportsFrag()).addToBackStack("").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    //on item click of navigation
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Dashboard) {
            mToolbar.setTitle(item.getTitle().toString());
            Intent intent = new Intent(DashboardActivity.this, DashboardActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_Engineers) {
            mToolbar.setTitle(item.getTitle().toString());
            getBlankInstance();

        } else if (id == R.id.nav_Attendance) {
            mToolbar.setTitle(item.getTitle().toString());
            getAttendanceInstance();


        } else if (id == R.id.nav_Reports) {
            mToolbar.setTitle(item.getTitle().toString());
            getReportInstance();

        } else if (id == R.id.nav_Clients) {
            mToolbar.setTitle(item.getTitle().toString());
            getBlankInstance();

        } else if (id == R.id.nav_Logout) {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //call blankscreen class
    public void getBlankInstance(){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_dashboard, new BlankScreenFrag())
                .addToBackStack(null).commit();
    }
}
