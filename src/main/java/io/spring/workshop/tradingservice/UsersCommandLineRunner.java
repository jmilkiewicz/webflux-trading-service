package io.spring.workshop.tradingservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class UsersCommandLineRunner implements CommandLineRunner {
    private final TradingUserRepository tradingUserRepository;

    public UsersCommandLineRunner(TradingUserRepository repository) {
        this.tradingUserRepository = repository;
    }


    @Override
    public void run(String... args) throws Exception {
        List<TradingUser> tradingUsers = Arrays.asList(
                new TradingUser("milus", "Jakub Milkiewicz"),
                new TradingUser("bobas", "Marta Milkiewicz"),
                new TradingUser("gzyl", "Grzegorz Milkiewicz"),
                new TradingUser("adas", "Adam Slodowy"),
                new TradingUser("jajor", "Jedrzej Klobukowski")
        );
        tradingUserRepository.insert(tradingUsers).then().block();
    }
}
