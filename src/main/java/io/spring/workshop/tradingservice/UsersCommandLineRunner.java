package io.spring.workshop.tradingservice;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;


@Component
public class UsersCommandLineRunner implements CommandLineRunner {
    private final TradingUserRepository tradingUserRepository;

    public UsersCommandLineRunner(TradingUserRepository repository) {
        this.tradingUserRepository = repository;
    }


    private static final Consumer<Object> NOOP = whatever -> {
    };

    @Override
    public void run(String... args) throws Exception {
        List<TradingUser> tradingUsers = Arrays.asList(
                new TradingUser("milus", "Jakub Milkiewicz"),
                new TradingUser("bobas", "Marta Milkiewicz"),
                new TradingUser("gzyl", "Grzegorz Milkiewicz"),
                new TradingUser("adas", "Adam Slodowy"),
                new TradingUser("jajor", "Jedrzej Klobukowski")
        );
        // if you do not consume (subscribe to) the result of insert (being FLUX )than this insert will actually not be RUN !
        // So no users will be inserted !!! - tradingUserRepository.insert(tradingUsers);
        // So tradingUserRepository.insert(tradingUsers); is like method skeleton than finally needs to be RUN (by subscribing to its result)
        tradingUserRepository.insert(tradingUsers).subscribe(NOOP, System.err::println);
    }
}
