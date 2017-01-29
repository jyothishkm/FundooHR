package com.fundoohr.bridgeit.fundoohr.callback;

import com.fundoohr.bridgeit.fundoohr.model.SalaryGenModel;

import java.util.ArrayList;

/**
 * Created by bridgeit on 22/1/17.
 */

public interface IResponseSalaryGenCallback {
    void salGenData(ArrayList<SalaryGenModel> modelArrayList);
}
