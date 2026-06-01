package com.library.library.repository;

import com.library.library.model.Issue;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Integer> {
    List<Issue> findByStudentIdOrderByIssueDateDesc(int studentId);
    List<Issue> findAllByOrderByIssueDateDesc();
    List<Issue> findByStatusOrderByIssueDateDesc(String status);
    List<Issue> findByStudentIdAndStatus(int studentId, String status);
}
