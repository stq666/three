package com.drink.cornerstone.metrics;

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.servlets.HealthCheckServlet;

/**
 * Created by newroc on 14-1-19.
 */
public class HealthCheckContextListener extends HealthCheckServlet.ContextListener {

    @Override
    protected HealthCheckRegistry getHealthCheckRegistry() {
        return MetricsManager.getHealthCheckRegistry();
    }
}
