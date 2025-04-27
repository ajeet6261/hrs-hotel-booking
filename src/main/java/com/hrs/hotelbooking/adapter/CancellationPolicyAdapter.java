package com.hrs.hotelbooking.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hrs.hotelbooking.model.CancellationPolicy;
import com.hrs.hotelbooking.utils.CacheUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Component
public class CancellationPolicyAdapter {
    private final RestTemplate restTemplate;
    private final ExecutorService executorService;
    private final ObjectMapper objectMapper;
    
    @Value("${cancellation.policy.api.url}")
    private String apiUrl;

    @Autowired
    public CancellationPolicyAdapter(ExecutorService executorService) {
        this.restTemplate = CacheUtil.getRestTemplate();
        this.executorService = executorService;
        this.objectMapper = CacheUtil.getObjectMapper();
    }

    public CompletableFuture<List<CancellationPolicy>> getCancellationPolicies(String bookingId) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Call third-party API
                String response = restTemplate.getForObject(apiUrl + "/" + bookingId, String.class);
                return objectMapper.readValue(response, new TypeReference<List<CancellationPolicy>>() {});
            } catch (Exception e) {
                // If API call fails, return from cache
                return getDefaultCancellationPolicies();
            }
        }, executorService);
    }

    private List<CancellationPolicy> getDefaultCancellationPolicies() {
        List<CancellationPolicy> policies = new ArrayList<>();
        
        CancellationPolicy policy1 = new CancellationPolicy();
        policy1.setPolicyId("CP001");
        policy1.setDescription("Free cancellation up to 24 hours before check-in");
        policy1.setHoursBeforeCheckIn(24);
        policy1.setCancellationCharge(0.0);
        policy1.setRefundable(true);
        policies.add(policy1);

        CancellationPolicy policy2 = new CancellationPolicy();
        policy2.setPolicyId("CP002");
        policy2.setDescription("50% charge for cancellation within 24 hours of check-in");
        policy2.setHoursBeforeCheckIn(24);
        policy2.setCancellationCharge(50.0);
        policy2.setRefundable(true);
        policies.add(policy2);

        CancellationPolicy policy3 = new CancellationPolicy();
        policy3.setPolicyId("CP003");
        policy3.setDescription("Non-refundable for cancellation within 12 hours of check-in");
        policy3.setHoursBeforeCheckIn(12);
        policy3.setCancellationCharge(100.0);
        policy3.setRefundable(false);
        policies.add(policy3);

        return policies;
    }
} 