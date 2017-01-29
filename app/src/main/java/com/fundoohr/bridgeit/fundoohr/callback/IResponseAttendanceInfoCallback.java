package com.fundoohr.bridgeit.fundoohr.callback;

import com.fundoohr.bridgeit.fundoohr.model.AttendanceModel;

import java.util.ArrayList;

/**
 * Created by bridgeit on 19/12/16.
 */

public interface IResponseAttendanceInfoCallback {
    void getAttendance(ArrayList<AttendanceModel> modelList);
}
