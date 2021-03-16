package fusikun.com.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import fusikun.com.api.interceptors.AuthorizationInterceptor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan("fusikun.com.api")
@EnableAutoConfiguration
@EnableSwagger2
@PropertySource("classpath:configure.properties")
public class ApiApplication {

	@Autowired
	Environment env;

	@Autowired
	AuthorizationInterceptor authorizationInterceptor;

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				String domainStrConfig = env.getProperty("configure.api.address");
				String[] domains = domainStrConfig.split(",");
				registry.addMapping("/**").allowedOrigins(domains).allowedMethods("*");
			}

			@Override
			public void addInterceptors(InterceptorRegistry registry) {
				registry.addInterceptor(authorizationInterceptor);
			}
		};
	}

}
