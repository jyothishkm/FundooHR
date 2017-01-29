package com.fundoohr.bridgeit.fundoohr.model;

/**
 * Created by bridgeit on 6/1/17.
 * Purpose:
 * It Will Contains The Data Object Only WhereIn If You Declare The Object
 * Private You Need To Use Getter And Setter.It Will Have The State And
 * Behaviour Of The Class.
 */

public class SalaryGenModel {
    private String enggId;
    private String name;
    private String bankName;
    private String accNO;
    private String IFSCcode;
    private String paySalary;

    public SalaryGenModel() {
    }

    public SalaryGenModel(String enggId, String name, String bankName, String accNO, String IFSCcode, String paySalary) {
        this.enggId = enggId;
        this.name = name;
        this.bankName = bankName;
        this.accNO = accNO;
        this.IFSCcode = IFSCcode;
        this.paySalary = paySalary;
    }

    public String getEnggId() {
        return enggId;
    }

    public void setEnggId(String enggId) {
        this.enggId = enggId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccNO() {
        return accNO;
    }

    public void setAccNO(String accNO) {
        this.accNO = accNO;
    }

    public String getIFSCcode() {
        return IFSCcode;
    }

    public void setIFSCcode(String IFSCcode) {
        this.IFSCcode = IFSCcode;
    }

    public String getPaySalary() {
        return paySalary;
    }

    public void setPaySalary(String paySalary) {
        this.paySalary = paySalary;
    }
}
