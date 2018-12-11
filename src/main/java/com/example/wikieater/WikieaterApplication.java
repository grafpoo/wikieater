package com.example.wikieater;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
@Slf4j
public class WikieaterApplication {


	@Bean
	WebClient getWebClient() {
		return WebClient.create("https://stream.wikimedia.org");
	}
	@Bean
	CommandLineRunner demo(WebClient client) {
		return args -> {
			client.get()
					.uri("/v2/stream/recentchange")
					.accept(MediaType.TEXT_EVENT_STREAM)
					.retrieve()
					.bodyToFlux(String.class)
					.map(s -> String.valueOf(s))
					.subscribe(msg -> {
					    log.info(msg);
					});
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(WikieaterApplication.class, args);
	}
}
