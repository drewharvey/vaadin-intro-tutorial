package com.example;

import com.vaadin.flow.component.page.ColorScheme;
import com.vaadin.flow.theme.aura.Aura;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.component.page.Push;

/**
 * The starting point of the application. Running {@code main} starts an embedded web server and
 * the app itself — there is no separate server to install or deploy to.
 * <p>
 * The annotations configure the application as a whole: {@code @SpringBootApplication} turns on
 * Spring Boot, which creates the application's objects and wires them together, and the two
 * {@code @StyleSheet} annotations load Vaadin's Aura theme and this app's own styles.
 */
@SpringBootApplication
@StyleSheet(Aura.STYLESHEET)
@StyleSheet("styles.css") // Your custom styles
@ColorScheme(ColorScheme.Value.LIGHT_DARK)
@Push
public class Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
