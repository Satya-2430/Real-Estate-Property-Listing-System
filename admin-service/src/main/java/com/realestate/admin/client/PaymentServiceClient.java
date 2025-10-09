package com.realestate.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    
    @GetMapping("/payments/transactions")
    List<Map<String, Object>> getAllTransactions();
    
    @GetMapping("/payments/transactions/user/{userId}")
    List<Map<String, Object>> getUserTransactions(@PathVariable Long userId);
    
    @GetMapping("/payments/methods/user/{userId}")
    List<Map<String, Object>> getUserPaymentMethods(@PathVariable Long userId);
}