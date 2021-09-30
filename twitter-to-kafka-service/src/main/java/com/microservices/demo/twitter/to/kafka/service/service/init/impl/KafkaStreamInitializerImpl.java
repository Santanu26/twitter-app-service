package com.microservices.demo.twitter.to.kafka.service.service.init.impl;

import com.microservices.demo.config.KafkaConfigData;
import com.microservices.demo.kafka.admin.config.client.KafkaAdminClient;
import com.microservices.demo.twitter.to.kafka.service.service.init.StreamInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class KafkaStreamInitializerImpl implements StreamInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaStreamInitializerImpl.class);

    private final KafkaConfigData kafkaConfigData;

    private final KafkaAdminClient kafkaAdminClient;

    public KafkaStreamInitializerImpl(KafkaConfigData kafkaConfigData, KafkaAdminClient kafkaAdminClient) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaAdminClient = kafkaAdminClient;
    }

    @Override
    public void init() {
        kafkaAdminClient.createTopic();
        kafkaAdminClient.checkSchemaRegistry();
        LOG.info("Topic with name {} is ready for operations: ", kafkaConfigData.getTopicNameToCreate().toArray());
    }
}
