package org.xhliu.demo.dto;

/**
 * @author xhliu
 * @create 2022-01-25-17:44
 **/
public class LoginDto {
    private String userAccount;

    private String password;

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static LoginDto newInstance() {
        return new LoginDto();
    }
}
