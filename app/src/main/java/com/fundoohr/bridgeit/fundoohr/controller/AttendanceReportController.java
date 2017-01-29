package com.fundoohr.bridgeit.fundoohr.controller;

import android.util.Log;

import com.fundoohr.bridgeit.fundoohr.callback.IResponseServiceCallback;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created by bridgeit on 6/1/17.
 * * Purpose:
 *  It is data controller in mvvm arch.
 * It Will Act Like A Manager Which WillControls The Flow Of Data In Between
 * The Models and Views .Controller Will Get The Data From The Server
 * And Will Pass Data To viewmodel.
 * It will interact with rest service to get data with the cloud
 * It encapsulates content info model
 * This provides interface for viewmodel to interact with the controller
 * essentially abstracting the service layer data model.
 */

public class AttendanceReportController {
    public void getAttendanceRepDetails(String internalEmpURL, String tokenHeader,
                                        final IResponseServiceCallback salCall) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("x-token", tokenHeader);
        client.get(internalEmpURL,  new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody != null) {
                    Log.i("on sucess attendanceRep", "" + statusCode);
                    salCall.getRestData(responseBody);
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.i("on onFailure attendance", "" + statusCode);
                salCall.getRestData(responseBody);
            }
        });
    }
}