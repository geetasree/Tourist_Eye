package com.example.welcome.travelmatev11.Model;

/**
 * Created by Welcome on 7/18/2016.
 */
public class UserInfo {

    private String userName;
    private String password;
    private String gender;
    private String eMail;
    private String picpath;

    public UserInfo(String userName, String password, String gender, String eMail, String picPath) {

        this.userName = userName;
        this.password = password;
        this.gender = gender;
        this.eMail = eMail;
        this.picpath = picpath;
    }

    public UserInfo(String userName, String password, String eMail, String gender) {
        this.userName = userName;
        this.password = password;
        this.eMail = eMail;
        this.gender = gender;
    }





    public String getGender() {
        return gender;
    }


    public UserInfo(String password, String loginName) {
        this.password = password;

    }



    public String geteMail() {
        return eMail;
    }


    public String getUserName() {

        return userName;
    }




    public String getPassword() {
        return password;
    }


    public String getPicpath() {
        return picpath;
    }
}
