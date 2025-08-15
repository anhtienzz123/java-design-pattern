package creational.prototype.documenttemplates;

import java.util.ArrayList;
import java.util.List;

public class ProposalDocument implements Document {
    private String title;
    private String content;
    private String author;
    private List<String> sections;
    private String clientName;
    private String projectDuration;
    private String budget;
    
    public ProposalDocument() {
        this.sections = new ArrayList<>();
        this.clientName = "Client Name";
        this.projectDuration = "6 months";
        this.budget = "TBD";
        initializeTemplate();
    }
    
    private void initializeTemplate() {
        this.title = "Project Proposal Template";
        this.content = "This template provides a structured format for project proposals with all essential sections.";
        this.sections.add("Project Overview");
        this.sections.add("Scope of Work");
        this.sections.add("Timeline and Milestones");
        this.sections.add("Budget and Resources");
        this.sections.add("Terms and Conditions");
        this.sections.add("Contact Information");
    }
    
    @Override
    public Document clone() {
        try {
            ProposalDocument cloned = (ProposalDocument) super.clone();
            cloned.sections = new ArrayList<>(this.sections);
            return cloned;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException("Clone not supported", e);
        }
    }
    
    @Override
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Override
    public String getTitle() {
        return title;
    }
    
    @Override
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public String getContent() {
        return content;
    }
    
    @Override
    public void addSection(String section) {
        this.sections.add(section);
    }
    
    @Override
    public List<String> getSections() {
        return sections;
    }
    
    @Override
    public void setAuthor(String author) {
        this.author = author;
    }
    
    @Override
    public String getAuthor() {
        return author;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    public String getClientName() {
        return clientName;
    }
    
    public void setProjectDuration(String projectDuration) {
        this.projectDuration = projectDuration;
    }
    
    public String getProjectDuration() {
        return projectDuration;
    }
    
    public void setBudget(String budget) {
        this.budget = budget;
    }
    
    public String getBudget() {
        return budget;
    }
    
    @Override
    public void display() {
        System.out.println("=== PROPOSAL DOCUMENT ===");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Client: " + clientName);
        System.out.println("Duration: " + projectDuration);
        System.out.println("Budget: " + budget);
        System.out.println("Content: " + content);
        System.out.println("Sections: " + sections);
        System.out.println("==========================");
    }
    
    @Override
    public String getType() {
        return "Proposal";
    }
}