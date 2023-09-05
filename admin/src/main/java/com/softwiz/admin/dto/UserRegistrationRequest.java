package com.softwiz.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistrationRequest {

    private String username;

    private String password;

    private String email;

    private Boolean isEnable;

    @Override
    public String toString() {
        return "UserRegistrationRequest{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", isEnable=" + isEnable +
                '}';
    }
}
