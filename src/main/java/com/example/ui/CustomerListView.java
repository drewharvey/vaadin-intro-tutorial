package com.example.ui;

import com.example.backend.Customer;
import com.example.backend.Status;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.signals.Signal;
import com.vaadin.flow.signals.local.ValueSignal;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("customers")
public class CustomerListView extends VerticalLayout {

    private final Grid<Customer> grid = new Grid<>();

    private final ValueSignal<String> searchValue = new ValueSignal<>("");

    private final List<Customer> customers = getSampleCustomers();

    public CustomerListView() {
        setSizeFull();

        var search = new TextField();
        search.setPlaceholder("Search...");
        search.setValueChangeMode(ValueChangeMode.LAZY);
        search.bindValue(searchValue, searchValue::set);
        Signal.effect(search, () -> updateCustomerList(searchValue.get()));
        add(search);

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

    private void updateCustomerList(String query) {
        var filtered = customers;

        if (!query.isEmpty()) {
            var lower = query.toLowerCase();
            filtered = customers.stream()
                    .filter(c -> c.getFirstName().toLowerCase().contains(lower)
                            || c.getLastName().toLowerCase().contains(lower)
                            || c.getEmail().toLowerCase().contains(lower)
                            || c.getStatus().name().toLowerCase().contains(lower))
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        grid.setItems(filtered);
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
