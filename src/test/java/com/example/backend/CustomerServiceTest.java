package com.example.backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for {@link CustomerService}, running against the real in-memory database (seeded by
 * {@code data.sql}). {@code @Transactional} rolls back each test's changes afterwards, so tests
 * leave the database as they found it.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@Transactional
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    void seed_data_is_loaded() {
        assertThat(customerService.findAll()).isNotEmpty();
    }

    @Test
    void customers_can_be_saved_and_deleted() {
        var before = customerService.findAll().size();

        var saved = customerService.save(
                new Customer("Test", "Person", "test.person@example.com", Status.LEAD, LocalDate.now()));
        assertThat(saved.getId()).isNotNull();
        assertThat(customerService.findAll()).hasSize(before + 1);

        customerService.delete(saved);
        assertThat(customerService.findAll()).hasSize(before);
    }
}
