package com.lch.ccims.demo;

import java.time.Instant;

public class TestUtil {

    public static void main(String[] args) {
        String api = "/api/files/1";
        String url = String.format("%s%s", api, api);
        System.out.println(url);

        System.out.println(Instant.now());
    }
}
