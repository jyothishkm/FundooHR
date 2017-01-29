package com.fundoohr.bridgeit.fundoohr.callback;

import com.fundoohr.bridgeit.fundoohr.model.SalaryPayslipModel;

import java.util.ArrayList;

/**
 * Created by bridgeit on 9/1/17.
 */

public interface IResponseSalaryCallback {
    void getSalaryModel(ArrayList<SalaryPayslipModel> models);
}
