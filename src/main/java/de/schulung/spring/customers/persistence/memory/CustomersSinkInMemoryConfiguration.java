package de.schulung.spring.customers.persistence.memory;

import de.schulung.spring.customers.domain.CustomersSink;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomersSinkInMemoryConfiguration {

  @Bean
  @ConditionalOnMissingBean(CustomersSink.class)
  CustomersSink inMemoryCustomersSink() {
    return new CustomersSinkInMemoryImpl();
  }

}
