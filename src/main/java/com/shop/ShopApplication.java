package com.shop;

import com.javaquasar.util.desktop.Browser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ShopApplication implements CommandLineRunner {

    @Value("${is.prod}")
    private boolean isProd;

	public static void main(String[] args) {
		//SpringApplication.run(ShopApplication.class, args);

		//Запуск без баннера Spring
		SpringApplication app = new SpringApplication(ShopApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
		if(!isProd) {
			Browser.browse("http://localhost:8080/test/hello");
			Browser.browse("http://localhost:8080/test/hello?name=Shop");
		}
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO
	}
}
