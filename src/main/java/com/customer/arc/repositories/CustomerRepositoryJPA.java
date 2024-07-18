package com.customer.arc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.customer.arc.entities.Customer;

public interface CustomerRepositoryJPA extends JpaRepository<Customer, Long>{

}
