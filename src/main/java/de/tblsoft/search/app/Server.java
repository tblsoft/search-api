package de.tblsoft.search.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * Created by tblsoft on 11.11.16.
 */
@SpringBootApplication
public class Server {


    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Server.class, args);



    }
}
