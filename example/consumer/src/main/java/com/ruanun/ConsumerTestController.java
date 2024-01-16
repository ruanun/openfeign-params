package com.ruanun;

import com.ruanun.client.Models;
import com.ruanun.client.TestClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author ruan
 */
@RestController
public class ConsumerTestController {
    @Resource
    private TestClient testClient;

    @GetMapping("/testGet")
    public String testGet(HttpServletRequest request) {
        return testClient.testGet();
    }

    @PostMapping("/testPost")
    public String testPost() {
        return testClient.testPost();
    }

    @GetMapping("/testGetPojo")
    public String testGetPojo() {
        return testClient.testGetPojo(new Models.TestParams());
    }
}
