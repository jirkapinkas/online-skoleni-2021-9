package com.example.mailing.repository;

import com.example.mailing.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Transactional
    @Modifying
    @Query("delete from Customer c where c.id = ?1")
    void deleteCustomer(int id);

}
