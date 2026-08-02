package com.emsi.sav.serviceclients.config;

import com.emsi.sav.serviceclients.entities.Contact;
import com.emsi.sav.serviceclients.entities.Customer;
import com.emsi.sav.serviceclients.repositories.ContactRepository;
import com.emsi.sav.serviceclients.repositories.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataSeeder implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final ContactRepository contactRepository;

    public DataSeeder(CustomerRepository customerRepository, ContactRepository contactRepository) {
        this.customerRepository = customerRepository;
        this.contactRepository = contactRepository;
    }

    @Override
    public void run(String... args) {
        if (customerRepository.count() == 0) {
            Customer mohamed = customerRepository.save(
                    new Customer(null, "Mohamed Tazi", "mohamed.tazi@example.com", "0600000001", null));

            contactRepository.save(new Contact(null, "EMAIL", "mohamed.tazi@example.com", mohamed));
            contactRepository.save(new Contact(null, "PHONE", "0600000001", mohamed));
        }
    }
}