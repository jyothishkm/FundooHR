package com.fundoohr.bridgeit.fundoohr.model;

/**
 * Created by bridgeit on 6/1/17.
 * Purpose:
 * It Will Contains The Data Object Only WhereIn If You Declare The Object
 * Private You Need To Use Getter And Setter.It Will Have The State And
 * Behaviour Of The Class.
 */

public class SalaryPayslipModel {
    private String id;
    private String name;
    private String states;
    private String company;

    public SalaryPayslipModel() {
    }

    public SalaryPayslipModel(String name, String states, String company) {
        this.name = name;
        this.states = states;
        this.company = company;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStates() {
        return states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
