package com.codewave.analyticsService.service;

import com.codewave.analyticsService.dto.CustomResponse;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AnalyticsService {
    List<CustomResponse> top10UsersBasedOnTheirTotalOrderValue();
    CompletableFuture<CustomResponse> topProductByCategory(String category);
}
