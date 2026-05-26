package org.example.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafakProduserConfiguration {

    @Bean
    NewTopic createTopic(){
        return TopicBuilder.name("message-topic")
                .partitions(3)
                .replicas(1)
                .build();
    }
}
