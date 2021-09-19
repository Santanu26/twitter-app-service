package com.microservices.demo.kafka.admin.config.exception;


public class KafkaClientException extends RuntimeException {

    public KafkaClientException() {
    }

    public KafkaClientException(String msg) {
        super(msg);
    }

    public KafkaClientException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
