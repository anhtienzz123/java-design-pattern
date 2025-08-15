package creational.prototype.documenttemplates;

import java.util.List;

public interface Document extends Cloneable {
    Document clone();
    
    void setTitle(String title);
    String getTitle();
    
    void setContent(String content);
    String getContent();
    
    void addSection(String section);
    List<String> getSections();
    
    void setAuthor(String author);
    String getAuthor();
    
    void display();
    
    String getType();
}