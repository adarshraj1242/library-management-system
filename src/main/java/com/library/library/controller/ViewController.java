package com.library.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    // User Pages
    @GetMapping("/user-profile")
    public String userProfile() {
        return "user-profile";
    }

    @GetMapping("/user-checkout")
    public String userCheckout() {
        return "user-checkout";
    }

    // Admin Pages
    @GetMapping("/admin-books")
    public String adminBooks() {
        return "admin-books";
    }

    @GetMapping("/admin-students")
    public String adminStudents() {
        return "admin-students";
    }
}
