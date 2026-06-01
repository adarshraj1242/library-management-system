package com.library.library.controller;

import com.library.library.model.Student;
import com.library.library.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class StudentController {

    private final LibraryService libraryService;

    public StudentController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/api/students")
    public ResponseEntity<?> getStudents(@RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            Student s = libraryService.getStudent(id);
            if (s == null) {
                Map<String, String> err = new HashMap<>();
                err.put("message", "Student not found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
            }
            return ResponseEntity.ok(s);
        }
        return ResponseEntity.ok(libraryService.getAllStudents());
    }

    @GetMapping("/api/students/{id}")
    public ResponseEntity<?> getStudent(@PathVariable Integer id) {
        Student s = libraryService.getStudent(id);
        if (s == null) {
            Map<String, String> err = new HashMap<>();
            err.put("message", "Student not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
        return ResponseEntity.ok(s);
    }

    @PostMapping("/api/students")
    public ResponseEntity<Map<String, Object>> handleStudentPost(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            String action = body.get("action");
            if ("update_fine".equals(action)) {
                int sId = Integer.parseInt(body.get("student_id"));
                double fine = Double.parseDouble(body.get("fine"));
                libraryService.updateStudentFine(sId, fine);
                response.put("status", "ok");
                return ResponseEntity.ok(response);
            }

            String name = body.get("name");
            String email = body.get("email");
            String phone = body.get("phone");
            String dob = body.get("dob");

            Student created = libraryService.addStudent(name, email, phone, dob);
            response.put("status", "ok");
            response.put("generated_password", created.getPassword());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/api/profile/update")
    public ResponseEntity<Map<String, Object>> updateProfile(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            int id = Integer.parseInt(body.get("id"));
            String name = body.get("name");
            String phone = body.get("phone");
            String bio = body.get("bio");
            libraryService.updateProfile(id, name, phone, bio);
            response.put("status", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/api/profile/password")
    public ResponseEntity<Map<String, Object>> updatePassword(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            int id = Integer.parseInt(body.get("id"));
            String pass = body.get("password");
            libraryService.updateStudentPassword(id, pass);
            response.put("status", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping(value = "/api/students/update-profile", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateStudentProfile(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            int id = Integer.parseInt(body.get("student_id"));
            String name = body.get("name");
            String phone = body.get("phone");
            String bio = body.get("bio");
            libraryService.updateProfile(id, name, phone, bio);
            response.put("status", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping(value = "/api/students/update-password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> updateStudentPassword(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            int id = Integer.parseInt(body.get("student_id"));
            String pass = body.get("new_password");
            libraryService.updateStudentPassword(id, pass);
            response.put("status", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/api/profile/photo")
    public ResponseEntity<Map<String, Object>> updatePhoto(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            int id = Integer.parseInt(body.get("id"));
            String base64Photo = body.get("photo_base64");
            String fileName = body.get("file_name");
            
            if (base64Photo != null && !base64Photo.isEmpty()) {
                if (base64Photo.contains(",")) base64Photo = base64Photo.split(",")[1];
                byte[] photoBytes = Base64.getDecoder().decode(base64Photo);
                String photoPath = "profile_photos/" + id + "_" + System.currentTimeMillis() + "_" + fileName;
                Files.createDirectories(Paths.get("profile_photos"));
                Files.write(Paths.get(photoPath), photoBytes);
                libraryService.updateProfilePhoto(id, photoPath);
                response.put("status", "ok");
                response.put("path", photoPath);
                return ResponseEntity.ok(response);
            } else {
                response.put("status", "error");
                response.put("message", "No photo data");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
