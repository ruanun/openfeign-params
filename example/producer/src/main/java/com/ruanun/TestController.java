package com.ruanun;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * @author ruan
 */
@RestController
public class TestController {

    @GetMapping("/testGet")
    public String testGet(HttpServletRequest request) {
        System.out.println("queryString:" + request.getQueryString());
        System.out.println("header=============================");
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ":" + value);
        }
        return "testGet";
    }

    @PostMapping("/testPost")
    public String testPost(HttpServletRequest request) {
        System.out.println(request.getQueryString());
        return "testPost";
    }

    @GetMapping("/testGetPojo")
    public String testGetPojo(Models.TestParams params, HttpServletRequest request) {
        System.out.println(params);
        System.out.println(request.getQueryString());
        return "testGetPojo";
    }
}
