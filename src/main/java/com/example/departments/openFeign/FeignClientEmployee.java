package com.example.departments.openFeign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="employee")
public interface FeignClientEmployee {
    @GetMapping(
            path = "/get",
            consumes = "application/json")
    EchoGetResponse getEcho(
            @RequestParam("foo") String foo,
            @RequestParam("bar") String bar
    );
}
