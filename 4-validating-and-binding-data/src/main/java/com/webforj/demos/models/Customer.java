package com.webforj.demos.models;

import java.util.UUID;

import com.google.gson.annotations.SerializedName;
import com.webforj.data.HasEntityKey;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

public class Customer implements HasEntityKey{
  @NotEmpty(message = "Name cannot be empty")
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String firstName = "";
  @NotEmpty(message = "Name cannot be empty")
  @Pattern(regexp = "[a-zA-Z]*", message = "Invalid characters")
  private String lastName = "";
  @Pattern(regexp = "[a-zA-Z0-9 ]*", message = "Invalid characters")
  private String company = "";
  private Country country = Country.UNKNOWN;
  private UUID uuid = UUID.randomUUID();

  public enum Country {

    @SerializedName("Unknown")
    UNKNOWN,

    @SerializedName("Germany")
    GERMANY,

    @SerializedName("England")
    ENGLAND,

    @SerializedName("Italy")
    ITALY,

    @SerializedName("USA")
    USA,
  }

  public Customer(String firstName, String lastName, String company, Country country) {
    setFirstName(firstName);
    setLastName(lastName);
    setCompany(company);
    setCountry(country);
  }

  public Customer(String firstName, String lastName, String company) {
    this(firstName, lastName, company, Country.UNKNOWN);
  }

  public Customer(String firstName, String lastName) {
    this(firstName, lastName, "");
  }

  public Customer(String firstName) {
    this(firstName, "");
  }

  public Customer() {
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getCompany() {
    return company;
  }

  public void setCompany(String company) {
    this.company = company;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}