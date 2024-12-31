package com.codewave.orderservice.service;

import com.codewave.orderservice.dto.CustomResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public interface OrderAnalyticsService {
    List<CustomResponse> getTotalCountAndSale(LocalDate fromDate, LocalDate toDate);
}
