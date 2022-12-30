package com.org.walletfinalprojectbehsazan.model.Dto;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserAuthRequest {

    @NotNull
    @NotBlank
    private String username;
    @NotNull
    @NotBlank
    private String password;
    public UserAuthRequest() {
    }

    public UserAuthRequest(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
