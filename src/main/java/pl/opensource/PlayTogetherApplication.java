package pl.opensource;

import javax.validation.Validator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@SpringBootApplication
@EnableScheduling
public class PlayTogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlayTogetherApplication.class, args);
	}
	
	@Bean
	public Validator validator() {
		return new LocalValidatorFactoryBean();
	}
}
