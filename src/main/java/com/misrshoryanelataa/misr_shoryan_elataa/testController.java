package com.misrshoryanelataa.misr_shoryan_elataa.Controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class testController {
    @GetMapping("/login")
    public String getName() {
        return "hello";
    }

}