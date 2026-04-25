package com.web.demo.services;
/*

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.concurrent.CompletableFuture;

@Service
public class ApiWebClientService {

    private final WebClient webClient;

    public ApiWebClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Async("apiExecutor")
    @CircuitBreaker(name = "apiService", fallbackMethod = "fallback")
    @Retry(name = "apiService")
    @TimeLimiter(name = "apiService")
    public CompletableFuture<String> callApi1() {
        return webClient.get()
                .uri("/posts/1")
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();
    }

    @Async("apiExecutor")
    @CircuitBreaker(name = "apiService", fallbackMethod = "fallback")
    @Retry(name = "apiService")
    @TimeLimiter(name = "apiService")
    public CompletableFuture<String> callApi2() {
        return webClient.get()
                .uri("/posts/2")
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();
    }

    @Async("apiExecutor")
    @CircuitBreaker(name = "apiService", fallbackMethod = "fallback")
    @Retry(name = "apiService")
    @TimeLimiter(name = "apiService")
    public CompletableFuture<String> callApi3() {
        return webClient.get()
                .uri("/posts/3")
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();
    }

    @Async("apiExecutor")
    @CircuitBreaker(name = "apiService", fallbackMethod = "fallback")
    @Retry(name = "apiService")
    @TimeLimiter(name = "apiService")
    public CompletableFuture<String> callApi4() {
        return webClient.get()
                .uri("/posts/4")
                .retrieve()
                .bodyToMono(String.class)
                .toFuture();
    }

    // fallback method
    public CompletableFuture<String> fallback(Throwable ex) {
        return CompletableFuture.completedFuture("Fallback response: " + ex.getMessage());
    }
}*/
