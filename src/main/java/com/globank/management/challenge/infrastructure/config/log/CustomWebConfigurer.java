package com.globank.management.challenge.infrastructure.config.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Implementation class for {@link WebMvcConfigurer} to add interceptors.
 *
 * @author jorge-arevalo
 */
@Component
@RequiredArgsConstructor
public class CustomWebConfigurer implements WebMvcConfigurer {

  private final InterceptLog interceptLog;

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(interceptLog);
  }

}
