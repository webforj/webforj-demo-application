package com.webforj.demos.models;

import com.google.gson.annotations.SerializedName;
import com.webforj.data.HasEntityKey;
import java.util.UUID;

public class Customer implements HasEntityKey {
  private String firstName = "";
  private String lastName = "";
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
    setFirstName(firstName).setLastName(lastName).setCompany(company).setCountry(country);
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

  public Customer setFirstName(String newName) {
    firstName = newName;
    return this;
  }

  public String getFirstName() {
    return firstName;
  }

  public Customer setLastName(String newName) {
    lastName = newName;
    return this;
  }

  public String getLastName() {
    return lastName;
  }

  public Customer setCompany(String newCompany) {
    company = newCompany;
    return this;
  }

  public String getCompany() {
    return company;
  }

  public Customer setCountry(Country newCountry) {
    country = newCountry;
    return this;
  }

  public Country getCountry() {
    return country;
  }

  @Override
  public Object getEntityKey() {
    return uuid;
  }
}