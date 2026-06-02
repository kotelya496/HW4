package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.service.event.MessageEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HW5FeignClientFallbackFactory implements FallbackFactory<HW5FeignClient> {

    private final KafkaTemplate<String, MessageEvent> kafkaTemplate;

    @Autowired
    public HW5FeignClientFallbackFactory(KafkaTemplate<String, MessageEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public HW5FeignClient create(Throwable cause) {

        log.error("Ошибка при вызове HW5. Причина: {}", cause.getMessage(), cause);

        return new HW5FeignClient() {
            @Override
            public void sendMessageEvent(MessageEvent messageEvent) {
                kafkaTemplate.send("message-topic", messageEvent);
            }
        };
    }
}
