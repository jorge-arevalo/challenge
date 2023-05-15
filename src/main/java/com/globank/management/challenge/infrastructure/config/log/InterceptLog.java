package com.globank.management.challenge.infrastructure.config.log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * Interceptor class for logging requests and responses.
 *
 * @author jorge-arevalo
 */
@Component
@Slf4j
public class InterceptLog implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    if (handler instanceof HandlerMethod) {
      HandlerMethod method = (HandlerMethod) handler;
      log.info("Start: {}, method: {}", method.getShortLogMessage(), request.getMethod());
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    if (handler instanceof HandlerMethod) {
      HandlerMethod method = (HandlerMethod) handler;
      log.info("End: {} with status: {}", method.getShortLogMessage(), response.getStatus());
    }
  }

}
