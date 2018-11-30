package ru.jug.nsk.spring.boot.test.client;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class ClientDataApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(ClientDataApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.logStartupInfo(false)
				.build().run(args);
	}
}
