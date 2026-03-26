package com.demoproj.springaidemoproj.repository;

import com.demoproj.springaidemoproj.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository for Document operations
 * Handles vector similarity search and basic CRUD
 */
public interface DocumentRepository extends JpaRepository<Document, Long> {
    
    @Query(value = """
        SELECT * FROM documents
        ORDER BY embedding::vector <-> CAST(:queryEmbedding AS vector)
        LIMIT :limitCount
        """, nativeQuery = true)
    List<Document> findSimilarDocuments(@Param("queryEmbedding") String queryEmbedding, 
                                       @Param("limitCount") int limit);
    
    List<Document> findBySource(String source);
}