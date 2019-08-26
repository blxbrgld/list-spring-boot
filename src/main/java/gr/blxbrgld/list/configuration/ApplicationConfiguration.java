package gr.blxbrgld.list.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * Application Configuration
 * @author blxbrgld
 */
@SpringBootApplication
@ComponentScan("gr.blxbrgld.list")
@EntityScan("gr.blxbrgld.list.model")
public class ApplicationConfiguration {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationConfiguration.class, args);
	}
}
