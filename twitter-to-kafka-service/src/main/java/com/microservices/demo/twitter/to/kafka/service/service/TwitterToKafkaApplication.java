package com.microservices.demo.twitter.to.kafka.service.service;

import com.microservices.demo.twitter.to.kafka.service.service.init.StreamInitializer;
import com.microservices.demo.twitter.to.kafka.service.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
public class TwitterToKafkaApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaApplication.class);

    private final StreamRunner streamRunner;

    private final StreamInitializer streamInitializer;

    public TwitterToKafkaApplication(StreamRunner streamRunner, StreamInitializer streamInitializer) {
        this.streamRunner = streamRunner;
        this.streamInitializer = streamInitializer;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Hello Runner");
        streamInitializer.init();
        streamRunner.start();
    }
}
