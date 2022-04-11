package com.example.myform;

public class getuserdata {

       String username,emailvalue,passwordvalue,mobilenumbervalue,dobvalue,spinnerdata,doevalue,toevalue;

    public getuserdata() {

    }

    public getuserdata(String username, String emailvalue, String passwordvalue, String mobilenumbervalue, String dobvalue, String spinnerdata, String toevalue,String doevalue) {
        this.username = username;
        this.emailvalue = emailvalue;
        this.passwordvalue = passwordvalue;
        this.mobilenumbervalue = mobilenumbervalue;
        this.dobvalue = dobvalue;
        this.spinnerdata = spinnerdata;
        this.doevalue = doevalue;
        this.toevalue = toevalue;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailvalue() {
        return emailvalue;
    }

    public void setEmailvalue(String emailvalue) {
        this.emailvalue = emailvalue;
    }

    public String getPasswordvalue() {
        return passwordvalue;
    }

    public void setPasswordvalue(String passwordvalue) {
        this.passwordvalue = passwordvalue;
    }

    public String getMobilenumbervalue() {
        return mobilenumbervalue;
    }

    public void setMobilenumbervalue(String mobilenumbervalue) {
        this.mobilenumbervalue = mobilenumbervalue;
    }

    public String getDobvalue() {
        return dobvalue;
    }

    public void setDobvalue(String dobvalue) {
        this.dobvalue = dobvalue;
    }

    public String getSpinnerdata() {
        return spinnerdata;
    }

    public void setSpinnerdata(String spinnerdata) {
        this.spinnerdata = spinnerdata;
    }

    public String getDoevalue() {
        return doevalue;
    }

    public void setDoevalue(String doevalue) {
        this.doevalue = doevalue;
    }

    public String getToevalue() {
        return toevalue;
    }

    public void setToevalue(String toevalue) {
        this.toevalue = toevalue;
    }
}
