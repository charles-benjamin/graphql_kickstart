package com.learn.graphq.resolver.bank.query;

import com.learn.graphq.context.CustomGraphQLContext;
import com.learn.graphq.domain.bank.BankAccount;
import com.learn.graphq.domain.bank.Currency;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Slf4j
public class BankAccountQueryResolver implements GraphQLQueryResolver {
    @Autowired
    BankAccountRepository bankAccountRepository;

    public BankAccount bankAccount(UUID id, DataFetchingEnvironment environment) {
        log.info("Retrieving bank account id : {}", id);

        CustomGraphQLContext context = environment.getContext();

        log.info("User id - {}", context.getUserId());

        return BankAccount
                .builder()
                .id(id)
                .currency(Currency.USD)

                .build();
    }

    public List<BankAccount> bankAccounts(DataFetchingEnvironment dataFetchingEnvironment){
        CustomGraphQLContext context = dataFetchingEnvironment.getContext();
        log.info("User Id - {}", context.getUserId());
        List<BankAccount> accounts = bankAccountRepository.getBankAccounts();
        return accounts;
    }
}
