package com.library.library.controller;

import com.library.library.model.Admin;
import com.library.library.model.Student;
import com.library.library.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    private final LibraryService libraryService;

    public AuthController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> handleAuth(@RequestBody Map<String, String> body) {
        String type = body.get("type");
        String identity = body.get("identity");
        String password = body.get("password");

        Map<String, Object> response = new HashMap<>();

        try {
            if ("admin_login".equals(type)) {
                Admin a = libraryService.adminLogin(identity, password);
                if (a != null) {
                    response.put("status", "ok");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "failed");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            } else if ("patron_login".equals(type)) {
                Student s = libraryService.patronLogin(identity, password);
                if (s != null) {
                    response.put("status", "ok");
                    response.put("id", s.getId());
                    response.put("name", s.getName());
                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "failed");
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
                }
            } else if ("add_admin".equals(type)) {
                libraryService.addAdmin(identity, password);
                response.put("status", "ok");
                return ResponseEntity.ok(response);
            } else if ("update_admin_password".equals(type)) {
                libraryService.updateAdminPassword(identity, password);
                response.put("status", "ok");
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "Unknown action");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
