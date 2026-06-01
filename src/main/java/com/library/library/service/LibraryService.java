package com.library.library.service;

import com.library.library.model.Admin;
import com.library.library.model.Book;
import com.library.library.model.Issue;
import com.library.library.model.Student;
import com.library.library.repository.AdminRepository;
import com.library.library.repository.BookRepository;
import com.library.library.repository.IssueRepository;
import com.library.library.repository.StudentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@Service
public class LibraryService {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final IssueRepository issueRepository;
    private final AdminRepository adminRepository;

    public LibraryService(BookRepository bookRepository, StudentRepository studentRepository, 
                          IssueRepository issueRepository, AdminRepository adminRepository) {
        this.bookRepository = bookRepository;
        this.studentRepository = studentRepository;
        this.issueRepository = issueRepository;
        this.adminRepository = adminRepository;
    }

    public void addBook(Book book) {
        if (book.getAvailableCopies() > 0) {
            book.setStatus("Available");
        } else {
            book.setStatus("Checked-Out");
        }
        bookRepository.save(book);
    }

    public List<Book> getAllBooks() { return bookRepository.findAll(); }
    public List<Book> getTrendingBooks() { return bookRepository.findTop6ByOrderByViewCountDesc(); }
    public List<Book> getNewArrivals() { return bookRepository.findTop6ByOrderByAddedDateDesc(); }
    public List<Book> searchBooks(String query) { return bookRepository.searchBooks(query); }
    
    @Transactional
    public void incrementBookView(int bookId) {
        bookRepository.findById(bookId).ifPresent(book -> {
            book.setViewCount(book.getViewCount() + 1);
            bookRepository.save(book);
        });
    }

    public Student addStudent(String name, String email, String phone, String dob) {
        Student s = new Student();
        s.setName(name);
        s.setEmail(email);
        s.setPhone(phone);
        s.setDob(dob);
        s.setFines(0.0);
        
        // Auto-generate password based on first name + last 4 chars of phone
        String firstName = name.contains(" ") ? name.split(" ")[0] : name;
        String phoneStr = (phone != null && phone.length() >= 4) ? phone.substring(phone.length() - 4) : "1234";
        String password = firstName.toLowerCase() + phoneStr;
        s.setPassword(password);
        
        return studentRepository.save(s);
    }
    
    @Transactional
    public void updateStudentPassword(int studentId, String newPassword) {
        studentRepository.findById(studentId).ifPresent(s -> {
            s.setPassword(newPassword);
            studentRepository.save(s);
        });
    }
    
    @Transactional
    public void updateStudentFine(int studentId, double fine) {
        studentRepository.findById(studentId).ifPresent(s -> {
            s.setFines(fine);
            studentRepository.save(s);
        });
    }

    public List<Student> getAllStudents() { return studentRepository.findAll(); }
    public Student getStudent(int id) { return studentRepository.findById(id).orElse(null); }
    
    @Transactional
    public void updateProfile(int id, String name, String phone, String bio) {
        studentRepository.findById(id).ifPresent(s -> {
            s.setName(name);
            s.setPhone(phone);
            s.setBio(bio);
            studentRepository.save(s);
        });
    }
    
    @Transactional
    public void updateProfilePhoto(int id, String photoPath) {
        studentRepository.findById(id).ifPresent(s -> {
            s.setProfilePhoto(photoPath);
            studentRepository.save(s);
        });
    }
    
