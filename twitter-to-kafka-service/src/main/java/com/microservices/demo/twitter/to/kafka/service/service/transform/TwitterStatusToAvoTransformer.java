package com.microservices.demo.twitter.to.kafka.service.service.transform;

import com.microservices.demo.kafka.avro.model.TwitterAvroModel;
import org.springframework.stereotype.Component;
import twitter4j.Status;

@Component
public class TwitterStatusToAvoTransformer {

    public TwitterAvroModel getTwitterAvroModelFromStatus(Status status) {
        return TwitterAvroModel.newBuilder()
                .setId(status.getId())
                .setText(status.getText())
                .setUserId(status.getUser().getId())
                .setCreatedAt(status.getCreatedAt().getTime())
                .build();
    }

}
