package ru.jug.nsk.spring.boot.test.sitemessages;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SuppressWarnings("NonFinalUtilityClass")
@SpringBootApplication
public class SiteMessagesApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SiteMessagesApplication.class)
				.bannerMode(Banner.Mode.OFF)
				.logStartupInfo(false)
				.build().run(args);
	}
}
