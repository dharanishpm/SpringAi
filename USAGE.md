# Simple RAG System Usage Guide

## Quick Start Commands

### 1. Upload Document (Simple)
```bash
curl -X POST http://localhost:8080/api/rag/simple-upload \
  -H "Content-Type: application/json" \
  -d '{"content": "Spring Boot makes Java development easy and fast"}'
```

### 2. Upload Document (With Source)
```bash
curl -X POST http://localhost:8080/api/rag/upload \
  -H "Content-Type: application/json" \
  -d '{"content": "Spring Cloud provides microservices tools", "source": "spring-guide.txt"}'
```

### 3. Ask Questions
```bash
curl -X POST http://localhost:8080/api/rag/ask \
  -H "Content-Type: application/json" \
  -d '{"question": "What is Spring Boot?"}'
```

### 4. Check System
```bash
curl http://localhost:8080/api/rag/health
curl http://localhost:8080/api/rag/count
```

## Project Structure
```
src/main/java/com/demoproj/springaidemoproj/
├── SpringAiDemoProjApplication.java    # Main app
├── controller/DocumentController.java  # REST endpoints
├── entity/Document.java               # Database model
├── repository/DocumentRepository.java # Database queries
├── service/DocumentService.java       # RAG logic
└── util/TextSplitter.java            # Text processing
```

## How It Works
1. **Upload**: Text → Chunks → Embeddings → Database
2. **Query**: Question → Embedding → Similar Docs → AI Answer

## Configuration
- Database: PostgreSQL with pgvector
- AI Models: Ollama (llama3.2 + nomic-embed-text)
- Chunk Size: 500 characters
- Similarity: Top 3 documents