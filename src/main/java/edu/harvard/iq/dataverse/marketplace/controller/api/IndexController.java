package edu.harvard.iq.dataverse.marketplace.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class IndexController {

    @GetMapping()
    public String index() {
        System.out.println("Hello World!");
        return "Hello World!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String admin() {
        System.out.println("Hello World!");
        return "Hello Admin!";
    }



}
