package com.okay.test;

/**
 * <h2><h2>
 *
 * @author okay
 * @create 2020-07-14 13:53
 */
public class UserModel {
    private String userpid;
    private String username;
    private String status;
    private String name;
    private String userid;
    private String type;
    private String mobile;

    public static UserModel build(int count) {
        UserModel userModel = new UserModel();
        userModel.setMobile("15989080388-"+count);
        userModel.setUserid("6d2441338d853f37cf7df81829db338e-"+count);
        userModel.setUsername("pengae-"+count);
        if (count == 3) {
            userModel.setMobile("13189891122");
            userModel.setName("alison阿里");
        } else {
            userModel.setMobile("13189891122"+count);
            userModel.setName("彭霭恩"+count);
        }
        userModel.setUserpid("8caf58d265bc992fcb7e0957b1a92847-"+count);
        userModel.setType("user");
        userModel.setStatus("create");
        return userModel;
    }

    public String getUserpid() {
        return userpid;
    }

    public void setUserpid(String userpid) {
        this.userpid = userpid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
