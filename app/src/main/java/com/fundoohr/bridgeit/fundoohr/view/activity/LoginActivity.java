package com.fundoohr.bridgeit.fundoohr.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fundoohr.bridgeit.fundoohr.R;
import com.fundoohr.bridgeit.fundoohr.callback.IResponseLoginCallback;
import com.fundoohr.bridgeit.fundoohr.model.LoginModel;
import com.fundoohr.bridgeit.fundoohr.utility.ProgressDialogDisplayUtility;
import com.fundoohr.bridgeit.fundoohr.viewmodel.LoginViewModel;

import java.util.regex.Pattern;


/**
 * Created by bridgeit on 19/12/16.
 *
 * Purpose:
 * It Is The View Of MVVM Design Pattern.
 * It Is The UI Class Which Hold The UI Elements.
 * It Listens To Action Performed In UI class.
 * It Implements And The Observer Pattern To Listen Changes In The View model.
 * It Holds The View model To Update Its State Of The UI.
 * It is The Activity Which Need To Be Included In Manifest.xml File.
 *
 */
public class LoginActivity extends AppCompatActivity implements IResponseLoginCallback {

    public static final String TAG = "LoginActivity";
    SharedPreferences mSharedPreferences;
    String mLoginURL;
    private EditText mEmailEditTxt, mPasswordEditTxt;
    private Button mLoginBtn;
    private  String mEmail;
    private String mPwd;
    private ProgressDialogDisplayUtility mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //initialize the id for button and editText
        mEmailEditTxt = (EditText) findViewById(R.id.edit_txt_log_email);
        mPasswordEditTxt = (EditText)findViewById(R.id.edit_txt_log_pass);
        mLoginBtn = (Button) findViewById(R.id.button_login);

        //login button
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get the login url in resourse file
                mLoginURL = getResources().getString(R.string.login_url);
                validation();

                Log.i(TAG, "onClick: "+mLoginURL);
                /*Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);*/
            }
        });



    }
    // validate the email and password befor call to restApi
    private void validation() {
        mEmail = mEmailEditTxt.getText().toString();
        mPwd = mPasswordEditTxt.getText().toString();

        //get the email pattern from resourse file
        String emailPattern = getResources().getString(R.string.pattenrMatcher);
        //email pattern matches
        boolean emailMatch =  Pattern.matches(emailPattern, mEmail);

        //validates the email and password
        if (!mEmail.equals("") && !mPwd.equals("") && emailMatch && mPwd.length()>5 ) {
            checkInternetConn();


        }else {
            Toast.makeText(this, "please Enter the Email and password correctly", Toast.LENGTH_SHORT).show();
        }



    }


    //get data from viewmodel
    @Override
    public void loginResponse(LoginModel data) {
        //close the progress Dialog
        mDialog.closeDialog();
        int status = data.getStatusCode();
        String message = data.getMessage();
        String token = data.getToken();
        Log.i("login class", "loginResponse: "+status+"..."+message+"..."+token);

        if (status == 200) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

            //store data in sharedPreferaces
            mSharedPreferences = getSharedPreferences("TOKEN_VALUE", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            editor.putString("TOKEN", token);
            editor.commit();

            //if login success go to dashboard
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
        } else  {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }
    }

    //check the internet connection
    protected void checkInternetConn() {
        ConnectivityManager connection = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connection.getActiveNetworkInfo();

        if (info !=null && info.isConnected()) {
            //show the progressDialog
            mDialog = new ProgressDialogDisplayUtility(LoginActivity.this);
            mDialog.showDialog("Login please wait");


            //pass the url , email and pwd to view model class
            LoginViewModel  viewModel = new LoginViewModel(LoginActivity.this);
            viewModel.validation(mEmail, mPwd,mLoginURL,this);

        } else {
           mDialog.closeDialog();
            Toast.makeText(LoginActivity.this, "Check the internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
