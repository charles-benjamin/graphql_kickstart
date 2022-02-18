package com.learn.graphq.resolver.bank;

import com.learn.graphq.context.dataloader.DataLoaderRegistryFactory;
import com.learn.graphq.domain.bank.BankAccount;
import com.learn.graphq.domain.bank.Client;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class ClientResolver implements GraphQLResolver<BankAccount> {
//    //DataFetcherResult will provide flexibility to return the data and pass on any errors
//    public DataFetcherResult<Client> client(BankAccount bankAccount) {
//        log.info("request client data for bank account id - {}", bankAccount.getId());
//
//        //use this to throw consumer friendly message
//        //throw new GraphQLException("Client Unavailable");
//        return DataFetcherResult.<Client>newResult()
//                .data(Client.builder()
//                        .id(UUID.randomUUID())
//                        .firstName("Philip")
//                        .lastName("Starritt")
//                        .build())
//                .error(new GenericGraphQLError("Could not get sub-client id"))
//                .build();
//
//    }

    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

//    public CompletableFuture<Client> client(BankAccount bankAccount) {
//        log.info("request client data for bank account id - {}", bankAccount.getId());
//        return CompletableFuture.supplyAsync(()-> {
//            log.info("request client data for bank account id - {}", bankAccount.getId());
//            return Client.builder().id(UUID.randomUUID()).firstName("Philip").lastName("Starritt").build();
//        }, executorService);
//
//
//    }

    public CompletableFuture <Client> client(BankAccount bankAccount, DataFetchingEnvironment environment){
        DataLoader<UUID, Client> dataLoader = environment.getDataLoader(DataLoaderRegistryFactory.CLIENT_DATA_LOADER);
        log.info("loading account - {} into loader for client", bankAccount.getId());
        return dataLoader.load(bankAccount.getId());
    }

}
