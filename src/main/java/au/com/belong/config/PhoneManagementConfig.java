package au.com.belong.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class PhoneManagementConfig {

	@Bean
	public OpenAPI phoneManagementOpenAPI() {
		return new OpenAPI().components(new Components())
				.info(new Info().title("Phone Management API")
						.description("This is an API doc for Phone Management.")
						.termsOfService("terms").contact(new Contact().email("belong@belong.com"))
						.license(new License().name("GNU")).version("1.0"));
	}

}
