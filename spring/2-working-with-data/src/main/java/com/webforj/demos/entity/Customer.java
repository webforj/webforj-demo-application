package com.webforj.demos.entity;

import com.webforj.data.HasEntityKey;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
public class Customer implements HasEntityKey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String firstName = "";
  private String lastName = "";
  private String company = "";
  private Country country = Country.UNKNOWN;

  public enum Country {
    UNKNOWN,
    GERMANY,
    ENGLAND,
    ITALY,
    USA
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

  public Long getId() {
    return id;
  }

  @Override
  public Object getEntityKey() {
    return id;
  }
}
