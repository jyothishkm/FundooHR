package com.fundoohr.bridgeit.fundoohr.callback;

import com.fundoohr.bridgeit.fundoohr.model.AttendanceReportModel;
import com.fundoohr.bridgeit.fundoohr.model.ExpListChildItemsModel;
import com.fundoohr.bridgeit.fundoohr.model.ExpListGroupItemsModel;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bridgeit on 10/1/17.
 */

public interface IResponseAttendRepCallback {
    void getAttendRep(ArrayList<ExpListGroupItemsModel> groupItemsModels,ArrayList<ArrayList<ExpListChildItemsModel>> arrayLists);
}
