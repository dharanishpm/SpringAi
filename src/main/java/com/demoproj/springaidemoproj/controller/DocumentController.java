package com.demoproj.springaidemoproj.controller;

import com.demoproj.springaidemoproj.entity.Document;
import com.demoproj.springaidemoproj.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for RAG System
 */
@RestController
@RequestMapping("/api/rag")
@CrossOrigin(origins = "*")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/upload")
    public String uploadDocument(@RequestBody UploadRequest request) {
        return documentService.saveDocument(request.getContent(), request.getSource());
    }

    @PostMapping("/simple-upload")
    public String simpleUploadDocument(@RequestBody SimpleUploadRequest request) {
        return documentService.saveDocument(request.getContent());
    }

    @PostMapping("/ask")
    public String askQuestion(@RequestBody QuestionRequest request) {
        return documentService.askQuestion(request.getQuestion());
    }

    @GetMapping("/documents/{source}")
    public List<Document> getDocumentsBySource(@PathVariable String source) {
        return documentService.getDocumentsBySource(source);
    }

    @GetMapping("/count")
    public String getTotalDocuments() {
        long count = documentService.getTotalDocuments();
        return "Total documents stored: " + count;
    }

    @GetMapping("/health")
    public String healthCheck() {
        return "RAG System is running! 🚀";
    }

    // Request DTOs
    public static class UploadRequest {
        private String content;
        private String source;

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
    }

    public static class SimpleUploadRequest {
        private String content;

        public String getContent() { return content; }
        public void setContent(String content) { this.content = content; }
    }

    public static class QuestionRequest {
        private String question;

        public String getQuestion() { return question; }
        public void setQuestion(String question) { this.question = question; }
    }
}