package com.demoproj.springaidemoproj.controller;

import com.demoproj.springaidemoproj.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Simple RAG Controller - Only Essential Endpoints
 */
@RestController
@RequestMapping("/api")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    /**
     * Upload a document
     */
    @PostMapping("/upload")
    public String uploadDocument(@RequestBody UploadRequest request) {
        return documentService.saveDocument(request.getContent());
    }

    /**
     * Ask a question
     */
    @PostMapping("/ask")
    public String askQuestion(@RequestBody QuestionRequest request) {
        return documentService.askQuestion(request.getQuestion());
    }

    /**
     * Check system status
     */
    @GetMapping("/status")
    public String getStatus() {
        long count = documentService.getTotalDocuments();
        return "RAG System is running! Documents stored: " + count;
    }

    // Simple request classes
    public static class UploadRequest {
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