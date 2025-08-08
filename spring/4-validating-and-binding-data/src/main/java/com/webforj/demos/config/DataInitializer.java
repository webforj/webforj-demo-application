package com.webforj.demos.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webforj.demos.entity.Customer;
import com.webforj.demos.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CustomerService customerService;

    @Override
    public void run(String... args) {
        if (customerService.getTotalCustomersCount() == 0) {
            loadCustomersFromJson();
        }
    }

    private void loadCustomersFromJson() {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = getClass().getResourceAsStream("/static/data/customers.json")) {
            List<Customer> customers = mapper.readValue(is, new TypeReference<List<Customer>>() {});
            for (Customer customer : customers) {
                customerService.createCustomer(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
