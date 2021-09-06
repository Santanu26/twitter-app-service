package com.microservices.demo.twitter.to.kafka.service.service.impl;

import com.microservices.demo.twitter.to.kafka.service.service.config.TwitterToKafkaConfigurationData;
import com.microservices.demo.twitter.to.kafka.service.service.listener.TwitterToKafkaStatusListener;
import com.microservices.demo.twitter.to.kafka.service.service.runner.StreamRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.FilterQuery;
import twitter4j.TwitterException;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;

import javax.annotation.PreDestroy;
import java.util.Arrays;


@Component
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-mock-tweets", havingValue = "false", matchIfMissing = true)
public class TwitterKafkaStreamRunner implements StreamRunner {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterKafkaStreamRunner.class);

    private final TwitterToKafkaConfigurationData twitterToKafkaConfigurationData;

    private final TwitterToKafkaStatusListener twitterToKafkaStatusListener;

    private TwitterStream twitterStream;

    public TwitterKafkaStreamRunner(TwitterToKafkaConfigurationData twitterToKafkaConfigurationData, TwitterToKafkaStatusListener twitterToKafkaStatusListener) {
        this.twitterToKafkaConfigurationData = twitterToKafkaConfigurationData;
        this.twitterToKafkaStatusListener = twitterToKafkaStatusListener;
    }

    @Override
    public void start() throws TwitterException {
        twitterStream = new TwitterStreamFactory().getInstance();
        twitterStream.addListener(twitterToKafkaStatusListener);

        addFilters();
    }

    @PreDestroy
    private void shutDown() {
        if (twitterStream != null) {
            LOG.info("Closing the twitter stream...");
            twitterStream.shutdown();
        }
    }

    private void addFilters() {
        String[] keywords = twitterToKafkaConfigurationData.getTwitterKeywords().toArray(new String[0]);

        FilterQuery filterQuery = new FilterQuery(keywords);
        twitterStream.filter(filterQuery);

        LOG.info("Start filtering twitter stream for keywords", Arrays.toString(keywords));
    }
}
