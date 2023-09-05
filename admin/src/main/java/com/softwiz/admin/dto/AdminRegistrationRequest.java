package com.softwiz.admin.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminRegistrationRequest {
    private String firstname;
    private String lastname;
    private String role;
    private String email;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "AdminRegistrationRequest{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", role='" + role + '\'' +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
