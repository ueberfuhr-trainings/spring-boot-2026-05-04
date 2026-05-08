package de.schulung.spring.customers;

import com.jayway.jsonpath.JsonPath;
import org.assertj.db.type.AssertDbConnectionFactory;
import org.assertj.db.type.Changes;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@AutoConfigureTestDatabase
public class CustomersApiWithDatabaseTests {

  @Autowired
  MockMvc mockMvc;

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private Changes customersTableChanges() {
    return AssertDbConnectionFactory
      .of(jdbcTemplate.getDataSource())
      .create()
      .changes()
      .table("customers")
      .build();
  }

  @Test
  void shouldCreateCustomerInDatabase() throws Exception {

    final var changes = customersTableChanges();
    changes.setStartPointNow();

    // Create the customer
    final var responseBody = mockMvc
      .perform(
        post("/customers")
          .contentType(MediaType.APPLICATION_JSON)
          .content("""
            {
              "name": "Tom Mayer",
              "birthdate": "2005-05-15",
              "state": "active"
            }
            """)
          .accept(MediaType.APPLICATION_JSON)
      )
      .andExpect(status().isCreated())
      .andReturn()
      .getResponse()
      .getContentAsString();

    final var uuid = JsonPath.read(responseBody, "$.uuid");

    changes.setEndPointNow();

    assertThat(changes)
      .hasNumberOfChanges(1)
      .change().isCreation()
      .rowAtEndPoint()
      .value("uuid").isEqualTo(UUID.fromString(uuid.toString()))
      .value("name").isEqualTo("Tom Mayer")
      .value("birth_date").isEqualTo(LocalDate.of(2005, Month.MAY, 15))
      .value("state").isEqualTo("active");

  }
}
