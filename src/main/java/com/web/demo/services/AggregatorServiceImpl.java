package com.web.demo.services;

import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
public class AggregatorServiceImpl implements AggregatorService {

    private final ApiService apiService;

    public AggregatorServiceImpl(ApiService apiService) {
        this.apiService = apiService;
        //this.apiWebClientService = apiWebClientService;
    }

    public String getAllResponses() throws InterruptedException, ExecutionException {
        CompletableFuture<String> api1 = apiService.callApi1();
        CompletableFuture<String> api2 = apiService.callApi2();
        CompletableFuture<String> api3 = apiService.callApi3();
        CompletableFuture<String> api4 = apiService.callApi4();

        // wait for all
        CompletableFuture.allOf(api1, api2, api3, api4).join();

        return api1.get() + "\n" +
                api2.get() + "\n" +
                api3.get() + "\n" +
                api4.get();
    }

   /* public String getAllResponsesTemp() throws Exception {
        CompletableFuture<String> a1 = apiWebClientService.callApi1();
        CompletableFuture<String> a2 = apiWebClientService.callApi2();
        CompletableFuture<String> a3 = apiWebClientService.callApi3();
        CompletableFuture<String> a4 = apiWebClientService.callApi4();

        CompletableFuture.allOf(a1, a2, a3, a4).join();

        return a1.get() + "\n" +
                a2.get() + "\n" +
                a3.get() + "\n" +
                a4.get();
    }*/
}
