package com.fns.monbox.config;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.servlet.InstrumentedFilterContextListener;
import com.fns.monbox.metrics.DefaultWebappMetricFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.MetricRepositoryAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.servlet.ServletContextEvent;

@Configuration
@AutoConfigureAfter(MetricRepositoryAutoConfiguration.class)
public class MetricsConfig {

  // @see http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-metrics.html#production-ready-code-hale-metrics
  @Autowired
  private MetricRegistry metricRegistry;


  /**
   * FilterRegistrationBean declaration it sets up a custom.
   * @return FilterRegistrationBean with filter DefaultWebAppMetricFilter.
   */
  @Bean(name = "filter:io.dropwizard.webAppMetrics")
  @DependsOn(value = "listener:io.dropwizard.monitoring")
  public FilterRegistrationBean webAppMetricsFilter() {
    final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
    registrationBean.setFilter(new DefaultWebappMetricFilter());
    return registrationBean;
  }

  @Bean(name = "listener:io.dropwizard.monitoring")
  public MonitoringListener monitoringListener() {
    return new MonitoringListener(metricRegistry);
  }

  protected static final class MonitoringListener extends InstrumentedFilterContextListener {

    private MetricRegistry metricRegistry;

    public MonitoringListener(MetricRegistry metricRegistry) {
      this.metricRegistry = metricRegistry;
    }

    @Override
    protected MetricRegistry getMetricRegistry() {
      return metricRegistry;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
      sce.getServletContext().setAttribute(
          DefaultWebappMetricFilter.REGISTRY_ATTRIBUTE, getMetricRegistry());
    }
  }
}