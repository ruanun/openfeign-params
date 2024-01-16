package com.ruanun.client;

import lombok.Data;

/**
 * @author ruan
 */
public class Models {

    @Data
    public static class TestParams {
        private String name;
        private String address;
    }
}
