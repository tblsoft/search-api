package de.tblsoft.search.app;

import de.tblsoft.search.spring.QueryWebArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by tblsoft on 11.11.16.
 */
@SpringBootApplication
public class Server extends WebMvcConfigurerAdapter {


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        super.addArgumentResolvers(argumentResolvers);
        argumentResolvers.add(new QueryWebArgumentResolver());
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Server.class, args);



    }
}
