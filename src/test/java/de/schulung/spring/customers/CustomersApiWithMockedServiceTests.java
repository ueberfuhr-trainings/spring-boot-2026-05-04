package de.schulung.spring.customers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomersApiWithMockedServiceTests {

  @Autowired
  MockMvc mockMvc;

  @MockitoBean
  CustomersService customersService;

  @Test
  void shouldReturn404WhenGetCustomerByUuidNotExisting() throws Exception {

    var uuid = UUID.randomUUID();

    when(customersService.getCustomerByUuid(uuid))
      .thenReturn(Optional.empty());

    mockMvc
      .perform(get("/customers/{uuid}", uuid))
      .andExpect(status().isNotFound());
  }

  @Test
  void shouldNotCreateCustomerWithInvalidAccept() throws Exception {
    mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
            {
              "name": "Tom Mayer",
              "birthdate": "2026-05-05",
              "state": "active"
            }
            """)
          .accept(MediaType.APPLICATION_XML)
      )
      .andExpect(status().isNotAcceptable());

    verify(customersService, never())
      .createCustomer(any());

  }

}
