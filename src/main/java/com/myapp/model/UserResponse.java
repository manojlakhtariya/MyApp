package com.myapp.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;

public class UserResponse {

  private final @NotNull
  Long id;
  private final @NotNull
  String firstName;
  private final @NotNull
  String lastName;
  private final @NotNull
  String email;
  private final @NotNull
  String phoneNumber;

  @JsonCreator
  public UserResponse(@JsonProperty("id") Long id,
      @JsonProperty("firstName") String firstName,
      @JsonProperty("lastName") String lastName,
      @JsonProperty("email") String email,
      @JsonProperty("phoneNumber") String phoneNumber) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public Long getId() {
    return id;
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
