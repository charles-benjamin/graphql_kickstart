package com.learn.graphq.service;

import com.learn.graphq.domain.bank.Client;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class ClientService {
    public Map<UUID, Client> getClientsFor(Set<UUID> bankAccountIds, String userId) {
        log.info("Requesting Client references for account ids - {}", bankAccountIds);
        return Map.of(UUID.fromString("c6aa269a-812b-49d5-b178-a739a1ed74cc"), Client.builder().firstName("Data").build(),
                UUID.fromString("024bb503-5c0f-4d60-aa44-db19d87042f4"), Client.builder().firstName("Loader").build()
        );

    }
}
