package com.microservices.demo.twitter.to.kafka.service.service.util;

public class Constant {
    public static final String[] WORDS = new String[]{
            "massive",
            "flood",
            "protection", "system", "built", "around", "New",
            "Orleans", "helped", "save"
    };
    public static final String TWITTER_STRING_AS_JSON = "{" +
            "\"created_at\":\"{0}\"," +
            "\"id\":\"{1}\"," +
            "\"text\":\"{2}\"," +
            "\"user\":{\"id\":\"{3}\"}" +
            "}";
}
