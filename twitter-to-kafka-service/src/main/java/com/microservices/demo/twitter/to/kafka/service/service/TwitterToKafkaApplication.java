package com.microservices.demo.twitter.to.kafka.service.service;

import com.microservices.demo.config.TwitterToKafkaConfigurationData;
import com.microservices.demo.twitter.to.kafka.service.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan(basePackages = "com.microservices.demo")
public class TwitterToKafkaApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaApplication.class);

    private final TwitterToKafkaConfigurationData twitter;

    private final StreamRunner streamRunner;

    public TwitterToKafkaApplication(TwitterToKafkaConfigurationData twitter, StreamRunner streamRunner) {
        this.twitter = twitter;
        this.streamRunner = streamRunner;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Hello Runner");
        LOG.info(Arrays.toString(twitter.getTwitterKeywords().toArray(new String[0])));
        LOG.info(twitter.getWelcomeMessage());
        streamRunner.start();
    }
}
