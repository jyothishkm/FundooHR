package com.fundoohr.bridgeit.fundoohr.model;

/**
 * Created by bridgeit on 6/1/17.
 * Purpose:
 * It Will Contains The Data Object Only WhereIn If You Declare The Object
 * Private You Need To Use Getter And Setter.It Will Have The State And
 * Behaviour Of The Class.
 */

public class AttendanceReportModel {
    String companyName;
    String name;
    String leave;

    public AttendanceReportModel() {
    }

    public AttendanceReportModel(String companyName, String empName, String leave) {
        this.companyName = companyName;
        this.name = empName;
        this.leave = leave;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLeave() {
        return leave;
    }

    public void setLeave(String leave) {
        this.leave = leave;
    }
}
