package com.example.customer;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Database access for {@link Customer}. Extending Spring Data's {@link JpaRepository} provides
 * ready-made operations (find, save, delete) — Spring generates the implementation at runtime,
 * so there is nothing to write here.
 */
interface CustomerRepository extends JpaRepository<Customer, Long> {
}
