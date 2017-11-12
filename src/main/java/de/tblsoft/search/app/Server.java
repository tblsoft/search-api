package de.tblsoft.search.app;

import de.tblsoft.search.spring.QueryWebArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by tblsoft on 11.11.16.
 */
@SpringBootApplication
@EnableCircuitBreaker
public class Server extends WebMvcConfigurerAdapter {


    @Bean
    public RestTemplate rest(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new QueryWebArgumentResolver());
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Server.class, args);



    }
}
