package com.myapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private final @NotNull
    String firstName;
    private final @NotNull
    String lastName;
    private final @NotNull
    String email;
    private final @NotNull
    String phoneNumber;

    @JsonCreator
    public UserRequest(@JsonProperty("firstName") String firstName,
                       @JsonProperty("lastName") String lastName,
                       @JsonProperty("email") String email,
                       @JsonProperty("phoneNumber") String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
