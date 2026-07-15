package com.example.ui;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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
    }
}
