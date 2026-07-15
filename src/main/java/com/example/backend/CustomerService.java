package com.example.backend;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The backend the UI talks to for loading, saving, and deleting customers. It's a plain Java
 * class — views call these methods directly; there are no REST endpoints in between.
 * <p>
 * {@code @Service} tells Spring to create one shared instance and hand it to any class that
 * declares it as a constructor parameter. {@code @Transactional} runs each method inside a
 * database transaction.
 */
@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Transactional
    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }

    @Transactional
    public void delete(Customer customer) {
        customerRepository.delete(customer);
    }
}
