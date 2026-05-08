package de.schulung.spring.customers.persistence.jpa;

import de.schulung.spring.customers.domain.Customer;
import de.schulung.spring.customers.domain.CustomersSink;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Component
@ConditionalOnProperty(
  name = "application.persistence.type",
  havingValue = "jpa",
  matchIfMissing = true
)
@RequiredArgsConstructor
public class CustomersSinkJpaImpl
  implements CustomersSink {

  private final CustomersRepository repo;

  @Override
  public Stream<Customer> findAll() {
    return repo
      .findAll()
      .stream();
  }

  @Override
  public Stream<Customer> findByState(String state) {
    return repo
      .findCustomerByState(state)
      .stream();
  }

  @Override
  public Optional<Customer> findById(UUID uuid) {
    return repo
      .findById(uuid);
  }

  @Override
  public void save(Customer customer) {
    repo.save(customer);
  }

  @Override
  public boolean existsById(UUID uuid) {
    return repo.existsById(uuid);
  }

  @Override
  public void deleteById(UUID uuid) {
    repo.deleteById(uuid);
  }
}
