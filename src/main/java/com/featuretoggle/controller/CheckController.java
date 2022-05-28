package com.featuretoggle.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/health")
public class CheckController {

    @RequestMapping("/")
    public HttpStatus home() {
        return HttpStatus.OK;
    }
}
