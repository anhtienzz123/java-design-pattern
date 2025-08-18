package behavioral.interpreter.searchfilters;

import java.time.LocalDateTime;
import java.util.*;

/**
 * Concrete implementation of SearchableItem representing a document.
 * Used for demonstration purposes in the search filter example.
 */
public class DocumentItem implements SearchableItem {
    private final String id;
    private final String title;
    private final String content;
    private final String author;
    private final String category;
    private final LocalDateTime createdDate;
    private final LocalDateTime modifiedDate;
    private final Set<String> tags;
    private final Map<String, Object> metadata;
    private final List<String> keywords;
    private final int pageCount;
    private final String language;
    private final boolean published;
    private final double score;
    
    public DocumentItem(Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.content = builder.content;
        this.author = builder.author;
        this.category = builder.category;
        this.createdDate = builder.createdDate;
        this.modifiedDate = builder.modifiedDate;
        this.tags = Set.copyOf(builder.tags);
        this.metadata = Map.copyOf(builder.metadata);
        this.keywords = List.copyOf(builder.keywords);
        this.pageCount = builder.pageCount;
        this.language = builder.language;
        this.published = builder.published;
        this.score = builder.score;
    }
    
    @Override
    public String getId() {
        return id;
    }
    
    @Override
    public String getType() {
        return "document";
    }
    
    @Override
    public String getStringField(String fieldName) {
        return switch (fieldName.toLowerCase()) {
            case "title" -> title;
            case "content" -> content;
            case "author" -> author;
            case "category" -> category;
            case "language" -> language;
            case "id" -> id;
            default -> {
                Object value = metadata.get(fieldName);
                yield value instanceof String ? (String) value : null;
            }
        };
    }
    
    @Override
    public Number getNumericField(String fieldName) {
        return switch (fieldName.toLowerCase()) {
            case "pagecount", "pages" -> pageCount;
            case "score" -> score;
            default -> {
                Object value = metadata.get(fieldName);
                yield value instanceof Number ? (Number) value : null;
            }
        };
    }
    
    @Override
    public Boolean getBooleanField(String fieldName) {
        return switch (fieldName.toLowerCase()) {
            case "published" -> published;
            default -> {
                Object value = metadata.get(fieldName);
                yield value instanceof Boolean ? (Boolean) value : null;
            }
        };
    }
    
    @Override
    public LocalDateTime getDateTimeField(String fieldName) {
        return switch (fieldName.toLowerCase()) {
            case "created", "createddate" -> createdDate;
            case "modified", "modifieddate" -> modifiedDate;
            default -> {
                Object value = metadata.get(fieldName);
                yield value instanceof LocalDateTime ? (LocalDateTime) value : null;
            }
        };
    }
    
    @Override
    public List<String> getListField(String fieldName) {
        return switch (fieldName.toLowerCase()) {
            case "keywords" -> keywords;
            case "tags" -> new ArrayList<>(tags);
            default -> {
                Object value = metadata.get(fieldName);
                if (value instanceof List<?> list) {
                    yield list.stream()
                              .map(Object::toString)
                              .collect(java.util.stream.Collectors.toList());
                }
                yield null;
            }
        };
    }
    
    @Override
    public Set<String> getTags() {
        return tags;
    }
    
    @Override
    public Set<String> getFieldNames() {
        Set<String> fieldNames = new HashSet<>(Set.of(
            "id", "title", "content", "author", "category", "createdDate", "modifiedDate",
            "tags", "keywords", "pageCount", "language", "published", "score"
        ));
        fieldNames.addAll(metadata.keySet());
        return fieldNames;
    }
    
    @Override
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    @Override
    public boolean hasField(String fieldName) {
        return getFieldNames().contains(fieldName) || metadata.containsKey(fieldName);
    }
    
    @Override
    public Object getFieldValue(String fieldName) {
        return switch (fieldName.toLowerCase()) {
            case "id" -> id;
            case "title" -> title;
            case "content" -> content;
            case "author" -> author;
            case "category" -> category;
            case "created", "createddate" -> createdDate;
            case "modified", "modifieddate" -> modifiedDate;
            case "tags" -> tags;
            case "keywords" -> keywords;
            case "pagecount", "pages" -> pageCount;
            case "language" -> language;
            case "published" -> published;
            case "score" -> score;
            default -> metadata.get(fieldName);
        };
    }
    
    @Override
    public String getSearchableText() {
        StringBuilder sb = new StringBuilder();
        if (title != null) sb.append(title).append(" ");
        if (content != null) sb.append(content).append(" ");
        if (author != null) sb.append(author).append(" ");
        if (category != null) sb.append(category).append(" ");
        if (!keywords.isEmpty()) sb.append(String.join(" ", keywords)).append(" ");
        if (!tags.isEmpty()) sb.append(String.join(" ", tags));
        return sb.toString().trim();
    }
    
    @Override
    public LocalDateTime getTimestamp() {
        return modifiedDate != null ? modifiedDate : createdDate;
    }
    
    @Override
    public double getScore() {
        return score;
    }
    
    @Override
    public String toString() {
        return String.format("Document{id='%s', title='%s', author='%s', category='%s'}", 
                           id, title, author, category);
    }
    
    /**
     * Builder class for creating DocumentItem instances.
     */
    public static class Builder {
        private String id;
        private String title = "";
        private String content = "";
        private String author = "";
        private String category = "";
        private LocalDateTime createdDate = LocalDateTime.now();
        private LocalDateTime modifiedDate = LocalDateTime.now();
        private Set<String> tags = new HashSet<>();
        private Map<String, Object> metadata = new HashMap<>();
        private List<String> keywords = new ArrayList<>();
        private int pageCount = 1;
        private String language = "en";
        private boolean published = true;
        private double score = 0.0;
        
        public Builder id(String id) {
            this.id = id;
            return this;
        }
        
        public Builder title(String title) {
            this.title = title;
            return this;
        }
        
        public Builder content(String content) {
            this.content = content;
            return this;
        }
        
        public Builder author(String author) {
            this.author = author;
            return this;
        }
        
        public Builder category(String category) {
            this.category = category;
            return this;
        }
        
        public Builder createdDate(LocalDateTime createdDate) {
            this.createdDate = createdDate;
            return this;
        }
        
        public Builder modifiedDate(LocalDateTime modifiedDate) {
            this.modifiedDate = modifiedDate;
            return this;
        }
        
        public Builder tags(String... tags) {
            this.tags.addAll(Arrays.asList(tags));
            return this;
        }
        
        public Builder tags(Set<String> tags) {
            this.tags = new HashSet<>(tags);
            return this;
        }
        
        public Builder metadata(String key, Object value) {
            this.metadata.put(key, value);
            return this;
        }
        
        public Builder metadata(Map<String, Object> metadata) {
            this.metadata = new HashMap<>(metadata);
            return this;
        }
        
        public Builder keywords(String... keywords) {
            this.keywords.addAll(Arrays.asList(keywords));
            return this;
        }
        
        public Builder keywords(List<String> keywords) {
            this.keywords = new ArrayList<>(keywords);
            return this;
        }
        
        public Builder pageCount(int pageCount) {
            this.pageCount = pageCount;
            return this;
        }
        
        public Builder language(String language) {
            this.language = language;
            return this;
        }
        
        public Builder published(boolean published) {
            this.published = published;
            return this;
        }
        
        public Builder score(double score) {
            this.score = score;
            return this;
        }
        
        public DocumentItem build() {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalStateException("Document ID is required");
            }
            return new DocumentItem(this);
        }
    }
    
    /**
     * Creates a new document builder.
     */
    public static Builder builder() {
        return new Builder();
    }
}