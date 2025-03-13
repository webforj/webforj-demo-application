package com.webforj.demos.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webforj.data.repository.CollectionRepository;
import com.webforj.data.repository.Repository;
import com.webforj.demos.models.Customer;
import com.webforj.environment.ObjectTable;
import com.webforj.utilities.Assets;

/**
 * Provides a singleton service for managing customer data.
 * 
 * 
 * This service loads customer data from a JSON file and stores it in a 
 * {@link CollectionRepository}. It supports retrieving the repository of 
 * customers and ensures a single instance of the service using the 
 * {@link ObjectTable}.
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
   * <p>
   * If the instance already exists in the {@link ObjectTable}, it is returned.
   * Otherwise, a new instance is created, stored in the {@link ObjectTable},
   * and returned.
   * </p>
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
   * Retrieves the customer repository containing all loaded customer data.
   *
   * @return a {@link Repository} of {@link Customer} objects
   */
  public Repository<Customer> getCustomers() {
    return repository;
  }

  /**
   * Loads customer data from a JSON file and returns it as a list.
   * 
   * <p>
   * This method uses {@link ObjectMapper} to parse the JSON file 
   * located at <code>ws://data/customers.json</code>. If an error occurs 
   * during loading, an empty list is returned.
   * </p>
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
      return new ArrayList<>();
    }
  }
}
