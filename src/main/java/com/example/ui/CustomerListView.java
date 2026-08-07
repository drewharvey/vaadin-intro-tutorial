package com.example.ui;

import com.example.backend.Customer;
import com.example.backend.CustomerService;
import com.example.backend.Status;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.signals.Signal;
import com.vaadin.flow.signals.local.ValueSignal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Route("customers")
public class CustomerListView extends VerticalLayout {

    private final CustomerService customerService;

    private final Grid<Customer> grid = new Grid<>();

    private final ValueSignal<String> searchValue = new ValueSignal<>("");
    private final ValueSignal<Customer> editedCustomer = new ValueSignal<>(null);

    private final Binder<Customer> binder = new Binder<>();

    public CustomerListView(CustomerService customerService) {
        this.customerService = customerService;

        setSizeFull();

        var toolbar = createToolbar();
        configureGrid();
        var form = createForm();

        var content = new HorizontalLayout(grid, form);
        content.setSizeFull();

        add(toolbar, content);
    }

    private HorizontalLayout createToolbar() {
        var title = new H3("Customers");

        var search = new TextField();
        search.setPlaceholder("Search...");
        search.setValueChangeMode(ValueChangeMode.LAZY);
        search.bindValue(searchValue, searchValue::set);
        Signal.effect(search, () -> updateCustomerList(searchValue.get()));

        var addBtn = new Button(VaadinIcon.PLUS.create());
        addBtn.addClickListener(e -> addCustomer());

        var layout = new HorizontalLayout(title, search, addBtn);
        layout.setWidthFull();
        layout.setFlexGrow(1, title);
        return layout;
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

        var saveBtn = new Button();
        saveBtn.bindText(editedCustomer.map(customer -> isNew(customer) ? "Create" : "Save"));
        saveBtn.addClickListener(e -> {
            save();
        });

        var discardBtn = new Button("Discard");
        discardBtn.addClickListener(e -> binder.readBean(editedCustomer.peek()));

        var deleteBtn = new Button("Delete");
        deleteBtn.addClickListener(e -> confirmDelete());
        deleteBtn.bindEnabled(editedCustomer.map(customer -> !isNew(customer)));

        var buttons = new HorizontalLayout(saveBtn, discardBtn, deleteBtn);

        var form = new FormLayout(
                firstName,
                lastName,
                email,
                status,
                customerSince,
                buttons
        );
        form.setWidth("300px");
        form.bindVisible(editedCustomer.map(customer -> customer != null));
        Signal.effect(form, () -> binder.readBean(editedCustomer.get()));
        return form;
    }

    private boolean isNew(Customer customer) {
        return customer != null && customer.getId() == null;
    }

    private void addCustomer() {
        editedCustomer.set(new Customer());
    }

    private void confirmDelete() {
        var customer = editedCustomer.peek();

        var dialog = new ConfirmDialog();
        dialog.setHeader("Confirm delete customer");
        dialog.setText("Are you sure you want to delete this %s %s?"
                .formatted(customer.getFirstName(), customer.getLastName()));
        dialog.setCancelable(true);
        dialog.setConfirmText("Delete");
        dialog.addConfirmListener(e -> {
            customerService.delete(customer);
            updateCustomerList();
        });
        dialog.open();
    }

    private void save() {
        try {
            var customer = editedCustomer.peek();
            binder.writeBean(customer);
            customerService.save(customer);
            updateCustomerList();
        } catch (ValidationException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void updateCustomerList() {
        updateCustomerList(searchValue.peek());
    }

    private void updateCustomerList(String query) {
        var filtered = customerService.findAll();

        if (!query.isEmpty()) {
            var lower = query.toLowerCase();
            filtered = filtered.stream()
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
