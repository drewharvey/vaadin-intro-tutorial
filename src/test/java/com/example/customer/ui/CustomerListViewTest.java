package com.example.customer.ui;

import com.vaadin.browserless.SpringBrowserlessTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class CustomerListViewTest extends SpringBrowserlessTest {

    @Test
    void view_renders() {
        var view = navigate(CustomerListView.class);
        assertThat(view).isNotNull();
    }
}
