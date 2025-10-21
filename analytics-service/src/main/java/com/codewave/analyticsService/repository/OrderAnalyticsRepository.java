package com.codewave.analyticsService.repository;

import com.codewave.analyticsService.entity.OrderAnalytics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderAnalyticsRepository extends JpaRepository<OrderAnalytics, Long> {
}
