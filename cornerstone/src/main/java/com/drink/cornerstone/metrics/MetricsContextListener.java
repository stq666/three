package com.drink.cornerstone.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlets.MetricsServlet;

/**
 * Created by newroc on 14-1-19.
 */
public class MetricsContextListener extends MetricsServlet.ContextListener {

    @Override
    protected MetricRegistry getMetricRegistry() {
        return MetricsManager.getMetricRegistry();
    }
}
