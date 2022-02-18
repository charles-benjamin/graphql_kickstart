package com.learn.graphq.domain.bank;

import lombok.Builder;
import lombok.Value;


import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Builder
@Value
public class BankAccount {
    UUID id;
    Client client;
    Currency currency;
    List<Asset> assets;
    BigDecimal balance;
}
