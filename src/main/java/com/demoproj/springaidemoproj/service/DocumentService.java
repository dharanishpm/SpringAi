package com.demoproj.springaidemoproj.service;

import com.demoproj.springaidemoproj.entity.Document;
import com.demoproj.springaidemoproj.repository.DocumentRepository;
import com.demoproj.springaidemoproj.util.TextSplitter;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * RAG Service - Handles document storage and question answering
 */
@Service
public class DocumentService {

    @Autowired
    private EmbeddingModel embeddingModel;

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ChatModel chatModel;

    public String saveDocument(String content, String source) {
        try {
            if (source == null || source.trim().isEmpty()) {
                source = "unknown-source";
            }
            
            List<String> chunks = TextSplitter.splitBySentences(content, 500);
            
            int savedChunks = 0;
            for (String chunk : chunks) {
                float[] vector = embeddingModel.embed(chunk);
                String embeddingText = convertVectorToString(vector);
                
                Document document = new Document(chunk, embeddingText, source);
                documentRepository.save(document);
                savedChunks++;
            }
            
            return "Successfully saved " + savedChunks + " chunks from: " + source;
            
        } catch (Exception e) {
            return "Error saving document: " + e.getMessage();
        }
    }

    public String saveDocument(String content) {
        return saveDocument(content, "default-document");
    }

    public String askQuestion(String question) {
        try {
            float[] questionVector = embeddingModel.embed(question);
            String questionEmbedding = convertVectorToString(questionVector);

            List<Document> similarDocs = documentRepository.findSimilarDocuments(questionEmbedding, 3);

            StringBuilder context = new StringBuilder();
            for (Document doc : similarDocs) {
                context.append("Source: ").append(doc.getSource()).append("\n");
                context.append("Content: ").append(doc.getContent()).append("\n\n");
            }

            String prompt = createPrompt(context.toString(), question);
            return chatModel.call(prompt);
            
        } catch (Exception e) {
            return "Error processing question: " + e.getMessage();
        }
    }

    private String convertVectorToString(float[] vector) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < vector.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(vector[i]);
        }
        sb.append("]");
        return sb.toString();
    }

    private String createPrompt(String context, String question) {
        return String.format("""
            Answer the question based ONLY on the provided context.
            If the answer is not in the context, say "I don't have enough information."
            
            Context:
            %s
            
            Question: %s
            
            Answer:
            """, context, question);
    }

    public List<Document> getDocumentsBySource(String source) {
        return documentRepository.findBySource(source);
    }

    public long getTotalDocuments() {
        return documentRepository.count();
    }
}