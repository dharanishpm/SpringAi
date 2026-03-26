# Simple RAG System with Spring AI and pgvector 🚀

A beginner-friendly **Retrieval-Augmented Generation (RAG)** system built with Spring Boot, Spring AI, and PostgreSQL with pgvector.

## What is RAG? 🤔

RAG combines two powerful concepts:
1. **Retrieval**: Finding relevant documents from a database
2. **Generation**: Using AI to generate answers based on those documents

Think of it as giving an AI assistant access to your personal library of documents!

## How It Works 📚

```
1. Upload Document → 2. Split into Chunks → 3. Create Embeddings → 4. Store in Database
                                                                            ↓
5. Ask Question ← 6. Generate Answer ← 7. Find Similar Chunks ← 8. Convert Question to Embedding
```

## Quick Start 🏃‍♂️

### Prerequisites
- Java 17+
- PostgreSQL with pgvector extension
- Ollama running locally (for embeddings and chat)

### 1. Database Setup
```sql
-- Run this in your PostgreSQL database
CREATE EXTENSION IF NOT EXISTS vector;
```

### 2. Configure Application
Update `application.properties`:
```properties
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
spring.datasource.username=your_username
spring.datasource.password=your_password

# Ollama
spring.ai.ollama.base-url=http://localhost:11434
spring.ai.ollama.chat.model=llama3
spring.ai.ollama.embedding.model=nomic-embed-text
```

### 3. Run the Application
```bash
./mvnw spring-boot:run
```

## API Endpoints 🔌

### 1. Upload a Document
```bash
curl -X POST http://localhost:8080/api/rag/upload \
  -H "Content-Type: application/json" \
  -d '{
    "content": "Spring Boot is a Java framework that makes it easy to create web applications.",
    "source": "spring-guide.txt"
  }'
```

### 2. Ask a Question
```bash
curl -X POST http://localhost:8080/api/rag/ask \
  -H "Content-Type: application/json" \
  -d '{
    "question": "What is Spring Boot?"
  }'
```

### 3. Check System Status
```bash
curl http://localhost:8080/api/rag/health
```

### 4. Get Document Count
```bash
curl http://localhost:8080/api/rag/count
```

## Code Structure 📁

```
src/main/java/com/demoproj/springaidemoproj/
├── entity/
│   └── Document.java          # Database entity (simple structure)
├── repository/
│   └── DocumentRepository.java # Database operations
├── service/
│   └── DocumentService.java   # Main RAG logic (well-commented)
├── controller/
│   └── DocumentController.java # REST API endpoints
└── util/
    └── TextSplitter.java      # Text chunking utilities
```

## Key Concepts Explained 💡

### 1. **Embeddings**
- Convert text into numbers (vectors) that represent meaning
- Similar texts have similar vectors
- Example: "dog" and "puppy" have similar embeddings

### 2. **Vector Similarity**
- Use mathematical distance to find similar documents
- Cosine distance is commonly used
- Smaller distance = more similar

### 3. **Chunking**
- Split large documents into smaller pieces
- Each chunk gets its own embedding
- Helps find specific relevant sections

### 4. **Context Building**
- Combine relevant chunks into context
- Feed context + question to AI
- AI generates answer based on provided information

## Example Workflow 🔄

1. **Upload a document about Java:**
   ```json
   {
     "content": "Java is a programming language. It's object-oriented and platform-independent.",
     "source": "java-basics.txt"
   }
   ```

2. **Ask a question:**
   ```json
   {
     "question": "Is Java object-oriented?"
   }
   ```

3. **System process:**
   - Converts question to embedding
   - Finds similar document chunks
   - Builds context from relevant chunks
   - Asks AI to answer based on context

4. **Get answer:**
   ```
   "Yes, Java is object-oriented according to the provided context."
   ```

## Troubleshooting 🔧

### Common Issues:

1. **"pgvector extension not found"**
   - Install pgvector: `CREATE EXTENSION vector;`

2. **"Ollama connection failed"**
   - Start Ollama: `ollama serve`
   - Pull models: `ollama pull llama3` and `ollama pull nomic-embed-text`

3. **"No similar documents found"**
   - Upload some documents first using `/upload` endpoint
   - Check if embeddings are being created properly

## Next Steps 🎯

Once you understand this basic version, you can explore:
- Adding metadata to documents
- Implementing hybrid search (vector + keyword)
- Using different embedding models
- Adding document preprocessing
- Implementing re-ranking algorithms

## Learning Resources 📖

- [Spring AI Documentation](https://docs.spring.io/spring-ai/reference/)
- [pgvector GitHub](https://github.com/pgvector/pgvector)
- [Ollama Documentation](https://ollama.ai/docs)
- [RAG Concepts Explained](https://www.pinecone.io/learn/retrieval-augmented-generation/)

Happy coding! 🎉