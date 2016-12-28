package com.fns.monbox.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Gauge;
import com.codahale.metrics.Histogram;
import com.codahale.metrics.Meter;
import com.codahale.metrics.Metric;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Snapshot;
import com.codahale.metrics.Timer;

import io.prometheus.client.Collector;
import io.prometheus.client.dropwizard.DropwizardExports;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.concurrent.TimeUnit;

/**
 * A custom implementation of {@link DropwizardExports}. This implementation adds more metrics
 * missing from the Prometheus client and cleans the code up a bit. Note that we couldn't simply
 * extend {@link DropwizardExports} because the methods we need to override are protected.
 */
public class PrometheusDropwizardExports extends Collector {

  private MetricRegistry registry;
  private static final Logger LOGGER = LoggerFactory.getLogger(PrometheusDropwizardExports.class);

  public PrometheusDropwizardExports(MetricRegistry registry) {
    this.registry = registry;
  }

  @Override
  public List<Collector.MetricFamilySamples> collect() {
    ArrayList<Collector.MetricFamilySamples> mfSamples = new ArrayList<>();
    for (SortedMap.Entry<String, Gauge> entry : registry.getGauges().entrySet()) {
      mfSamples.addAll(fromGauge(entry.getKey(), entry.getValue()));
    }
    for (SortedMap.Entry<String, Counter> entry : registry.getCounters().entrySet()) {
      mfSamples.addAll(fromCounter(entry.getKey(), entry.getValue()));
    }
    for (SortedMap.Entry<String, Histogram> entry : registry.getHistograms().entrySet()) {
      mfSamples.addAll(fromHistogram(entry.getKey(), entry.getValue()));
    }
    for (SortedMap.Entry<String, Timer> entry : registry.getTimers().entrySet()) {
      mfSamples.addAll(fromTimer(entry.getKey(), entry.getValue()));
    }
    for (SortedMap.Entry<String, Meter> entry : registry.getMeters().entrySet()) {
      mfSamples.addAll(fromMeter(entry.getKey(), entry.getValue()));
    }
    return mfSamples;
  }

  /**
   * Replace all unsupported chars with '_'.
   *
   * @param dropwizardName
   *          original metric name.
   * @return the sanitized metric name.
   */
  public static String sanitizeMetricName(String dropwizardName) {
    return dropwizardName.replaceAll("[^a-zA-Z0-9:_]", "_");
  }

  private List<Collector.MetricFamilySamples> fromCounter(String dropwizardName, Counter counter) {
    String name = sanitizeMetricName(dropwizardName);
    MetricFamilySamples.Sample sample = new MetricFamilySamples.Sample(name, new ArrayList<>(),
        new ArrayList<>(), counter.getCount());
    return Collections.singletonList(new MetricFamilySamples(name, Type.GAUGE,
        getHelpMessage(dropwizardName, counter), Collections.singletonList(sample)));
  }

  private static String getHelpMessage(String metricName, Metric metric) {
    return String.format("Generated from Dropwizard metric import (metric=%s, type=%s)", metricName,
        metric.getClass().getName());
  }

  private List<Collector.MetricFamilySamples> fromGauge(String dropwizardName, Gauge gauge) {
    String name = sanitizeMetricName(dropwizardName);
    Object obj = gauge.getValue();
    double value;
    if (obj instanceof Number) {
      value = ((Number) obj).doubleValue();
    } else if (obj instanceof Boolean) {
      value = ((Boolean) obj) ? 1 : 0;
    } else {
      LOGGER.warn(String.format("Invalid type for Gauge %s: %s", name, obj.getClass().getName()));
      return new ArrayList<>();
    }
    MetricFamilySamples.Sample sample = new MetricFamilySamples.Sample(name, new ArrayList<>(),
        new ArrayList<>(), value);
    return Collections.singletonList(new MetricFamilySamples(name, Type.GAUGE,
        getHelpMessage(dropwizardName, gauge), Collections.singletonList(sample)));
  }

  private List<Collector.MetricFamilySamples> fromHistogram(String dropwizardName,
      Histogram histogram) {
    return dumpSnapshotMetrics(dropwizardName, histogram.getSnapshot(), histogram.getCount(), 1.0,
        getHelpMessage(dropwizardName, histogram));
  }

  private List<Collector.MetricFamilySamples> fromTimer(String dropwizardName, Timer timer) {
    return dumpTimerMetrics(dropwizardName, timer, timer.getCount(),
        1.0D / TimeUnit.SECONDS.toMicros(1L), getHelpMessage(dropwizardName, timer));
  }