    @Transactional
    public void renewMembership(int id, String type) {
        studentRepository.findById(id).ifPresent(s -> {
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, 12); // 1 year renewal
            s.setMembershipExpiry(new java.sql.Date(cal.getTimeInMillis()).toString());
            s.setMembershipType(type);
            studentRepository.save(s);
        });
    }

    @Transactional
    public boolean checkoutBook(int studentId, int bookId) {
        // This now creates a PENDING request instead of direct checkout
        Issue issue = new Issue();
        issue.setStudentId(studentId);
        issue.setBookId(bookId);
        issue.setIssueType("Check-Out");
        issue.setStatus("Pending");
        issue.setIssueDate(new Timestamp(System.currentTimeMillis()));
        issue.setFineAmount(0.0);
        issueRepository.save(issue);
        return true;
    }

    @Transactional
    public boolean approveIssue(int issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue == null || !"Pending".equals(issue.getStatus())) return false;

        Book book = bookRepository.findById(issue.getBookId()).orElse(null);
        if (book != null && book.getAvailableCopies() > 0) {
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            if (book.getAvailableCopies() == 0) {
                book.setStatus("Checked-Out");
            }
            bookRepository.save(book);

            issue.setStatus("Issued");
            Calendar cal = Calendar.getInstance();
            issue.setIssueDate(new Timestamp(cal.getTimeInMillis()));
            cal.add(Calendar.DAY_OF_MONTH, 14); // 14 days due date
            issue.setDueDate(new Timestamp(cal.getTimeInMillis()));
            
            issueRepository.save(issue);
            return true;
        }
        return false;
    }

    @Transactional
    public void rejectIssue(int issueId) {
        issueRepository.findById(issueId).ifPresent(issue -> {
            issue.setStatus("Rejected");
            issueRepository.save(issue);
        });
    }

    @Transactional
    public boolean returnBook(int issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue == null || !"Issued".equals(issue.getStatus())) return false;

        issue.setStatus("Returned");
        issue.setReturnDate(new Timestamp(System.currentTimeMillis()));

        // Calculate fine: ₹5 per day overdue
        double fine = 0.0;
        if (issue.getDueDate() != null) {
            long now = System.currentTimeMillis();
            long due = issue.getDueDate().getTime();
            if (now > due) {
                long daysOverdue = (now - due) / (1000L * 60 * 60 * 24);
                fine = daysOverdue * 5.0;
            }
        }
        issue.setFineAmount(fine);
        issueRepository.save(issue);

        // Restore book copy
        Book book = bookRepository.findById(issue.getBookId()).orElse(null);
        if (book != null) {
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            book.setStatus("Available");
            bookRepository.save(book);
        }

        // Add fine to student's account
        if (fine > 0) {
            final double finalFine = fine;
            studentRepository.findById(issue.getStudentId()).ifPresent(s -> {
                s.setFines(s.getFines() + finalFine);
                studentRepository.save(s);
            });
        }
        return true;
    }

    @Transactional
    public boolean requestReissue(int issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null && "Issued".equals(issue.getStatus())) {
            issue.setStatus("Reissue-Pending");
            issueRepository.save(issue);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean approveReissue(int issueId) {
        Issue issue = issueRepository.findById(issueId).orElse(null);
        if (issue != null && "Reissue-Pending".equals(issue.getStatus())) {
            Calendar cal = Calendar.getInstance();
            // Extend from current due date if it exists, otherwise from now
            if (issue.getDueDate() != null) {
                cal.setTimeInMillis(issue.getDueDate().getTime());
            }
            cal.add(Calendar.DAY_OF_MONTH, 14);
            issue.setDueDate(new Timestamp(cal.getTimeInMillis()));
            issue.setStatus("Issued");
            issueRepository.save(issue);
            return true;
        }
        return false;
    }
    
    public List<Issue> getAllIssues() { return issueRepository.findAllByOrderByIssueDateDesc(); }
    public List<Issue> getIssuesForStudent(int studentId) { return issueRepository.findByStudentIdOrderByIssueDateDesc(studentId); }

    // Admin Auth
    public Admin adminLogin(String username, String password) {
        Admin a = adminRepository.findByUsername(username);
        if(a != null && a.getPassword().equals(password)) {
            return a;
        }
        return null;
    }
    
    @Transactional
    public void updateAdminPassword(String username, String newPassword) {
        Admin a = adminRepository.findByUsername(username);
        if (a != null) {
            a.setPassword(newPassword);
            adminRepository.save(a);
        }
    }
    
    public void addAdmin(String username, String password) {
        Admin a = new Admin();
        a.setUsername(username);
        a.setPassword(password);
        adminRepository.save(a);
    }
    
    // Patron Auth
    public Student patronLogin(String email, String password) {
        return studentRepository.findByEmailAndPassword(email, password);
    }
}
