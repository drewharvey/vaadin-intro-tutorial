package com.example.ui;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.Layout;

@Layout
public class MainLayout extends AppLayout {

    public MainLayout() {
        setPrimarySection(Section.DRAWER);

        var logo = VaadinIcon.VAADIN_H.create();

        var appName = new Span("Polaris CRM");
        appName.addClassNames("app-name");

        var header = new HorizontalLayout(logo, appName);
        header.setPadding(true);

        var nav = new SideNav();
        nav.addItem(new SideNavItem("Home", HomeView.class, VaadinIcon.HOME.create()));
        nav.addItem(new SideNavItem("Customers", CustomerListView.class, VaadinIcon.USER.create()));

        addToDrawer(header, nav);
    }
}
