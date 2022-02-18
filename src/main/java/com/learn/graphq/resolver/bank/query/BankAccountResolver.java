package com.learn.graphq.resolver.bank.query;

import com.learn.graphq.context.dataloader.DataLoaderRegistryFactory;
import com.learn.graphq.domain.bank.BankAccount;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
public class BankAccountResolver implements GraphQLResolver<BankAccount> {
    public CompletableFuture<BigDecimal> balance(BankAccount bankAccount, DataFetchingEnvironment environment) {
        DataLoader<UUID, BigDecimal> dataLoader = environment.getDataLoader(DataLoaderRegistryFactory.BALANCE_DATA_LOADER);
        log.info("loading account - {} into loader for balance", bankAccount.getId());
        return dataLoader.load(bankAccount.getId());
    }
}
