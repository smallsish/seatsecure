package com.seatsecure.backend.controllers;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {
    @GetMapping
    public String get(){
        return "Get:: admin controller";
    }

    @PostMapping
    public String post(){
        return "Post:: admin controller";
    }
    
    @PutMapping
    public String update(){
        return "Update:: admin controller";
    }
    
    @DeleteMapping
    public String delete(){
        return "Delete:: admin controller";
    }
}
