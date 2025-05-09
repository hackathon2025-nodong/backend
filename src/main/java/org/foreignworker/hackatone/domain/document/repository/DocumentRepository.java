package org.foreignworker.hackatone.domain.document.repository;

import org.foreignworker.hackatone.domain.document.Document;
import org.foreignworker.hackatone.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {
    List<Document> findByUser(User user);
} 