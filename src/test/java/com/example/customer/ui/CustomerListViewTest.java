package com.example.customer.ui;

import com.vaadin.browserless.SpringBrowserlessTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * UI test for {@link CustomerListView}. It runs the real view without opening a browser:
 * Vaadin's browserless testing lets a test navigate to the view and interact with its
 * components directly in Java.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CustomerListViewTest extends SpringBrowserlessTest {

    @Test
    void view_renders() {
        var view = navigate(CustomerListView.class);
        assertThat(view).isNotNull();
    }
}
