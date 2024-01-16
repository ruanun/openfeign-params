package com.ruanun.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author ruan
 */
@FeignClient(name = "producer")
public interface TestClient {

    @GetMapping("/testGet")
    String testGet();

    @GetMapping(value = "/testGetPojo")
    String testGetPojo(@SpringQueryMap Models.TestParams params);

    @PostMapping("/testPost")
    String testPost();
}
