package com.shop;

import com.javaquasar.util.desktop.Browser;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ShopApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ShopApplication.class, args);
	}

	@EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
		Browser.browse("http://localhost:8080/test/hello");
		Browser.browse("http://localhost:8080/test/hello?name=Shop");
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO
	}
}
