package org.example.service;

import org.example.service.event.MessageEvent;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "HW5", fallbackFactory = HW5FeignClientFallbackFactory.class)
public interface HW5FeignClient {

    @PostMapping("/notification") // Путь, который слушает HW5
    void sendMessageEvent(@RequestBody MessageEvent messageEvent);
}
