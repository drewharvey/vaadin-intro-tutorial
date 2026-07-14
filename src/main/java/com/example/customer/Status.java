package com.example.customer;

/**
 * The stage a customer is at in the sales pipeline. Stored as a string in the database.
 */
public enum Status {
    LEAD, PROSPECT, CUSTOMER, INACTIVE
}
