package io.spring.workshop.tradingservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM_VALUE;

@Controller
public class QuotesController {
    //can it be shared like that ?
    //seems it is not run when no request to quoteFeeds is executed
    //and can be shared between all requests 
    private Flux<Quote> sss = WebClient.create("http://localhost:8081")
            .get()
            .uri("/quotes")
            .retrieve().bodyToFlux(Quote.class).log("sss").share();

    @GetMapping("/quotes")
    public String quotesPage() {
        return "quotes";
    }

    @GetMapping(value = "/quotes/feed", produces = TEXT_EVENT_STREAM_VALUE)
    //@ResponseBody is the key
    @ResponseBody
    public Flux<Quote> quoteFeeds() {
        return sss;
    }
}
