package com.web.demo.services;

import java.util.concurrent.ExecutionException;

public interface AggregatorService {
    String getAllResponses() throws InterruptedException, ExecutionException;
}
