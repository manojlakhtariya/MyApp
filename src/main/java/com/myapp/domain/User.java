package com.myapp.domain;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {

  @Id
  @GeneratedValue
  private Long id;

  @NotNull
  private String firstName;

  @NotNull
  private String lastName;

  @NotNull
  private String email;

  @NotNull
  private String phoneNumber;

  public User() {
  }

  @JsonCreator
  public User(@JsonProperty("firstName") String firstName,
      @JsonProperty("lastName") String lastName,
      @JsonProperty("email") String email,
      @JsonProperty("phoneNumber") String phoneNumber) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phoneNumber = phoneNumber;
  }

  public Long getId() {
    return id;
  }

  @JsonProperty("id")
  public void setId(Long id) {
    this.id = id;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return Objects.equals(id, user.id) &&
        Objects.equals(firstName, user.firstName) &&
        Objects.equals(lastName, user.lastName) &&
        Objects.equals(email, user.email) &&
        Objects.equals(phoneNumber, user.phoneNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, email, phoneNumber);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", email='" + email + '\'' +
        ", phoneNumber='" + phoneNumber + '\'' +
        '}';
  }
}
