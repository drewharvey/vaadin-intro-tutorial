package com.example.ui;

import com.example.backend.Customer;
import com.example.backend.Status;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.Validator;
import com.vaadin.flow.data.binder.ValueContext;
import com.vaadin.flow.data.validator.EmailValidator;
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
    private final ValueSignal<Customer> editedCustomer = new ValueSignal<>(null);

    private final List<Customer> customers = getSampleCustomers();

    public CustomerListView() {
        setSizeFull();

        var search = createSearch();
        configureGrid();
        var form = createForm();

        var content = new HorizontalLayout(grid, form);
        content.setSizeFull();

        add(search, content);
    }

    private TextField createSearch() {
        var search = new TextField();
        search.setPlaceholder("Search...");
        search.setValueChangeMode(ValueChangeMode.LAZY);
        search.bindValue(searchValue, searchValue::set);
        Signal.effect(search, () -> updateCustomerList(searchValue.get()));
        return search;
    }

    private void configureGrid() {
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
        grid.asSingleSelect().bindValue(editedCustomer, editedCustomer::set);
    }

    private FormLayout createForm() {
        var binder = new Binder<Customer>();

        var firstName = new TextField("First name");
        binder.forField(firstName)
                .asRequired("First name is required")
                .bind(Customer::getFirstName, Customer::setFirstName);

        var lastName = new TextField("Last name");
        binder.forField(lastName)
                .asRequired("Last name is required")
                .bind(Customer::getLastName, Customer::setLastName);

        var email = new EmailField("Email");
        binder.forField(email)
                .asRequired("Email is required")
                .bind(Customer::getEmail, Customer::setEmail);

        var status = new ComboBox<Status>("Status");
        status.setItems(Status.values());
        binder.forField(status)
                .asRequired("Status is required")
                .bind(Customer::getStatus, Customer::setStatus);

        var customerSince = new DatePicker("Customer since");
        binder.bind(customerSince, Customer::getCustomerSince, Customer::setCustomerSince);

        var form = new FormLayout(
                firstName,
                lastName,
                email,
                status,
                customerSince
        );
        form.setWidth("300px");
        form.bindVisible(editedCustomer.map(customer -> customer != null));
        Signal.effect(form, () -> binder.setBean(editedCustomer.get()));
        return form;
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
