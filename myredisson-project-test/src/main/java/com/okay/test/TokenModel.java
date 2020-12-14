package com.okay.test;

/**
 * <h2><h2>
 *
 * @author okay
 * @create 2020-07-14 13:44
 */
public class TokenModel {
    private String expires;
    private String token;

    public TokenModel(String expires, String token) {
        this.expires = expires;
        this.token = token;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
