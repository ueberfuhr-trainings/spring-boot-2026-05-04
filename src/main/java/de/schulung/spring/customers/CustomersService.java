package de.schulung.spring.customers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@Validated
@Service
@RequiredArgsConstructor
public class CustomersService {

  private final CustomersRepository repo;

  public Stream<Customer> getCustomers() {
    return repo
      .findAll()
      .stream();
  }

  public Stream<Customer> getCustomersByState(String state) {
    return repo
      .findCustomerByState(state)
      .stream();
  }

  public Optional<Customer> getCustomerByUuid(UUID uuid) {
    return repo
      .findById(uuid);
  }

  public void createCustomer(@NotNull @Valid Customer customer) {
    repo.save(customer);
  }

  public boolean existsCustomerByUuid(UUID uuid) {
    return repo.existsById(uuid);
  }

  public void deleteCustomer(UUID uuid) {
    repo.deleteById(uuid);
  }


}
