package fusikun.com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

/**
 * 
 * @author thtrungvo
 * We use HIBERNATE=> remove some auto-configuration of jpa (exclude=...)
 * https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world?fbclid=IwAR0ljU7yB6dhqnz_D3YB9DJZ3CgGVz2nCAfbWdWeWLS6oisoEGvrMv4doz0
 */

@SpringBootApplication
@ComponentScan("fusikun.com.api")
@EnableAutoConfiguration(exclude = { 
		DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class, 
		HibernateJpaAutoConfiguration.class })
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
