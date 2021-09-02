package com.microservices.demo.twitter.to.kafka.service.service;

import com.microservices.demo.twitter.to.kafka.service.service.config.TwitterToKafkaConfigurationData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class TwitterToKafkaApplication implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterToKafkaApplication.class);

    private final TwitterToKafkaConfigurationData twitter;

    public TwitterToKafkaApplication(TwitterToKafkaConfigurationData twitter) {
        this.twitter = twitter;
    }

    public static void main(String[] args) {
        SpringApplication.run(TwitterToKafkaApplication.class,args);
    }

    @Override
    public void run(String... args) throws Exception {
        LOG.info("Hello Runner");
        LOG.info(Arrays.toString(twitter.getTwitterKeywords().toArray(new String[0])));
        LOG.info(twitter.getWelcomeMessage());
    }
}
