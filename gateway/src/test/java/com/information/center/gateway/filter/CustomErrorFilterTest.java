package com.information.center.gateway.filter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.monitoring.CounterFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.cloud.netflix.zuul.metrics.EmptyCounterFactory;

@RunWith(MockitoJUnitRunner.class)
public class CustomErrorFilterTest {

  private static final String FILTER_TYPE = "error";
  private static final String THROWABLE_KEY = "throwable";
  private static final int FILTER_ORDER = -1;
  private CustomErrorFilter customErrorFilter;
  private RequestContext requestContext;


  @Before
  public void setUp() {
    customErrorFilter = new CustomErrorFilter();
    requestContext = mock(RequestContext.class);
    RequestContext.testSetCurrentContext(requestContext);
    CounterFactory.initialize(new EmptyCounterFactory());
  }


  @Test
  public void shouldFilter_expectTrue() {
    boolean response = customErrorFilter.shouldFilter();

    assertTrue(response);
  }

  @Test
  public void filterType() {
    String response = customErrorFilter.filterType();

    assertEquals(FILTER_TYPE, response);
  }

  @Test
  public void filterOrder() {
    int response = customErrorFilter.filterOrder();

    assertEquals(FILTER_ORDER, response);
  }

  @Test
  public void run_exceptionNotTypeZuulException() {
    when(requestContext.get(THROWABLE_KEY)).thenReturn(null);

    Object response = customErrorFilter.run();

    assertNull(response);
    verify(requestContext, never()).remove(THROWABLE_KEY);
  }


  @Test
  public void run_expectStatusServerUnavailable() {
    when(requestContext.get(THROWABLE_KEY))
        .thenReturn(new ZuulException(new Throwable("ZuulException"),
            INTERNAL_SERVER_ERROR.value(),
            "ErrorCause"));

    Object response = customErrorFilter.run();

    assertNull(response);
    verify(requestContext).remove(THROWABLE_KEY);
    verify(requestContext).setResponseStatusCode(SERVICE_UNAVAILABLE.value());
  }

}
