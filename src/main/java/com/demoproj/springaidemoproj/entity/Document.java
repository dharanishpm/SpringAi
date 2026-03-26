package com.demoproj.springaidemoproj.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Simple Document entity for RAG system
 * Stores text chunks with their vector embeddings
 */
@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT")
    private String embedding;

    @Column
    private String source;

    public Document(String content, String embedding, String source) {
        this.content = content;
        this.embedding = embedding;
        this.source = source;
    }
}