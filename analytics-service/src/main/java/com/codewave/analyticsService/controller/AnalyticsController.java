package com.codewave.analyticsService.controller;

import com.codewave.analyticsService.dto.CustomResponse;
import com.codewave.analyticsService.service.AnalyticsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@AllArgsConstructor
@RequestMapping("api/analytics")
public class AnalyticsController {

    private AnalyticsService analyticsService;

    //Get top 10 users by their total order value
    @GetMapping("/top-users")
    public ResponseEntity<List<CustomResponse>> getTop10UsersBasedOnTheirTotalOrderValue(){
        List<CustomResponse> results = analyticsService.top10UsersBasedOnTheirTotalOrderValue();
        return ResponseEntity.ok(results);
    }

    //top product in given category
    @GetMapping("/top-product/{category}")
    public ResponseEntity<CustomResponse> getTopProduct(@PathVariable("category") String category){
         CustomResponse results;
        try {
            results = analyticsService.topProductByCategory(category).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(results);
    }

    //top product in given category using completableFuture
    @GetMapping("/top-product-cf/{category}")
    public ResponseEntity<CustomResponse> getTopProductCF(@PathVariable("category") String category){

        CustomResponse results;
        try {
            results = analyticsService.topProductByCategory(category).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(results);
    }


}
