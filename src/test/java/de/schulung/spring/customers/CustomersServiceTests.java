package de.schulung.spring.customers;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
public class CustomersServiceTests {

  @Autowired
  CustomersService customersService;

  @Test
  void shouldValidateOnCreateCustomerWithNull() {
    assertThatThrownBy(() -> customersService.createCustomer(null))
      .isInstanceOf(ValidationException.class);
  }

  @Test
  void shouldValidateOnCreateCustomerWithInvalidCustomer() {
    assertThatThrownBy(() -> customersService.createCustomer(new Customer()))
      .isInstanceOf(ValidationException.class);
  }

}
