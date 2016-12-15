package com.example.bridgeit.fundoohr.view;

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

import com.example.bridgeit.fundoohr.R;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private  final String mTitle = "Attendance Summary";
    private Toolbar mToolbar;
    private CardView mCardView_attend_sum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mCardView_attend_sum = (CardView)findViewById(R.id.card_attendance_summary);
        mToolbar  = (Toolbar) findViewById(R.id.toolbar);

        mToolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.text_apprence));
        setSupportActionBar(mToolbar);
        mCardView_attend_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView mTextView_title;
                mTextView_title = (TextView) findViewById(R.id.txt_attend_sum);
                mTextView_title.setText(mTitle);
                mToolbar.setTitle(mTitle);
                getAttendanceInstance();
            }
        });
/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public void getAttendanceInstance() {
        getSupportFragmentManager().beginTransaction().addToBackStack(null)
                .replace(R.id.frame_dashboard, new AttendanceSummary()).commit();
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {


        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Dashboard) {
            mToolbar.setTitle(item.getTitle().toString());


        } else if (id == R.id.nav_Engineers) {
            mToolbar.setTitle(item.getTitle().toString());

        } else if (id == R.id.nav_Attendance) {
            mToolbar.setTitle(item.getTitle().toString());
            getAttendanceInstance();


        } else if (id == R.id.nav_Reports) {
            mToolbar.setTitle(item.getTitle().toString());

        } else if (id == R.id.nav_Clients) {
            mToolbar.setTitle(item.getTitle().toString());

        } else if (id == R.id.nav_Logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
