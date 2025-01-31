package edu.harvard.iq.dataverse.marketplace.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Hidden;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api")
public class IndexController {

    @GetMapping()
    @Hidden
    public String index() {
        return "Hello World!";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Hidden
    public String admin() {
        return "Hello Admin!";
    }



}
