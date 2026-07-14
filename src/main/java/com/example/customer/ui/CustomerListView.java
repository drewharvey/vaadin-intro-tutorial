package com.example.customer.ui;

import com.example.base.ui.ViewTitle;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("")
@PageTitle("Customers")
@Menu(order = 0, icon = "icons/users.svg", title = "Customers")
class CustomerListView extends VerticalLayout {

    CustomerListView() {
        setSizeFull();
        add(new ViewTitle("Customers"));
    }
}
