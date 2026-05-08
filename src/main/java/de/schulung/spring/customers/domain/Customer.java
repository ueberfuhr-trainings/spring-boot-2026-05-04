package de.schulung.spring.customers.domain;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class Customer {

  private UUID uuid;
  @NotNull
  private String name;
  @NotNull
  private LocalDate birthdate;
  @NotNull
  private CustomerState state = CustomerState.ACTIVE;


}
