package de.schulung.spring.customers;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@RestController
@RequestMapping("/customers")
public class CustomersController {

  private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Stream<Customer> getCustomers(
    @RequestParam(value = "state", required = false)
    String stateFilter
  ) {
    return customers
      .values()
      .stream()
      .filter(customer -> stateFilter == null || customer.getState().equals(stateFilter));
  }

  @GetMapping(
    path = "/{uuid}",
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public Customer findCustomerById(@PathVariable UUID uuid) {
    if (!customers.containsKey(uuid)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    return customers.get(uuid);
  }

  @PostMapping
  // @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<Customer> createCustomer(@Valid @RequestBody Customer customer) {

    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);

    final var location = ServletUriComponentsBuilder
      .fromCurrentRequest()
      .path("/{uuid}")
      .buildAndExpand(customer.getUuid())
      .toUri();

    return ResponseEntity
      .created(location)
      .body(customer);

  }

  @DeleteMapping("/{uuid}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteCustomer(@PathVariable UUID uuid) {
    if (!customers.containsKey(uuid)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
    customers.remove(uuid);
  }


}
