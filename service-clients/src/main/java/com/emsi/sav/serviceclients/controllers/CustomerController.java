package com.emsi.sav.serviceclients.controllers;

import com.emsi.sav.serviceclients.entities.Customer;
import com.emsi.sav.serviceclients.repositories.CustomerRepository;
import com.emsi.sav.serviceclients.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerService customerService, CustomerRepository customerRepository) {
        this.customerService = customerService;
        this.customerRepository = customerRepository;
    }

    @PostMapping
    public ResponseEntity<Customer> creerCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.creerCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> listerCustomers() {
        return ResponseEntity.ok(customerRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> consulterCustomer(@PathVariable("id") UUID id) {
        return customerRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> mettreAJourCustomer(@PathVariable("id") UUID id, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.mettreAJourCustomer(id, customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> supprimerCustomer(@PathVariable("id") UUID id) {
        customerService.supprimerCustomer(id);
        return ResponseEntity.noContent().build();
    }
}