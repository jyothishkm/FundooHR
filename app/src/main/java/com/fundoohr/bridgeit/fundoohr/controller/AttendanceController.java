package com.fundoohr.bridgeit.fundoohr.controller;

import android.util.Log;

import com.fundoohr.bridgeit.fundoohr.model.AttendanceModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
/*
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

public class AttendanceController {
    FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    DatabaseReference mReference = mDatabase.getReference("months");

    public DatabaseReference getmReference() {
        mReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                GenericTypeIndicator<ArrayList<AttendanceModel>> indicator = new GenericTypeIndicator<ArrayList<AttendanceModel>>() {
                };
                ArrayList<AttendanceModel> models = new ArrayList<AttendanceModel>();
                models.addAll(dataSnapshot.getValue(indicator));

                for (int i = 0; i <models.size() ; i++) {
                    Log.i("showdata",""+models.get(i).getDate_01());
                    Log.i("showdata",""+models.get(i).getDate_02());

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return null;
    }
}
