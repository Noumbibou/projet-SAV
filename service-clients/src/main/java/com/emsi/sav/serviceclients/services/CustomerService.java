package com.emsi.sav.serviceclients.services;

import com.emsi.sav.serviceclients.entities.Customer;
import com.emsi.sav.serviceclients.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer creerCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Customer mettreAJourCustomer(UUID id, Customer donneesModifiees) {
        Customer customerExistant = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client introuvable: " + id));

        customerExistant.setName(donneesModifiees.getName());
        customerExistant.setEmail(donneesModifiees.getEmail());
        customerExistant.setPhone(donneesModifiees.getPhone());

        return customerRepository.save(customerExistant);
    }

    public void supprimerCustomer(UUID id) {
        if (!customerRepository.existsById(id)) {
            throw new IllegalArgumentException("Client introuvable: " + id);
        }
        customerRepository.deleteById(id);
    }
}