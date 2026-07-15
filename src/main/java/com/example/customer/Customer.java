package com.example.customer;

import jakarta.persistence.*;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;

/**
 * A customer record: a plain Java class holding the fields the UI shows and edits. The JPA
 * annotations ({@code @Entity}, {@code @Column}, and friends) map the class to the
 * {@code customer} database table so instances can be loaded from and saved to the database.
 */
@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    @Nullable
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName = "";

    @Column(name = "last_name", nullable = false)
    private String lastName = "";

    @Column(name = "email", nullable = false)
    private String email = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.LEAD;

    @Column(name = "customer_since")
    @Nullable
    private LocalDate customerSince;

    public Customer() {
        // Empty constructor needed by JPA and for creating new customers in the UI.
    }

    public Customer(String firstName, String lastName, String email, Status status,
            @Nullable LocalDate customerSince) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.status = status;
        this.customerSince = customerSince;
    }

    public @Nullable Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public @Nullable LocalDate getCustomerSince() {
        return customerSince;
    }

    public void setCustomerSince(@Nullable LocalDate customerSince) {
        this.customerSince = customerSince;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || !getClass().isAssignableFrom(obj.getClass())) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Customer other = (Customer) obj;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        // Hashcode should never change during the lifetime of an object. Because of
        // this we can't use getId() to calculate the hashcode. Unless you have sets
        // with lots of entities in them, returning the same hashcode should not be a
        // problem.
        return getClass().hashCode();
    }
}
