package com.library.library.controller;

import com.library.library.model.Issue;
import com.library.library.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/issues")
@CrossOrigin(origins = "*", maxAge = 3600)
public class IssueController {

    private final LibraryService libraryService;

    public IssueController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<Issue> getIssues(@RequestParam(value = "student_id", required = false) Integer studentId) {
        if (studentId != null) {
            return libraryService.getIssuesForStudent(studentId);
        }
        return libraryService.getAllIssues();
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> checkoutBook(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            String sIdStr = body.get("student_id");
            String bIdStr = body.get("book_id");

            if (sIdStr != null && bIdStr != null) {
                // Now creates a request
                boolean success = libraryService.checkoutBook(Integer.parseInt(sIdStr), Integer.parseInt(bIdStr));
                if (success) {
                    response.put("status", "ok");
                    response.put("message", "Request submitted for approval");
                    return ResponseEntity.ok(response);
                } else {
                    response.put("status", "error");
                    response.put("message", "Failed to submit request");
                    return ResponseEntity.badRequest().body(response);
                }
            } else {
                response.put("status", "error");
                response.put("message", "Missing student_id or book_id");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping("/{id}/approve")
    public ResponseEntity<Map<String, Object>> approveIssue(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        boolean success = libraryService.approveIssue(id);
        if (success) {
            response.put("status", "ok");
            return ResponseEntity.ok(response);
        }
        response.put("status", "error");
        response.put("message", "Could not approve issue (maybe out of stock)");
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/{id}/reject")
    public ResponseEntity<Map<String, Object>> rejectIssue(@PathVariable int id) {
        libraryService.rejectIssue(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/reissue")
    public ResponseEntity<Map<String, Object>> requestReissue(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        boolean success = libraryService.requestReissue(id);
        if (success) {
            response.put("status", "ok");
            return ResponseEntity.ok(response);
        }
        response.put("status", "error");
        response.put("message", "Failed to request reissue");
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/{id}/reissue-approve")
    public ResponseEntity<Map<String, Object>> approveReissue(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        boolean success = libraryService.approveReissue(id);
        if (success) {
            response.put("status", "ok");
            return ResponseEntity.ok(response);
        }
        response.put("status", "error");
        response.put("message", "Failed to approve reissue");
        return ResponseEntity.badRequest().body(response);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<Map<String, Object>> returnBook(@PathVariable int id) {
        Map<String, Object> response = new HashMap<>();
        boolean success = libraryService.returnBook(id);
        if (success) {
            response.put("status", "ok");
            response.put("message", "Book returned successfully");
            return ResponseEntity.ok(response);
        }
        response.put("status", "error");
        response.put("message", "Could not mark as returned (already returned or not issued)");
        return ResponseEntity.badRequest().body(response);
    }
}
