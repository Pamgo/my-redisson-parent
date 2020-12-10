package com.okay.test;

/**
 * <h2><h2>
 *
 * @author pangy
 * @create 2020-07-14 13:57
 */
public class OrgModel {
    private String orgid;
    private String status;
    private String name;
    private String fullname;

    public static OrgModel build(int count) {
        OrgModel orgModel = new OrgModel();
        orgModel.setOrgid("be3436af5ea966b68e09353ef4c6c55d-"+count);
        orgModel.setStatus("create-");
        orgModel.setName("石壁街道办-"+count);
        orgModel.setFullname("石壁街道办-"+count);
        return orgModel;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getOrgid() {
        return orgid;
    }

    public void setOrgid(String orgid) {
        this.orgid = orgid;
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
}
