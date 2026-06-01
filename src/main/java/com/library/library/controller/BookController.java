package com.library.library.controller;

import com.library.library.model.Book;
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
@RequestMapping("/api/books")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BookController {

    private final LibraryService libraryService;

    public BookController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping
    public List<Book> getAllBooks() {
        return libraryService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBook(@PathVariable Integer id) {
        Book book = libraryService.getAllBooks().stream()
            .filter(b -> b.getId() == id)
            .findFirst()
            .orElse(null);
        if (book == null) {
            Map<String, String> err = new HashMap<>();
            err.put("error", "Book not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
        }
        libraryService.incrementBookView(id);
        return ResponseEntity.ok(book);
    }

    @GetMapping("/trending")
    public List<Book> getTrendingBooks() {
        return libraryService.getTrendingBooks();
    }

    @GetMapping("/new")
    public List<Book> getNewArrivals() {
        return libraryService.getNewArrivals();
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam("q") String query) {
        return libraryService.searchBooks(query);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> addBook(@RequestBody Map<String, String> body) {
        Map<String, Object> response = new HashMap<>();
        try {
            String title = body.get("title");
            String author = body.get("author");
            String category = body.get("category");
            String totalCopiesStr = body.get("total_copies");
            int totalCopies = totalCopiesStr != null ? Integer.parseInt(totalCopiesStr) : 1;
            
            String pdfBase64 = body.get("pdf_base64");
            String pdfName = body.get("pdf_name");
            
            String coverUrl = body.get("cover_url");       // NEW: direct URL support
            String coverBase64 = body.get("cover_base64"); // legacy file upload
            String coverName = body.get("cover_name");

            String description = body.get("description");
            String yearStr = body.get("publication_year");
            int pubYear = yearStr != null ? Integer.parseInt(yearStr) : 2023;

            Book b = new Book();
            b.setTitle(title);
            b.setAuthor(author);
            b.setCategory(category);
            b.setTotalCopies(totalCopies);
            b.setAvailableCopies(totalCopies);
            b.setPublicationYear(pubYear);
            b.setDescription(description);
            b.setStatus("Available");
            b.setViewCount(0);
            b.setAddedDate(new java.sql.Timestamp(System.currentTimeMillis()));

            if (pdfBase64 != null && !pdfBase64.isEmpty()) {
                String baseContent = pdfBase64;
                if (baseContent.contains(",")) baseContent = baseContent.split(",")[1];
                byte[] pdfBytes = Base64.getDecoder().decode(baseContent);
                String pdfPath = "ebooks/" + System.currentTimeMillis() + "_" + pdfName;
                Files.createDirectories(Paths.get("ebooks"));
                Files.write(Paths.get(pdfPath), pdfBytes);
                b.setPdfPath(pdfPath);
            } else {
                b.setPdfPath("");
            }

            // Cover: URL takes priority over file upload
            if (coverUrl != null && !coverUrl.isBlank()) {
                b.setCoverPath(coverUrl);
            } else if (coverBase64 != null && !coverBase64.isEmpty()) {
                String baseContent = coverBase64;
                if (baseContent.contains(",")) baseContent = baseContent.split(",")[1];
                byte[] imgBytes = Base64.getDecoder().decode(baseContent);
                String coverPath = "book_covers/" + System.currentTimeMillis() + "_" + coverName;
                Files.createDirectories(Paths.get("book_covers"));
                Files.write(Paths.get(coverPath), imgBytes);
                b.setCoverPath(coverPath);
            } else {
                b.setCoverPath("");
            }
            
            libraryService.addBook(b);
            response.put("status", "ok");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            response.put("status", "error");
            response.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
