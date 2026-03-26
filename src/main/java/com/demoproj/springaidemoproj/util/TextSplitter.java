package com.demoproj.springaidemoproj.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Text splitter utility for RAG system
 */
public class TextSplitter {

    public static List<String> splitText(String text, int chunkSize) {
        List<String> chunks = new ArrayList<>();
        
        if (text == null || text.trim().isEmpty()) {
            return chunks;
        }
        
        text = text.trim();
        int length = text.length();
        
        for (int i = 0; i < length; i += chunkSize) {
            int endIndex = Math.min(length, i + chunkSize);
            String chunk = text.substring(i, endIndex);
            
            if (!chunk.trim().isEmpty()) {
                chunks.add(chunk.trim());
            }
        }
        
        return chunks;
    }
    
    public static List<String> splitBySentences(String text, int maxChunkSize) {
        List<String> chunks = new ArrayList<>();
        
        if (text == null || text.trim().isEmpty()) {
            return chunks;
        }
        
        String[] sentences = text.split("\\. ");
        StringBuilder currentChunk = new StringBuilder();
        
        for (String sentence : sentences) {
            if (currentChunk.length() + sentence.length() > maxChunkSize && currentChunk.length() > 0) {
                chunks.add(currentChunk.toString().trim());
                currentChunk = new StringBuilder();
            }
            
            currentChunk.append(sentence);
            if (!sentence.endsWith(".")) {
                currentChunk.append(".");
            }
            currentChunk.append(" ");
        }
        
        if (currentChunk.length() > 0) {
            chunks.add(currentChunk.toString().trim());
        }
        
        return chunks;
    }
}