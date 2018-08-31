package com.shop;

import com.javaquasar.util.desktop.Browser;
import com.shop.config.PidContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShopApplication implements CommandLineRunner {

    @Value("${is.prod}")
    private boolean isProd;

	public static void main(String[] args) {
		//SpringApplication.run(ShopApplication.class, args);

		SpringApplication app = new SpringApplication(ShopApplication.class);
		//app.setBannerMode(Banner.Mode.OFF); //Запуск без баннера Spring
		app.addListeners(new ApplicationPidFileWriter(PidContainer.PATH_SHUTDOWN_PID));
		app.run(args);
	}


	@EventListener({ApplicationReadyEvent.class})
    void applicationReadyEvent() {
		if(!isProd) {
			//Browser.browse("https://localhost:8080/test/hello?name=Shop");
            Browser.browse("https://localhost:8080/home");
		    //Browser.browse("https://localhost:8080/h2-console/");
		}
    }

	@Override
	public void run(String... args) throws Exception {
		// TODO
	}
}
