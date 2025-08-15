package creational.prototype.documenttemplates;

import java.util.ArrayList;
import java.util.List;

public class ContractDocument implements Document {
    private String title;
    private String content;
    private String author;
    private List<String> sections;
    private String contractType;
    private String effectiveDate;
    private String expirationDate;
    
    public ContractDocument() {
        this.sections = new ArrayList<>();
        this.contractType = "Service Agreement";
        this.effectiveDate = "TBD";
        this.expirationDate = "TBD";
        initializeTemplate();
    }
    
    private void initializeTemplate() {
        this.title = "Legal Contract Template";
        this.content = "This template provides a standard format for legal contracts with all necessary clauses and sections.";
        this.sections.add("Parties Involved");
        this.sections.add("Terms and Conditions");
        this.sections.add("Payment Terms");
        this.sections.add("Deliverables");
        this.sections.add("Confidentiality");
        this.sections.add("Termination Clause");
        this.sections.add("Signatures");
    }
    
    @Override
    public Document clone() {
        try {
            ContractDocument cloned = (ContractDocument) super.clone();
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
    
    public void setContractType(String contractType) {
        this.contractType = contractType;
    }
    
    public String getContractType() {
        return contractType;
    }
    
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    
    public String getEffectiveDate() {
        return effectiveDate;
    }
    
    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
    
    public String getExpirationDate() {
        return expirationDate;
    }
    
    @Override
    public void display() {
        System.out.println("=== CONTRACT DOCUMENT ===");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Contract Type: " + contractType);
        System.out.println("Effective Date: " + effectiveDate);
        System.out.println("Expiration Date: " + expirationDate);
        System.out.println("Content: " + content);
        System.out.println("Sections: " + sections);
        System.out.println("==========================");
    }
    
    @Override
    public String getType() {
        return "Contract";
    }
}