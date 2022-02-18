package com.learn.graphq.context.dataloader;

import com.learn.graphq.domain.bank.Client;
import com.learn.graphq.service.BalanceService;
import com.learn.graphq.service.ClientService;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class DataLoaderRegistryFactory {
    public static final String BALANCE_DATA_LOADER = "BALANCE_DATA_LOADER";
    public static final String CLIENT_DATA_LOADER = "CLIENT_DATA_LOADER";
    @Autowired
    private  BalanceService balanceService;
    @Autowired
    private ClientService clientService;

    private static final ExecutorService balanceThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private static final ExecutorService clientThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    public DataLoaderRegistry create(String userId){
        var registry = new DataLoaderRegistry();
        registry.register(BALANCE_DATA_LOADER, createBalanceDataLoader(userId));
        registry.register(CLIENT_DATA_LOADER, createClientDataLoader(userId));

        return registry;

    }

    private DataLoader<UUID, Client> createClientDataLoader(String userId) {
        return DataLoader.newMappedDataLoader(bankAccountIds ->
                CompletableFuture.supplyAsync(() ->
                    clientService.getClientsFor(bankAccountIds, userId),
                clientThreadPool));
    }

    //since the balance service needs a UUID and returns BigDecimal
    //bankAccountIds are to be loaded by the resolver and the below md will return a map of id and value for th resolver to map out
    private DataLoader<UUID, BigDecimal> createBalanceDataLoader(String userId) {
        return DataLoader.newMappedDataLoader(bankAccountIds ->
             CompletableFuture.supplyAsync(() ->
                        balanceService.getBalanceFor(bankAccountIds, userId),
                     balanceThreadPool));
    }
}
