package com.fns.monbox.metrics;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Timer;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class MetricsAspect {

  private final MetricRegistry metricRegistry;

  @Autowired
  public MetricsAspect(MetricRegistry metricRegistry) {
    this.metricRegistry = metricRegistry;
  }

  /**
   * Around expression that times the api calls.
   *
   * @param joinPoint The joinPoint matched by the pointcut expression and proxied method call.
   */
  @Around(
      value = "execution(* com.tmobile.fiesta.controller.*Controller.*(..))")
  public Object timeApiCalls(ProceedingJoinPoint joinPoint) throws Throwable {
    Class<?> curClass = joinPoint.getTarget().getClass();
    MethodSignature ms = (MethodSignature) joinPoint.getSignature();
    Method method = ms.getMethod();
    Timer timer = metricRegistry.timer(MetricRegistry.name(curClass, method.getName()));
    try (Timer.Context context = timer.time()) {
      return joinPoint.proceed();
    }
  }
}