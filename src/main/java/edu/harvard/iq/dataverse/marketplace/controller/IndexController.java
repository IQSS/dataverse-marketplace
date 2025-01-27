package edu.harvard.iq.dataverse.marketplace.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("api")
public class IndexController {

    @GetMapping()
    public String index() {
        System.out.println("Hello World!");
        return "Hello World!";
    }

}
