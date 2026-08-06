package com.example.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

/**
 * The first view of the application — a view is a Java class the user can navigate to in the
 * browser. {@code @Route("")} maps it to the application's root URL, and extending a layout
 * class ({@code VerticalLayout}) means components added to it stack top to bottom. It starts
 * out empty: the tutorial's UI code goes here.
 */
@Route("")
class HomeView extends VerticalLayout {

    HomeView() {
        var name = new TextField("Name");
        add(name);

        var dob = new DatePicker("Date of birth");
        add(dob);

        var save = new Button("Save");
        save.addClickListener(e -> {
            Notification.show("Details saved!");
        });

        var discard = new Button("Discard");
        discard.addClickListener(e -> {
            Notification.show("Details discarded");
        });

        var buttonsLayout = new HorizontalLayout();
        buttonsLayout.add(save, discard);
        add(buttonsLayout);
    }
}
