package com.drink.cornerstone.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.health.HealthCheckRegistry;

/**
 * Created by newroc on 14-1-19.
 */
public class MetricsManager {
    private static final MetricRegistry METRIC_REGISTRY = new MetricRegistry();
    private static final HealthCheckRegistry HEALTH_CHECK_REGISTRY = new HealthCheckRegistry();

    public static MetricRegistry getMetricRegistry() {
        return METRIC_REGISTRY;
    }
    public static HealthCheckRegistry getHealthCheckRegistry() {
        return HEALTH_CHECK_REGISTRY;
    }
}
