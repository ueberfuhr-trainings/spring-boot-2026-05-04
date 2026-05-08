package de.schulung.spring.customers.domain;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface CustomersSink {

  Stream<Customer> findAll();

  default Stream<Customer> findByState(String state) {
    return findAll()
      .filter(c -> c.getState().equals(state));
  }

  default Optional<Customer> findById(UUID uuid) {
    return findAll()
      .filter(c -> c.getUuid().equals(uuid))
      .findFirst();
  }

  void save(Customer customer);

  default boolean existsById(UUID uuid) {
    return findById(uuid)
      .isPresent();
  }

  void deleteById(UUID uuid);

}
