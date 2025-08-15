package creational.prototype.documenttemplates;

import java.util.ArrayList;
import java.util.List;

public class ReportDocument implements Document {
    private String title;
    private String content;
    private String author;
    private List<String> sections;
    private String reportType;
    private String dateRange;
    
    public ReportDocument() {
        this.sections = new ArrayList<>();
        this.reportType = "General Report";
        this.dateRange = "Q1 2024";
        initializeTemplate();
    }
    
    private void initializeTemplate() {
        this.title = "Quarterly Report Template";
        this.content = "This is a template for quarterly reports with standard formatting and sections.";
        this.sections.add("Executive Summary");
        this.sections.add("Performance Metrics");
        this.sections.add("Financial Analysis");
        this.sections.add("Recommendations");
        this.sections.add("Appendix");
    }
    
    @Override
    public Document clone() {
        try {
            ReportDocument cloned = (ReportDocument) super.clone();
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
    
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public void setDateRange(String dateRange) {
        this.dateRange = dateRange;
    }
    
    public String getDateRange() {
        return dateRange;
    }
    
    @Override
    public void display() {
        System.out.println("=== REPORT DOCUMENT ===");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Report Type: " + reportType);
        System.out.println("Date Range: " + dateRange);
        System.out.println("Content: " + content);
        System.out.println("Sections: " + sections);
        System.out.println("========================");
    }
    
    @Override
    public String getType() {
        return "Report";
    }
}