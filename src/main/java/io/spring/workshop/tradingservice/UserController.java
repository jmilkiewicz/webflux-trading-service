package io.spring.workshop.tradingservice;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class UserController {
    private final TradingUserRepository tradingUserRepository;

    public UserController(TradingUserRepository tradingUserRepository) {
        this.tradingUserRepository = tradingUserRepository;
    }

    //this is not streaming, it waits for full flux and then returns it
    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public Flux<TradingUser> getAllUsers() {
        return tradingUserRepository
                .findAll()
                .doOnRequest((ile) -> System.out.println(Thread.currentThread() + " chca " + ile))
                .zipWith(Flux.interval(Duration.ofMillis(500)), (user, l) -> user);
    }

    //on APPLICATION_STREAM_JSON_VALUE we have streaming
    @GetMapping(path = "/userss", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<TradingUser> getAllUsersStreaming() {
        return tradingUserRepository
                .findAll()
                .doOnRequest((ile) -> System.out.println(Thread.currentThread() + " chca " + ile))
                .zipWith(Flux.interval(Duration.ofMillis(500)), (user, l) -> user);
    }

    @GetMapping(path = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<TradingUser> getUser(@PathVariable String username) {
        System.out.println(tradingUserRepository);
        return tradingUserRepository.findByUserName(username);
    }
}
