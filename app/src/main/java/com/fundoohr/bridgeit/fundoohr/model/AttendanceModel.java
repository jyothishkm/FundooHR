package com.fundoohr.bridgeit.fundoohr.model;

/**
 * Created by bridgeit on 6/1/17.
 * Purpose:
 * It Will Contains The Data Object Only WhereIn If You Declare The Object
 * Private You Need To Use Getter And Setter.It Will Have The State And
 * Behaviour Of The Class.
 */

public class AttendanceModel {
    String date_01;
    String date_02;

    public AttendanceModel() {
    }

    public AttendanceModel(String date_01, String date_02) {
        this.date_01 = date_01;
        this.date_02 = date_02;
    }

    public String getDate_01() {
        return date_01;
    }

    public void setDate_01(String date_01) {
        this.date_01 = date_01;
    }

    public String getDate_02() {
        return date_02;
    }

    public void setDate_02(String date_02) {
        this.date_02 = date_02;
    }
}
