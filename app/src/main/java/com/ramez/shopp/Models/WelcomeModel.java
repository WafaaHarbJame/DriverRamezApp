package com.ramez.shopp.Models;

public class WelcomeModel {
    private String infoTxtTitle;
    private String infoTxt1;
    private String infoTxt2;


    public WelcomeModel(String infoTxtTitle, String infoTxt1, String infoTxt2) {
        this.infoTxtTitle = infoTxtTitle;
        this.infoTxt1 = infoTxt1;
        this.infoTxt2 = infoTxt2;
    }

    public String getInfoTxtTitle() {
        return infoTxtTitle;
    }

    public void setInfoTxtTitle(String infoTxtTitle) {
        this.infoTxtTitle = infoTxtTitle;
    }

    public String getInfoTxt1() {
        return infoTxt1;
    }

    public void setInfoTxt1(String infoTxt1) {
        this.infoTxt1 = infoTxt1;
    }

    public String getInfoTxt2() {
        return infoTxt2;
    }

    public void setInfoTxt2(String infoTxt2) {
        this.infoTxt2 = infoTxt2;
    }
}
