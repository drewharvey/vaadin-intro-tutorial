package com.example.ui;

import com.example.backend.Customer;
import com.example.backend.Status;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Route("customers")
public class CustomerListView extends VerticalLayout {

    private final Grid<Customer> grid = new Grid<>();

    private final List<Customer> customers = getSampleCustomers();

    public CustomerListView() {
        setSizeFull();

        grid.addColumn(Customer::getFirstName)
                .setSortable(true)
                .setHeader("First name");
        grid.addColumn(Customer::getLastName)
                .setSortable(true)
                .setHeader("Last name");
        grid.addColumn(Customer::getEmail)
                .setSortable(true)
                .setHeader("Email");
        grid.addColumn(Customer::getStatus)
                .setSortable(true)
                .setHeader("Status");
        grid.addColumn(Customer::getCustomerSince)
                .setSortable(true)
                .setHeader("Customer since");
        grid.setSizeFull();
        grid.setItems(customers);
        add(grid);
    }

    private List<Customer> getSampleCustomers() {
        return new ArrayList<>(List.of(
                new Customer("Alice", "Nguyen", "alice.nguyen@meridian-labs.com",
                        Status.CUSTOMER, LocalDate.of(2023, 1, 15)),
                new Customer("Bob", "Martinez", "bob.martinez@bluefern.io",
                        Status.CUSTOMER, LocalDate.of(2022, 11, 3)),
                new Customer("Carol", "Schmidt", "carol.schmidt@meridian-labs.com",
                        Status.PROSPECT, null)
        ));
    }
}
