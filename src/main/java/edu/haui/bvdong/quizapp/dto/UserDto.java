package edu.haui.bvdong.quizapp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class UserDto implements Serializable {

    @NotNull(message = "Username can't be null!")
    @NotEmpty(message = "Username can't be empty!")
    private String userName;
    @NotNull(message = "Password can't be null!")
    @NotEmpty(message = "Password can't be null!")
    private String password;
    @NotNull
    @NotEmpty
    private String rePassword;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }
}
