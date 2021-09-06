package com.microservices.demo.twitter.to.kafka.service.service.impl;

import com.microservices.demo.config.TwitterToKafkaConfigurationData;
import com.microservices.demo.twitter.to.kafka.service.service.listener.TwitterToKafkaStatusListener;
import com.microservices.demo.twitter.to.kafka.service.service.runner.StreamRunner;
import com.microservices.demo.twitter.to.kafka.service.service.util.Constant;
import com.microservices.demo.twitter.to.kafka.service.service.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import twitter4j.Status;
import twitter4j.TwitterObjectFactory;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

@Component
@ConditionalOnProperty(name = "twitter-to-kafka-service.enable-mock-tweets", havingValue = "true")
public class MockKafkaStreamRunner implements StreamRunner {

    public static final Random RANDOM = new Random();

    private static final Logger LOG = LoggerFactory.getLogger(MockKafkaStreamRunner.class);
    private final TwitterToKafkaConfigurationData twitterToKafkaConfigurationData;
    private final TwitterToKafkaStatusListener twitterToKafkaStatusListener;

    public MockKafkaStreamRunner(TwitterToKafkaConfigurationData twitterToKafkaConfigurationData, TwitterToKafkaStatusListener twitterToKafkaStatusListener) {
        this.twitterToKafkaConfigurationData = twitterToKafkaConfigurationData;
        this.twitterToKafkaStatusListener = twitterToKafkaStatusListener;
    }

    @Override
    public void start() {
        final String[] words = twitterToKafkaConfigurationData.getTwitterKeywords().toArray(new String[0]);
        final int maxTweetLength = twitterToKafkaConfigurationData.getMockMaxTweetLength();
        final int minTweetLength = twitterToKafkaConfigurationData.getMockMinTweetLength();
        final long sleepMs = twitterToKafkaConfigurationData.getMockSleepMs();
        LOG.info("Staring mock filtering twitter streams for keywords: {}", Arrays.toString(words));
        simulateStreamTweet(words, maxTweetLength, minTweetLength, sleepMs);
    }

    private void simulateStreamTweet(String[] words, int maxTweetLength, int minTweetLength, long sleepMs) {
        Executors.newSingleThreadExecutor().submit(() -> {
            try {
                while (true) {
                    String formattedAsJson = getFormattedTweet(words, minTweetLength, maxTweetLength);
                    Status status = TwitterObjectFactory.createStatus(formattedAsJson);
                    twitterToKafkaStatusListener.onStatus(status);
                    sleep(sleepMs);
                }
            } catch (Exception e) {
                LOG.error("Error occurred at simulate tweet: ", e);
            }
        });
    }

    private void sleep(long sleepMs) {
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException i) {
            throw new RuntimeException("");
        }
    }

    private String getFormattedTweet(String[] words, int minTweetLength, int maxTweetLength) {
        String[] param = new String[]{
                ZonedDateTime.now().format(DateTimeFormatter.ofPattern(DateUtil.TWITTER_STATUS_DATE_FORMAT, Locale.ENGLISH)),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE)),
                getRandomTweetContent(words, minTweetLength, maxTweetLength),
                String.valueOf(ThreadLocalRandom.current().nextLong(Long.MAX_VALUE))
        };
        return formattedTweetAsJsonWithParam(param);
    }

    private String formattedTweetAsJsonWithParam(String[] param) {
        String tweet = Constant.TWITTER_STRING_AS_JSON;
        for (int i = 0; i < param.length; i++) {
            tweet = tweet.replace("{" + i + "}", param[i]);
        }
        return tweet;
    }

    private String getRandomTweetContent(String[] keywords, int minTweetLength, int maxTweetLength) {
        StringBuilder tweet = new StringBuilder();
        int tweetLength = RANDOM.nextInt(maxTweetLength - minTweetLength + 1) + minTweetLength;

        return constructRandomTweet(keywords, tweet, tweetLength);
    }

    private String constructRandomTweet(String[] keywords, StringBuilder tweet, int tweetLength) {
        for (int i = 0; i < tweetLength; i++) {
            tweet.append(Constant.WORDS[RANDOM.nextInt(Constant.WORDS.length)]).append(" ");
            if (i == tweetLength / 2) {
                tweet.append(keywords[RANDOM.nextInt(keywords.length)]).append(" ");
            }
        }
        return tweet.toString().trim();
    }
}
