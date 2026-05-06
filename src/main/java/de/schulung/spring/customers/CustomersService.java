package de.schulung.spring.customers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class CustomersService {

  private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

  public Stream<Customer> getCustomers() {
    return customers
      .values()
      .stream();
  }

  public Stream<Customer> getCustomersByState(String state) {
    return this
      .getCustomers()
      .filter(customer -> customer.getState().equals(state));
  }

  public Optional<Customer> getCustomerByUuid(UUID uuid) {
    return Optional
      .ofNullable(customers.get(uuid));
  }

  public void createCustomer(Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
  }

  public boolean existsCustomerByUuid(UUID uuid) {
    return customers.containsKey(uuid);
  }

  public void deleteCustomer(UUID uuid) {
    customers.remove(uuid);
  }


}
