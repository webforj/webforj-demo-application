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

public class Service {
  private List<Customer> data = new ArrayList<>();
  private CollectionRepository<Customer> repository;

  private Service() {
    data = buildDemoList();
    repository = new CollectionRepository<>(data);
  }

  public static Service getCurrent() {
    String key = "com.webforj.demos.data.service.instance";
    if (ObjectTable.contains(key)) {
      return (Service) ObjectTable.get(key);
    }

    Service instance = new Service();
    ObjectTable.put(key, instance);

    return instance;
  }

  public Repository<Customer> getCustomers() {
    return repository;
  }

  public Customer getCustomerByKey(Object key) {
    return repository.findByKey(key).get();
  }

  public String getCustomerKey(Customer cust) {
    return repository.getKey(cust).toString();
  }

  public void addCustomer(Customer newCustomer) {
    data.add(newCustomer);
    repository.commit(newCustomer);
  }

  public void editCustomer(Customer editedCustomer) {
    repository.commit(editedCustomer);
  }

  private List<Customer> buildDemoList() {
    ObjectMapper mapper = new ObjectMapper();

    try {
      return mapper.readValue(Assets.contentOf(Assets.resolveContextUrl("context://data/customers.json")),
          new TypeReference<List<Customer>>() {
          });
    } catch (IOException e) {
      App.console().error(e.getMessage());
    }
    return new ArrayList<>();
  }
}