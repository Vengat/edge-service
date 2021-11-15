package com.vengat.tuts.edgeservice.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.CompositeReactiveHealthContributor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.ReactiveHealthContributor;
import org.springframework.boot.actuate.health.ReactiveHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Configuration
public class HealthConfig {
	

	@Autowired
	WebClient webClient;
	
	@Bean
	ReactiveHealthContributor healthCheckMicroServices() {
		final Map<String, ReactiveHealthIndicator> registry = new LinkedHashMap<>();

		registry.put("product", () -> getHealth("http://product"));
		registry.put("recommendation",    () -> 
		getHealth("http://recommendation"));
		registry.put("review",            () -> 
		getHealth("http://review"));
		registry.put("product-composite", () -> 
		getHealth("http://product-composite"));

		return CompositeReactiveHealthContributor.fromMap(registry);

	}

	private Mono<Health> getHealth(String baseUrl) {

		String url = baseUrl + "/actuator/health";
		//		TODO: Enable logging
				LOG.debug("Setting up a call to the Health API on URL: {}", 
					      url);

		return webClient.get().uri(url).retrieve()
				.bodyToMono(String.class)
				.map(s -> new Health.Builder().up().build())
				.onErrorResume(ex -> 
				Mono.just(new Health.Builder().down(ex).build()))
				.log(LOG.getName(), FINE);
	}
}
