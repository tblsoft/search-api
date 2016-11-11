package de.tblsoft.search.api.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

import java.util.concurrent.Future;

/**
 * Created by tblsoft on 24.06.16.
 */


public class TblCommand extends HystrixCommand<String> {

    public static void main(String[] args) throws Exception{
        Future<String> s1 = new TblCommand("Bob", 100, 1000).queue();
        Future<String> s2 = new TblCommand("Alice", 100, 1000).queue();

        System.out.println(s1.get());
        System.out.println(s2.get());
    }



    private final int sleep;
    private final String name;

    public TblCommand(String name, int timeout, int sleep) {

        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("ExampleGroup")).andCommandPropertiesDefaults(

                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(timeout)));
        this.name = name;
        this.sleep = sleep;
    }

    @Override
    protected String run() {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello " + name + "!";
    }

    @Override
    protected String getFallback() {
        return "foo";
    }
}

