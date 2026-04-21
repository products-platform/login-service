package com.web.demo.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class ApiService {

    @Async("apiExecutor")
    public CompletableFuture<String> callApi1() throws InterruptedException {
        Thread.sleep(2000); // simulate delay
        return CompletableFuture.completedFuture("Response from API 1");
    }

    @Async("apiExecutor")
    public CompletableFuture<String> callApi2() throws InterruptedException {
        Thread.sleep(3000);
        return CompletableFuture.completedFuture("Response from API 2");
    }

    @Async("apiExecutor")
    public CompletableFuture<String> callApi3() throws InterruptedException {
        Thread.sleep(1500);
        return CompletableFuture.completedFuture("Response from API 3");
    }

    @Async("apiExecutor")
    public CompletableFuture<String> callApi4() throws InterruptedException {
        Thread.sleep(2500);
        return CompletableFuture.completedFuture("Response from API 4");
    }
}
