package com.webforj.demos.service;

import com.webforj.data.repository.spring.SpringDataRepository;
import com.webforj.demos.entity.Customer;
import com.webforj.demos.repository.CustomerRepository;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerService {

  @Autowired
  private CustomerRepository repository;

  public Customer createCustomer(Customer customer) {
    return repository.save(customer);
  }

  public Customer updateCustomer(Customer customer) {
    if (!repository.existsById(customer.getId())) {
      throw new IllegalArgumentException("Customer not found with ID: " + customer.getEntityKey());
    }
    return repository.save(customer);
  }

  public void deleteCustomer(Long id) {
    if (!repository.existsById(id)) {
      throw new IllegalArgumentException("Customer not found with ID: " + id);
    }
    repository.deleteById(id);
  }

  public long getTotalCustomersCount() {
    return repository.count();
  }

  public SpringDataRepository<Customer, Long> getFilterableRepository() {
    return new SpringDataRepository<>(repository);
  }

}
