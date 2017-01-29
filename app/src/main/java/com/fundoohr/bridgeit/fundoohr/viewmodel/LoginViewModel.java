package com.fundoohr.bridgeit.fundoohr.viewmodel;

import android.content.Context;
import android.util.Log;

import com.fundoohr.bridgeit.fundoohr.callback.IResponseServiceCallback;
import com.fundoohr.bridgeit.fundoohr.callback.IResponseLoginCallback;
import com.fundoohr.bridgeit.fundoohr.controller.LoginController;
import com.fundoohr.bridgeit.fundoohr.model.LoginModel;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bridgeit on 6/1/17.
 * * Purpose:
 * 1.This Class Is The ViewModel Of MVVM Design Pattern.
 * 2.Holding The Model Required For The Content View List.
 * 3.This Class Has The Controller Object To Retrieve The Necessary
 * Model.
 * Carries The Required Field Data To The View
 * As In Eg:
 * Here We Can Take The Necessary Data  And Pass It To The View.
 */

public class LoginViewModel {
    Context mContext;
    public LoginViewModel(Context context) {
        this.mContext = context;
    }

    public void validation(String email, String pwd, String loginURL, final IResponseLoginCallback loginCallback) {


        RequestParams params = new RequestParams();
        params.put("emailId", email);
        params.put("password", pwd);

        //call the controller get the data from restApi
        LoginController controller = new LoginController();
        controller.loginRestCall(loginURL, params, new IResponseServiceCallback() {
            @Override
            public void getRestData(byte[] bytes) {
                JSONObject jsonObject = null;
                try {
                    if (bytes != null) {
                        jsonObject = new JSONObject(new String(bytes));
                        String message = (jsonObject.getString("message"));
                        int status = (jsonObject.getInt("status"));
                        String token = (jsonObject.getString("token"));
                        LoginModel model = new LoginModel();
                        //set the data to login model class
                        model.setMessage(message);
                        model.setStatusCode(status);
                        if (token != null) {
                            model.setToken(token);
                        }
                        //pass the data to view through interface
                        loginCallback.loginResponse(model);


                        Log.i("login", "login viewmodel: " + status + message);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
