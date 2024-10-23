package br.com.bagnascojhoel.contactsaggregator.infrastructure.config;

import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class Resilience4jConfig {

    // could use the spring boot resiliency4j dependency and set this up on
    // application.properties

    @Bean
    public TimeLimiter timeLimiterContacts() {
        return TimeLimiter.of("contacts", TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofSeconds(5L))
                .cancelRunningFuture(true)
                .build());
    }
}
