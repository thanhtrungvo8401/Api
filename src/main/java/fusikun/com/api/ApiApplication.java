package fusikun.com.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@SpringBootApplication
@ComponentScan("fusikun.com.api")
@EnableAutoConfiguration
public class ApiApplication {

    @Autowired
    Environment env;

    @Autowired
    AuthorizationInterceptor authorizationInterceptor;
    static Logger logger = LoggerFactory.getLogger(ApiApplication.class);


    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
        logger.info("MEOMEO-KUN-API IS RESTARTED!");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String domainStrConfig = env.getProperty("configure.api.address");
                try {
                    String[] domains = domainStrConfig.split(",");
                    registry.addMapping("/**").allowedOrigins(domains).allowedMethods("*");
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Check check configure.api.address");
                }

            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(authorizationInterceptor);
            }
        };
    }

}
