package com.webforj.demos.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webforj.App;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.data.repository.Repository;
import com.webforj.demos.models.Customer;
import com.webforj.environment.ObjectTable;
import com.webforj.utilities.Assets;

/**
 * Provides a singleton service for managing customer data.
 *
 * This service loads customer data from a JSON file and stores it in a
 * {@link CollectionRepository}. It supports retrieving, adding, and editing
 * customer records while ensuring a single instance of the service using the
 * {@link ObjectTable}.
 *
 */
public class Service {

  /** List of customer data loaded from the JSON file. */
  private List<Customer> data = new ArrayList<>();

  /** Repository wrapping the customer list for data management. */
  private CollectionRepository<Customer> repository;

  /**
   * Private constructor to initialize the service instance with demo data.
   * This ensures that the service follows a singleton pattern.
   */
  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  /**
   * Retrieves the singleton instance of the {@code Service} class.
   *
   * If the instance already exists in the {@link ObjectTable}, it is returned.
   * Otherwise, a new instance is created, stored in the {@link ObjectTable},
   * and returned.
   *
   * @return the singleton instance of the service
   */
  public static Service getCurrent() {
    String key = "com.webforj.demos.data.service.instance";
    if (ObjectTable.contains(key)) {
      return (Service) ObjectTable.get(key);
    }

    Service instance = new Service();
    ObjectTable.put(key, instance);
    
    return instance;
  }

  /**
   * Retrieves the repository containing all customer data.
   *
   * @return a {@link Repository} of {@link Customer} objects
   */
  public Repository<Customer> getCustomers() {
    return repository;
  }

  /**
   * Retrieves a customer by its unique key.
   *
   * @param key the unique identifier of the customer
   * @return the {@link Customer} associated with the key
   */
  public Customer getCustomerByKey(Object key) {
    return repository.findByKey(key).get();
  }

  /**
   * Retrieves the unique key associated with a customer.
   *
   * @param cust the customer object
   * @return the key of the given customer as a {@link String}
   */
  public String getCustomerKey(Customer cust) {
    return repository.getKey(cust).toString();
  }

  /**
   * Adds a new customer to the repository.
   *
   * @param newCustomer the new customer to be added
   */
  public void addCustomer(Customer newCustomer) {
    data.add(newCustomer);
    repository.commit(newCustomer);
  }

  /**
   * Updates an existing customer's information in the repository.
   *
   * @param editedCustomer the updated customer data
   */
  public void editCustomer(Customer editedCustomer) {
    repository.commit(editedCustomer);
  }

  /**
  * Loads customer data from a JSON file and returns it as a list.
   *
   * This method uses {@link ObjectMapper} to parse the JSON file
   * located at <code>ws://data/customers.json</code>. If an error occurs
   * during loading, an empty list is returned, and an error message is logged
   * to the application console.
   *
   * @return a list of {@link Customer} objects, or an empty list if loading fails
   */
  private List<Customer> buildDemoList() {
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(Assets.contentOf(Assets.resolveWebServerUrl("ws://data/customers.json")),
          new TypeReference<List<Customer>>() {
          });
    } catch (IOException e) {
      App.console().error(e.getMessage());
    }
    return new ArrayList<>();
  }
}