package com.codewave.orderservice.controller;

import com.codewave.orderservice.dto.CustomResponse;
import com.codewave.orderservice.service.OrderAnalyticsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/analytics")
public class OrderAnalyticsController {
    private OrderAnalyticsService orderAnalyticsService;

    //get product's order count and it's corresponding sales
    @GetMapping("/total-count-sales")
    public ResponseEntity<List<CustomResponse>> getTotalCountAndSales(@RequestParam("fromDate")LocalDate fromDate,
                                                                @RequestParam("toDate") LocalDate toDate){
        List<CustomResponse> totalCountAndSales = orderAnalyticsService.getTotalCountAndSale(fromDate, toDate);
        return ResponseEntity.ok(totalCountAndSales);
    }
}