  private List<Collector.MetricFamilySamples> fromMeter(String dropwizardName, Meter meter) {
    String name = sanitizeMetricName(dropwizardName);
    return Collections.singletonList(new MetricFamilySamples(name + "_total", Type.COUNTER,
        getHelpMessage(dropwizardName, meter),
        Collections.singletonList(new MetricFamilySamples.Sample(name + "_total", new ArrayList<>(),
            new ArrayList<>(), meter.getCount())))

    );
  }

  private List<Collector.MetricFamilySamples> dumpSnapshotMetrics(String dropwizardName,
      Snapshot snapshot, long count, double factor, String helpMessage) {

    String name = sanitizeMetricName(dropwizardName);
    List<MetricFamilySamples.Sample> samples = getCommonSamples(snapshot, count, factor, name);
    return Collections
        .singletonList(new MetricFamilySamples(name, Type.SUMMARY, helpMessage, samples));
  }

  private List<Collector.MetricFamilySamples> dumpTimerMetrics(String dropwizardName, Timer timer,
      long count, double factor, String helpMessage) {

    Snapshot snapshot = timer.getSnapshot();

    String name = sanitizeMetricName(dropwizardName);
    List<MetricFamilySamples.Sample> samples = getCommonSamples(snapshot, count, factor, name);
    samples.add(new MetricFamilySamples.Sample(name + "_timer", Collections.singletonList("type"),
        Collections.singletonList("oneMinuteRate"), timer.getOneMinuteRate()));
    samples.add(new MetricFamilySamples.Sample(name + "_timer", Collections.singletonList("type"),
        Collections.singletonList("fiveMinuteRate"), timer.getFiveMinuteRate()));
    samples.add(new MetricFamilySamples.Sample(name + "_timer", Collections.singletonList("type"),
        Collections.singletonList("fifteenMinuteRate"), timer.getFifteenMinuteRate()));
    samples.add(new MetricFamilySamples.Sample(name + "_timer", Collections.singletonList("type"),
        Collections.singletonList("meanRate"), timer.getMeanRate()));

    return Collections
        .singletonList(new MetricFamilySamples(name, Type.SUMMARY, helpMessage, samples));
  }

  private List<MetricFamilySamples.Sample> getCommonSamples(Snapshot snapshot, long count,
      double factor, String name) {

    List<MetricFamilySamples.Sample> samples = new ArrayList<>();
    samples.add(new MetricFamilySamples.Sample(name + "_latency", Collections.singletonList("type"),
        Collections.singletonList("max"), snapshot.getMax() * factor));
    samples.add(new MetricFamilySamples.Sample(name + "_latency", Collections.singletonList("type"),
        Collections.singletonList("min"), snapshot.getMin() * factor));
    samples.add(new MetricFamilySamples.Sample(name + "_latency", Collections.singletonList("type"),
        Collections.singletonList("mean"), snapshot.getMean() * factor));
    samples.add(new MetricFamilySamples.Sample(name + "_latency", Collections.singletonList("type"),
        Collections.singletonList("median"), snapshot.getMedian() * factor));
    samples.add(new MetricFamilySamples.Sample(name + "_latency", Collections.singletonList("type"),
        Collections.singletonList("stdDev"), snapshot.getStdDev() * factor));

    samples.add(new MetricFamilySamples.Sample(name, Collections.singletonList("quantile"),
        Collections.singletonList("0.5"), snapshot.getMedian() * factor));
    samples.add(new MetricFamilySamples.Sample(name, Collections.singletonList("quantile"),
        Collections.singletonList("0.75"), snapshot.get75thPercentile() * factor));
    samples.add(new MetricFamilySamples.Sample(name, Collections.singletonList("quantile"),
        Collections.singletonList("0.95"), snapshot.get95thPercentile() * factor));
    samples.add(new MetricFamilySamples.Sample(name, Collections.singletonList("quantile"),
        Collections.singletonList("0.98"), snapshot.get98thPercentile() * factor));
    samples.add(new MetricFamilySamples.Sample(name, Collections.singletonList("quantile"),
        Collections.singletonList("0.99"), snapshot.get99thPercentile() * factor));
    samples.add(new MetricFamilySamples.Sample(name + "_count", new ArrayList<>(),
        new ArrayList<>(), count));

    return samples;
  }

}
